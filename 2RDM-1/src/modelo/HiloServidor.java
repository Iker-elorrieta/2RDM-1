package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
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

				Object obj = entrada.readObject();

				if (obj instanceof String[]) {
					this.datosRecibidos = (String[]) obj;

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
					}

					salida.flush();
				} else {
					System.err.println("Se esperaba un String[], se recibió: " + obj.getClass().getName());
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Cliente " + cliente.getInetAddress().toString().replace("/", "") + " desconectado.");
		}
	}

	private void obtenerMatricula(ObjectOutputStream salida) {
		Matriculaciones matriculas = new Matriculaciones();
		String matriculaAlumno = matriculas.recogerMatriculaPorId(Integer.parseInt(datosRecibidos[1]), session);
		try {
			salida.writeObject(matriculaAlumno);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void login(ObjectOutputStream salida) throws IOException {

		Users usuario = new Users();
		usuario.setUsername(datosRecibidos[1]);
		usuario.setPassword(datosRecibidos[2]);

		usuario = usuario.login(session);

		if (usuario != null && usuario.getTipos().getId() != 4) {
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

			// TODO falta usuario.setArgazkia(null);

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
				usuario.getUsername() // [9] --> username

				// TODO falta usuario.getArgazkia(URL);

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
		reuniones = r.getReunionesById(Integer.parseInt(datosRecibidos[1]), session);

		List<Object[]> listaReuniones = new ArrayList<>();
		for (Reuniones reunion : reuniones) {
			listaReuniones.add(new Object[] { reunion.getIdCentro(), reunion.getAsunto(), reunion.getAula(),
					reunion.getEstado(), reunion.getFecha(), reunion.getTitulo() });
		}

		salida.writeObject(listaReuniones);
	}

	private void obtenerCentros(ObjectOutputStream salida) throws IOException {
		Centros c = new Centros();
		List<Centros> centros = new ArrayList<>();
		centros = c.leerJson();

		List<Object[]> listaCentros = new ArrayList<>();
		for (Centros centro : centros) {
			listaCentros.add(new Object[] { centro.getId(), centro.getCentro() });
		}

		salida.writeObject(listaCentros);
	}

}