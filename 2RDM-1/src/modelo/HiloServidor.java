package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class HiloServidor extends Thread {
	private Socket cliente;
	
	private static SessionFactory sesion = HibernateUtil.getSessionFactory();
	private static Session session = sesion.openSession();

	public HiloServidor(Socket cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {
		try {
			
			while(true){
	            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
	            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());

				// Lee los datos del cliente
				final String[] datosRecibidos = entrada.readUTF().split(",");

				if (datosRecibidos[0].equals("login")) {
					Users usuario = new Users();
					int idUsuario = usuario.login(datosRecibidos[1], datosRecibidos[2],session);
					
					salida.writeInt(idUsuario);
					
					if(idUsuario!=0) {
						Ciclos.guardarCiclo(8, "ELECRONICA",session);
					}
					
					salida.flush();

				} else if (datosRecibidos[0].equals("registro")) {

				}
			}


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}