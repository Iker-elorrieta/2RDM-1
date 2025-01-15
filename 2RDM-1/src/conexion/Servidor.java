package conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import controlador.Metodos;
import modelo.Users;

public class Servidor {

	private ArrayList<Users> users;

	public static void main(String[] args) {
		boolean disponible = true;
		Metodos metodos = new Metodos();
		int puerto = 2000;
		try (ServerSocket serverSocket = new ServerSocket(puerto)) {

			System.out.println("Servidor esperando.......");
			Socket socket = null;
			while (disponible) {
				socket = serverSocket.accept();
				System.out.println("Cliente conectado.");

				DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
				DataInputStream entrada = new DataInputStream(socket.getInputStream());

				salida.writeUTF("Bienvenido al servidor numero");
				salida.write(1);

				String mensajeCliente = entrada.readUTF() + entrada.read();
				System.out.println(mensajeCliente);

				metodos.conectarJSON();

				metodos.pruebaSentenciaHQL();
			}

			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public synchronized int login(String user, String pswd) {
		try {
			String hashedUser = hash(user);
			String hashedPswd = hash(pswd);

			/*
			 * for (Users u : users) { if (u.getUsername().equals(hashedUser) &&
			 * u.getPassword().equals(hashedPswd)) { return "Bienvenido " + u.getNombre(); }
			 * }
			 */

			if (user.equals("Eder") && pswd.equals("Eder1"))
				return 0;

			return 2;
		} catch (NoSuchAlgorithmException e) {
			return 1;
		}
		/*
		 * Return 0 -> Login correcto
		 * Return 1 -> Error
		 * Return 2 -> Login incorrecto
		 */
	}

	/*
	 * public synchronized String registro(String user, String name, String pswd) {
	 * try { for (Users u : users) { if (u.getUsername().equals(user)) { return
	 * "Error: El nombre de usuario ya existe."; } }
	 * 
	 * users.add(new Users(user, name, hash(pswd))); return "Registro exitoso."; }
	 * catch (NoSuchAlgorithmException e) { return "Error al registrar usuario: " +
	 * e.getMessage(); } }
	 */

	public static String hash(String respuesta) throws NoSuchAlgorithmException {
		String resumenString = new String();
		MessageDigest md = MessageDigest.getInstance("SHA");

		byte dataBytes[] = respuesta.getBytes();
		md.update(dataBytes);

		byte resumen[] = md.digest();
		resumenString = new String(resumen);

		return resumenString;

	}
}
