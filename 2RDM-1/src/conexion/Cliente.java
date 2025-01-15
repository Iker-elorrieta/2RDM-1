package conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {

	public static void main(String[] args) {
		int puerto = 2000;

		try {
			Socket socket = new Socket("10.5.13.47", puerto);
			System.out.println("Conectado al servidor.");

			DataInputStream entrada = new DataInputStream(socket.getInputStream());
			DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

			String mensajeServidor = entrada.readUTF() + entrada.read();
			System.out.println(mensajeServidor);

			salida.writeUTF("hola soy el cliente numero ");
			salida.write(2);

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}