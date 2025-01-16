package conexion;

import java.io.IOException;
import java.net.Socket;

public class Cliente {

	public static void main(String[] args) {
		int puerto = 2000;

		try {
			Socket socket = new Socket("localhost", puerto);
			System.out.println("Conectado al servidor.");
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}