package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import controlador.Metodos;

public class HiloServidor extends Thread {
	private Socket cliente;
	private Metodos metodos = new Metodos();

	public HiloServidor(Socket cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream entradaLogin = new ObjectInputStream(cliente.getInputStream());
			ObjectOutputStream salidaLogin = new ObjectOutputStream(cliente.getOutputStream());

			Datos datosLogin = (Datos) entradaLogin.readObject();

			//System.out.println(datosLogin.getUsername() + "aaaaaaa");

			if (datosLogin.getOperacion().equals("login")) {
				salidaLogin.writeObject(metodos.login(datosLogin.getUsername(), datosLogin.getContrasenna()));
				salidaLogin.flush();

			} else if (datosLogin.getOperacion().equals("registro")) {

			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
