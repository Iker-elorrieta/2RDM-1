package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
            
            // Lee los datos del cliente
            String[] datosRecibidos = entrada.readUTF().split(",");
			
			if(datosRecibidos[0].equals("login")) {
				salida.writeInt(metodos.login(datosRecibidos[1],datosRecibidos[2]));
				salida.flush();
				
			}else if(datosRecibidos[0].equals("registro")) {
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}