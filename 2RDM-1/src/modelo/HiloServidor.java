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

	private static SessionFactory sesion = HibernateUtil.getSessionFactory();
	private static Session session = sesion.openSession();

	public HiloServidor(Socket cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {
		try {

			while (true) {
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());

				// LEER DATOS DEL CLIENTE (CONTROLADOR)
				datosRecibidos = ((String) entrada.readObject()).split(",");

				if (datosRecibidos[0].equals("login")) {

					Users usuario = new Users();
					usuario = usuario.login(datosRecibidos[1], datosRecibidos[2], session);

					if (usuario != null) {
						String resultadoGuardado = Ciclos.guardarCiclo(8, "ELECRONICA", session);
						if (!resultadoGuardado.equals(""))
							JOptionPane.showMessageDialog(null, resultadoGuardado, "Información",
									JOptionPane.INFORMATION_MESSAGE);
					}

					salida.writeObject(usuario);
					salida.flush();

				} else if (datosRecibidos[0].equals("horario")) {
					Horarios h = new Horarios();

					List<Horarios> horarios = new ArrayList<>();
					horarios = h.cargarHorariosPorUsuario(Integer.parseInt(datosRecibidos[1]), session);

					salida.writeObject(horarios);
					salida.flush();
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}