package conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import controlador.Metodos;

public class Servidor {

	public static void main(String[] args) {
	    boolean disponible=true;
		Metodos metodos = new Metodos();
		int puerto=2000;
    	try (ServerSocket serverSocket = new ServerSocket(puerto)) {
    		
			System.out.println("Servidor esperando.......");
			Socket socket = null;
            while(disponible) {
                socket = serverSocket.accept();
                System.out.println("Cliente conectado.");
                
    			DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
                DataInputStream entrada = new DataInputStream(socket.getInputStream());
                
                salida.writeUTF("Bienvenido al servidor numero ");
                salida.write(1);
                
                String mensajeCliente = entrada.readUTF()+entrada.read();
                System.out.println(mensajeCliente);
                
                metodos.conectarJSON();
                
                System.out.println("PRUEBA---");
                metodos.pruebaSentenciaHQL();
                

            }
            

            socket.close();
            serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
