package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

				// Lee los datos del cliente
				datosRecibidos = ((String) entrada.readObject()).split(",");

				if (datosRecibidos[0].equals("login")) {

					Users usuario = new Users();
					usuario = usuario.login(datosRecibidos[1], datosRecibidos[2], session);

					if (usuario != null) {
						String resultadoGuardado = Ciclos.guardarCiclo(8, "ELECRONICA", session);
						if (!resultadoGuardado.equals(""))
							JOptionPane.showMessageDialog(null, resultadoGuardado, "Error",
									JOptionPane.INFORMATION_MESSAGE);

					}

					salida.writeObject(usuario);
					salida.flush();

				} else if (datosRecibidos[0].equals("registro")) {

				}
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}