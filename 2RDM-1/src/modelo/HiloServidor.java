package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HiloServidor extends Thread {
	private Socket cliente;
	private String[] datosRecibidos;
	private final String login = "login", horario = "horario", todosUsuarios = "todosUsuarios",
			otrosHorarios = "otrosHorarios";

	private static SessionFactory sesion = HibernateUtil.getSessionFactory();
	private static Session session = sesion.openSession();

	public HiloServidor(Socket cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {
		try {

			while (cliente.isConnected()) {
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());

				// Lee los datos del cliente
				datosRecibidos = ((String) entrada.readObject()).split(",");

				if (datosRecibidos[0].equals(login)) {

					Users usuario = new Users();
					usuario.setUsername(datosRecibidos[1]);
					usuario.setPassword(datosRecibidos[2]);

					usuario = usuario.login(session);

					if (usuario != null && usuario.getTipos().getId() != 4) {
						String resultadoGuardado = Ciclos.guardarCiclo(8, "ELECRONICA", session);
						if (!resultadoGuardado.equals(""))
							JOptionPane.showMessageDialog(null, resultadoGuardado, "Información",
									JOptionPane.INFORMATION_MESSAGE);
					}

					salida.writeObject(usuario);

					salida.flush();

				} else if (datosRecibidos[0].equals(horario)) {
					Horarios h = new Horarios();

					List<Horarios> horarios = new ArrayList<>();
					horarios = h.cargarHorariosPorUsuario(Integer.parseInt(datosRecibidos[1]), session);

					salida.writeObject(horarios);
					salida.flush();

				} else if (datosRecibidos[0].equals(todosUsuarios)) {
					Users usuariosTodos = new Users();
					salida.writeObject(usuariosTodos.todosUsers(session));

				} else if (datosRecibidos[0].equals(otrosHorarios)) {
					Horarios otrosHorarios = new Horarios();
					Users usElegido = new Users();

					usElegido.setId(Integer.parseInt(datosRecibidos[1]));

					salida.writeObject(
							otrosHorarios.cargarHorariosPorUsuario(Integer.parseInt(datosRecibidos[1]), session));
				}

				salida.flush();
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}