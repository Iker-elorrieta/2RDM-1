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

				datosRecibidos = ((String) entrada.readObject()).split(",");

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
					reuniones(salida);
					break;

				case OBTENERCENTROS:
					obtenerCentros(salida);
					break;
				}

				salida.flush();
			}

		} catch (IOException | ClassNotFoundException e) {
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

		salida.writeObject(String.join(",", datosUsuario));

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
		salida.writeObject(usuariosTodos.todosUsers(session));

	}

	private void reuniones(ObjectOutputStream salida) throws IOException {

		Reuniones r = new Reuniones();
		Users u = new Users();
		u.setId(Integer.parseInt(datosRecibidos[1]));
		r.setUsersByProfesorId(u);

		salida.writeObject(r.reuniones(session));

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

}