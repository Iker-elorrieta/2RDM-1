package conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
                
                String mensajeCliente = entrada.readUTF()+entrada.readInt();
                System.out.println(mensajeCliente);
                
                //metodos.conectarJSON(); TODO FUNCIONA PERO LO HE COMENTADO PORQUE EL SYSO ES MUY GRANDE
                
                //metodos.pruebaSentenciaHQL();
                
                ObjectInputStream entradaObjeto = new ObjectInputStream(socket.getInputStream());

                try {
					int id = (int) entradaObjeto.readObject();
					String nombre = (String) entradaObjeto.readObject();
					
					metodos.guardarCiclo(id,nombre);
			        
			        
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

            }
            

            socket.close();
            serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
