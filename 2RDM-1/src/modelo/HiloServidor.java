package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HiloServidor extends Thread {
	private Socket cliente;
	private String[] datosRecibidos;
	private final String login = "login", registro = "registro";

	private static SessionFactory sesion = HibernateUtil.getSessionFactory();
	private static Session session = sesion.openSession();

	public HiloServidor(Socket cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {
		try {

			while (true) {
				DataInputStream entrada = new DataInputStream(cliente.getInputStream());
				DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());

				// Lee los datos del cliente
				datosRecibidos = entrada.readUTF().split(",");

				if (datosRecibidos[0].equals(login)) {
					Users usuario = new Users();
					int idUsuario = usuario.login(datosRecibidos[1], datosRecibidos[2], session);

					if (idUsuario != 0) {
						String resultadoGuardado = Ciclos.guardarCiclo(8, "ELECRONICA", session);
						if (!resultadoGuardado.equals(""))
							JOptionPane.showMessageDialog(null, resultadoGuardado, "Error",
									JOptionPane.INFORMATION_MESSAGE);

					}

					salida.writeInt(idUsuario);
					salida.flush();

				} else if (datosRecibidos[0].equals(registro)) {

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}