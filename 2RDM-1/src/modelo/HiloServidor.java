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

				case OTROSHORARIOS:
					otrosHorarios(salida);
					break;

				case REUNIONES:
					reuniones(salida);
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
			usuario.setNombre(null);
			Tipos tiposCero = new Tipos();
			tiposCero.setId(-1);
			usuario.setTipos(tiposCero);
		}

		String[] datosUsuario = { Integer.toString(usuario.getId()), Integer.toString(usuario.getTipos().getId()),
				usuario.getNombre() };

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

	private void otrosHorarios(ObjectOutputStream salida) throws IOException {
		Horarios oH = new Horarios();
		Users usElegido = new Users();

		usElegido.setId(Integer.parseInt(datosRecibidos[1]));

		List<Horarios> otrosHorarios = oH.cargarHorariosPorUsuario(Integer.parseInt(datosRecibidos[1]), session);

		// Convertir Horarios a un arreglo antes de enviar
		List<Object[]> listaOtrosHorarios = new ArrayList<>();
		for (Horarios horario : otrosHorarios) {
			listaOtrosHorarios.add(new Object[] { horario.getId().getDia(), horario.getId().getHora(),
					horario.getModulos().getNombre() });
		}

		salida.writeObject(listaOtrosHorarios);

	}

	private void reuniones(ObjectOutputStream salida) throws IOException {
		Reuniones reu = new Reuniones();
		Users uProfe = new Users();
		uProfe.setId(Integer.parseInt(datosRecibidos[1]));
		reu.setUsersByProfesorId(uProfe);

		salida.writeObject(reu.reuniones(session));

	}

}