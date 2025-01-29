package conexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import modelo.HiloServidor;

public class Servidor {
	public static void main(String[] args) {
		boolean disponible = true;
		int puerto = 2000;

		try (ServerSocket serverSocket = new ServerSocket(puerto)) {

			System.out.println("Servidor esperando...");
			Socket socket = null;

			while (disponible) {

				socket = serverSocket.accept();
				System.out.println("Cliente conectado.");

				HiloServidor hiloLogin = new HiloServidor(socket);
				hiloLogin.start();

			}
			System.out.println("Cliente desconectado.");

			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}