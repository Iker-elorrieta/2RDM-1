package conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		int puerto = 2000;

		try {
			Socket socket = new Socket("10.5.13.47", puerto);
			System.out.println("Conectado al servidor.");
			Scanner sc = new Scanner(System.in);

			DataInputStream entrada = new DataInputStream(socket.getInputStream());
			DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

			String mensajeServidor = entrada.readUTF() + entrada.read();
			System.out.println(mensajeServidor);

			System.out.println("Escribe un texto al servidor: ");
			salida.writeUTF(sc.nextLine());

			System.out.println("Escribe un numero al servidor: ");
			salida.writeInt(sc.nextInt());

			ObjectOutputStream salidaObjeto = new ObjectOutputStream(socket.getOutputStream());
			// ENVIO DE CICLO NUEVO
			salidaObjeto.writeObject(6);
			salidaObjeto.writeObject("ELEC");

			sc.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}