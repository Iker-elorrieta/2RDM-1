package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import vista.Principal;

public class HiloServidor extends Thread {
	private Socket cliente;
	private String[] datosRecibidos;

	private SessionFactory sesion = HibernateUtil.getSessionFactory();
	private Session session = sesion.openSession();

	public HiloServidor(Socket cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {
		try {

			while (cliente.isConnected()) {
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());

				this.datosRecibidos = (String[]) entrada.readObject();

				Principal.enumAccionesHiloServidor accion = Principal.enumAccionesHiloServidor
						.valueOf(datosRecibidos[0]);

				switch (accion) {

				case LOGIN:
					login(salida);
					break;
				case HORARIO:
					horario(salida);
					break;
				case TODOSUSUARIOS:
					todosUsuarios(salida);
					break;
				case REUNIONES:
					reunion(salida);
					break;
				case OBTENERCENTROS:
					obtenerCentros(salida);
					break;
				case OBTENERMATRICULACIONES:
					obtenerMatricula(salida);
					break;
				case MODIFICARREUNION:
					aceptarReunion(salida);
					break;
				case GUARDARIMAGEN:
					guardarImagen();
					break;
				}

				salida.flush();
			}

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Cliente " + cliente.getInetAddress() + " desconectado (" + e.getMessage() + ")");
		}

	}

	private void login(ObjectOutputStream salida) throws IOException {

		Users usuario = new Users();
		usuario.setUsername(datosRecibidos[1]);
		usuario.setPassword(datosRecibidos[2]);

		usuario = usuario.login(session);

		if (usuario != null && usuario.getTipos().getId() != 4) {
			// Solo pueden logearse profesores
			String resultadoGuardado = Ciclos.guardarCiclo(8, "ELECRONICA", session);
			if (!resultadoGuardado.equals(""))
				System.out.println(resultadoGuardado);
		}

		if (usuario == null) {
			usuario = new Users();
			usuario.setId(-1);
			usuario.setEmail(null);
			usuario.setNombre(null);
			usuario.setDni(null);
			usuario.setDireccion(null);
			usuario.setTelefono1(-1);
			usuario.setTelefono2(-1);
			usuario.setApellidos(null);
			usuario.setUsername(null);

			Tipos tiposCero = new Tipos();
			tiposCero.setId(-1);
			usuario.setTipos(tiposCero);

			usuario.setArgazkia(new byte[0]);

		}

		String[] datosUsuario = { Integer.toString(usuario.getId()), // [0] --> ID
				Integer.toString(usuario.getTipos().getId()), // [1] --> ID
				usuario.getNombre(), // [2] --> ID
				usuario.getEmail(), // [3] --> email
				usuario.getDni(), // [4] --> DNI
				usuario.getDireccion(), // [5] --> Direcc
				usuario.getTelefono1() != null ? Integer.toString(usuario.getTelefono1()) : "0", // [6] --> Tlf1
				usuario.getTelefono2() != null ? Integer.toString(usuario.getTelefono2()) : "0", // [7] --> Tlf2
				usuario.getApellidos(), // [8] --> apellido
				usuario.getUsername(), // [9] --> username
				usuario.getArgazkia() != null ? usuario.getArgazkia().toString() : "0" // [10]

		};

		salida.writeObject(datosUsuario);

	}

	private void horario(ObjectOutputStream salida) throws IOException {
		Horarios h = new Horarios();
		List<Horarios> horarios = new ArrayList<>();
		horarios = h.cargarHorariosPorUsuario(Integer.parseInt(datosRecibidos[1]), session);

		List<Object[]> listaHorarios = new ArrayList<>();
		for (Horarios horario : horarios) {
			listaHorarios.add(new Object[] { horario.getId().getDia(), horario.getId().getHora(),
					horario.getModulos().getNombre() });
		}

		salida.writeObject(listaHorarios);

	}

	private void todosUsuarios(ObjectOutputStream salida) throws IOException {
		Users usuariosTodos = new Users();
		List<Object[]> usuarios = usuariosTodos.todosUsers(session);

		salida.writeObject(usuarios);
	}

	private void reunion(ObjectOutputStream salida) throws IOException {
		Reuniones r = new Reuniones();
		List<Reuniones> reuniones = new ArrayList<>();
		reuniones = r.getReunionesById(Integer.parseInt(datosRecibidos[1]));

		List<Object[]> listaReuniones = new ArrayList<>();
		for (Reuniones reunion : reuniones) {
			listaReuniones.add(new Object[] { reunion.getAsunto(), reunion.getAula(), reunion.getEstado(),
					reunion.getFecha(), reunion.getIdCentro(), reunion.getTitulo(), reunion.getIdReunion(), reunion.getUsersByProfesorId().getNombre(), reunion.getUsersByAlumnoId().getNombre()});
		}

		salida.writeObject(listaReuniones);
	}

	private void obtenerCentros(ObjectOutputStream salida) throws IOException {
		Centros c = new Centros();
		List<Centros> centros = c.leerJson();
		

		List<Object[]> listaCentros = new ArrayList<>();
		for (Centros centro : centros) {
			listaCentros.add(new Object[] { centro.getId(), centro.getCentro() });
		}

		salida.writeObject(listaCentros);
	}

	private void aceptarReunion(ObjectOutputStream salida) throws IOException {
		Reuniones r = new Reuniones();
		boolean resultado = r.modificarReunion(Integer.parseInt(datosRecibidos[1]), datosRecibidos[2].toString());

		salida.writeObject(resultado);

	}

	private void obtenerMatricula(ObjectOutputStream salida) {
		Matriculaciones matriculas = new Matriculaciones();
		String matriculaAlumno[] = matriculas.recogerMatriculaPorId(Integer.parseInt(datosRecibidos[1]), session);
		try {
			salida.writeObject(matriculaAlumno);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void guardarImagen() {
		// Recibimos la cadena Base64 de la imagen
		String imagenBase64 = datosRecibidos[2]; // Aquí recibimos la cadena Base64

		// Decodificar la cadena Base64 a un arreglo de bytes
		byte[] imagenBytes = Base64.getDecoder().decode(imagenBase64); // Usamos getDecoder() y decode() de
																		// java.util.Base64

		Users usuarioImagen = new Users();
		usuarioImagen.setId(Integer.parseInt(datosRecibidos[1]));
		usuarioImagen.setArgazkia(imagenBytes); // Asignamos los bytes a la propiedad de imagen

		// Guardar la imagen en la base de datos
		usuarioImagen.guardarImagen(session);
		System.out.println("Imagen guardada correctamente.");
	}

}