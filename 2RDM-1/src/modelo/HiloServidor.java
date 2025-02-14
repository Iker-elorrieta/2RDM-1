package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import vista.Principal;

public class HiloServidor extends Thread {
	private Socket cliente;
	private String[] datosRecibidos;
	private final String login = "login", registro = "registro";

	private SessionFactory sesion = HibernateUtil.getSessionFactory();
	private Session session = sesion.openSession();

	public HiloServidor(Socket cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {
		try {

			while (cliente.isConnected()) {
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());

				// Lee los datos del cliente
				datosRecibidos = ((String) entrada.readObject()).split(",");

				Principal.enumAccionesHiloServidor accion = Principal.enumAccionesHiloServidor
						.valueOf(datosRecibidos[0]);


				switch (accion) {

				case LOGIN:

					Users usuario = new Users();
					usuario.setUsername(datosRecibidos[1]);
					usuario.setPassword(datosRecibidos[2]);

					usuario = usuario.login(session);


					if (usuario != null && usuario.getTipos().getId() != 4) {
						String resultadoGuardado = Ciclos.guardarCiclo(8, "ELECRONICA", session);
						if (!resultadoGuardado.equals(""))
							System.out.println(resultadoGuardado);

					}

					salida.writeObject(usuario);

					break;

				case HORARIO:
					Horarios h = new Horarios();
					List<Horarios> horarios = new ArrayList<>();
					horarios = h.cargarHorariosPorUsuario(Integer.parseInt(datosRecibidos[1]), session);

					List<Object[]> listaHorarios = new ArrayList<>();
					for (Horarios horario : horarios) {
						listaHorarios.add(new Object[] { horario.getId().getDia(), horario.getId().getHora(),
								horario.getModulos().getNombre() });

					if (idUsuario != 0) {
						Ciclos ciclo = new Ciclos();
						String resultadoGuardado = ciclo.guardarCiclo(8, "ELECRONICA", session);
						if (!resultadoGuardado.equals(""))
							JOptionPane.showMessageDialog(null, resultadoGuardado, "Error",
									JOptionPane.INFORMATION_MESSAGE);

					}

					salida.writeObject(listaHorarios);
					break;


				case TODOSUSUARIOS:
					Users usuariosTodos = new Users();
					salida.writeObject(usuariosTodos.todosUsers(session));

					break;

				case OTROSHORARIOS:
				    Horarios oH = new Horarios();
				    Users usElegido = new Users();

				    usElegido.setId(Integer.parseInt(datosRecibidos[1]));

				    List<Horarios> otrosHorarios = oH.cargarHorariosPorUsuario(Integer.parseInt(datosRecibidos[1]), session);

				    // Convertir Horarios a un arreglo antes de enviar
				    List<Object[]> listaOtrosHorarios = new ArrayList<>();
				    for (Horarios horario : otrosHorarios) {
				        listaOtrosHorarios.add(new Object[]{
				            horario.getId().getDia(),
				            horario.getId().getHora(),
				            horario.getModulos().getNombre()
				        });
				    }

				    salida.writeObject(listaOtrosHorarios);
				    break;

				case REUNIONES:
					Reuniones reu = new Reuniones();
					Users uProfe = new Users();
					uProfe.setId(Integer.parseInt(datosRecibidos[1]));
					reu.setUsersByProfesorId(uProfe);

					salida.writeObject(reu.reuniones(session));

					break;


				}

				salida.flush();
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}