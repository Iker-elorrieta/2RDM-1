package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HiloServidor extends Thread {
	private Socket cliente;
	private String[] datosRecibidos;
	private boolean dividirEnComas = true;

	private static SessionFactory sesion = HibernateUtil.getSessionFactory();
	private static Session session = sesion.openSession();

	public HiloServidor(Socket cliente) {
		this.cliente = cliente;
		this.dividirEnComas = true;
	}

	@Override
	public void run() {
		try {

			while (cliente.isConnected()) {
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());

				// Lee los datos del cliente
				if(dividirEnComas) {
					datosRecibidos = ((String) entrada.readObject()).split(",");
				}else {
					datosRecibidos[0]="otrosHorarios";
				}
				
				if (datosRecibidos[0].equals("login")) {
					Users usuario = new Users();
					usuario.setUsername(datosRecibidos[1]);
					usuario.setPassword(datosRecibidos[2]);					
					
					usuario = usuario.login(session);

					if (usuario != null) {
						
						String resultadoGuardado = Ciclos.guardarCiclo(8, "ELECRONICA", session);
						if (!resultadoGuardado.equals(""))
							JOptionPane.showMessageDialog(null, resultadoGuardado, "Error",
									JOptionPane.INFORMATION_MESSAGE);
					}

					salida.writeObject(usuario);
				} else if (datosRecibidos[0].equals("registro")) {

				}else if(datosRecibidos[0].equals("todosUsuarios")) {
					Users usuariosTodos = new Users();
					
					salida.writeObject(usuariosTodos.todosUsers(session));
					this.dividirEnComas=false;
					
				}else if(datosRecibidos[0].equals("otrosHorarios")) {
					Horarios otrosHorarios = new Horarios();
					Users usElegido = (Users) entrada.readObject();
					
					salida.writeObject(otrosHorarios.otrosHorarios(session,usElegido));
				}

				salida.flush();
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}