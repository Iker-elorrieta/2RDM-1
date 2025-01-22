package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Horarios;
import vista.PanelHorario;
import vista.Principal;
import modelo.Reuniones;
import modelo.Users;

public class Controlador implements ActionListener {
	private vista.Principal vistaPrincipal;
	private vista.PanelLogin vistaLogin;
	private vista.PanelMenu vistaMenu;
	private vista.PanelHorario vistaHorario;
	private vista.PanelOtrosHorarios vistaOtrosHorarios;
	private vista.PanelReuniones vistaReuniones;
	private Socket socket = null;
	private Users usuarioLogeado = null;
	private PanelHorario panelHorario;
	private int usuarioNoAdmitidoId = 4;

	public Controlador(vista.Principal vistaPrincipal, vista.PanelLogin vistaLogin, vista.PanelMenu vistaMenu,
			vista.PanelHorario vistaHorario, vista.PanelOtrosHorarios vistaOtrosHorarios,
			vista.PanelReuniones vistaReuniones) {
		this.vistaPrincipal = vistaPrincipal;
		this.vistaLogin = vistaLogin;
		this.vistaMenu = vistaMenu;
		this.vistaHorario = vistaHorario;
		this.vistaOtrosHorarios = vistaOtrosHorarios;
		this.vistaReuniones = vistaReuniones;

		this.iniciarConexionConServidor();
		this.inicializarControlador();

	}

	/**
	 * Metodo que hace la conexion con el Servidor
	 */
	private void iniciarConexionConServidor() {
		int puerto = 2000;
		// String ip = "10.5.13.47";
		String ip = "localhost";
		try {
			socket = new Socket(ip, puerto);
			System.out.println("Conectado al servidor.");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Controlador() {
	}

	private void inicializarControlador() {
		accionesVistaLogin();
		accionesVistaMenu();
		accionesVistaHorario();
		accionesVistaOtrosHorarios();
		accionesVistaReuniones();
	}

	private void accionesVistaLogin() {
		this.vistaLogin.getBtnLogin().addActionListener(this);
		this.vistaLogin.getBtnLogin().setActionCommand(Principal.enumAcciones.LOGIN_INICIAR_SESION.toString());

		this.vistaLogin.getRootPane().setDefaultButton(this.vistaLogin.getBtnLogin());
	}

	private void accionesVistaMenu() {
		this.vistaMenu.getBtnHorario().addActionListener(this);
		this.vistaMenu.getBtnHorario().setActionCommand(Principal.enumAcciones.PANEL_HORARIO.toString());

		this.vistaMenu.getBtnOtrosHorarios().addActionListener(this);
		this.vistaMenu.getBtnOtrosHorarios().setActionCommand(Principal.enumAcciones.PANEL_OTROS_HORARIOS.toString());

		this.vistaMenu.getBtnReuniones().addActionListener(this);
		this.vistaMenu.getBtnReuniones().setActionCommand(Principal.enumAcciones.PANEL_REUNIONES.toString());

		this.vistaMenu.getBtnDesconectarse().addActionListener(this);
		this.vistaMenu.getBtnDesconectarse().setActionCommand(Principal.enumAcciones.PANEL_LOGIN.toString());
	}

	private void accionesVistaHorario() {
		this.vistaHorario.getBtnVolver().addActionListener(this);
		this.vistaHorario.getBtnVolver().setActionCommand(Principal.enumAcciones.PANEL_MENU.toString());
	}

	private void accionesVistaOtrosHorarios() {
		this.vistaOtrosHorarios.getProfesComboBox().addActionListener(this);
		this.vistaOtrosHorarios.getProfesComboBox()
				.setActionCommand(Principal.enumAcciones.CARGAR_TABLA_OTROS_HORARIOS.toString());

		this.vistaOtrosHorarios.getBtnVolver().addActionListener(this);
		this.vistaOtrosHorarios.getBtnVolver().setActionCommand(Principal.enumAcciones.PANEL_MENU.toString());
	}

	private void accionesVistaReuniones() {
		this.vistaReuniones.getBtnVolver().addActionListener(this);
		this.vistaReuniones.getBtnVolver().setActionCommand(Principal.enumAcciones.PANEL_MENU.toString());
	}

	public void actionPerformed(ActionEvent e) {
		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());
		switch (accion) {
		case PANEL_LOGIN:
			visualizarPanel(Principal.enumAcciones.PANEL_LOGIN);
			break;
		case LOGIN_INICIAR_SESION:
			login();
			break;
		case PANEL_MENU:
			visualizarPanel(Principal.enumAcciones.PANEL_MENU);
			break;
		case PANEL_HORARIO:
			this.vistaPrincipal.getPanelHorario().getModeloHorario().setRowCount(0);
			visualizarPanel(Principal.enumAcciones.PANEL_HORARIO);
			cargarHorarioProfe();
			break;
		case PANEL_OTROS_HORARIOS:
			mCargarTodosUsuarios();
			mCargarDatosOtrosHorarios();
			visualizarPanel(Principal.enumAcciones.PANEL_OTROS_HORARIOS);
			break;
		case PANEL_REUNIONES:
			this.vistaPrincipal.getPanelReuniones().getModeloReuniones().setRowCount(0);
			mCargarReuniones();
			visualizarPanel(Principal.enumAcciones.PANEL_REUNIONES);
			break;
		case CARGAR_TABLA_OTROS_HORARIOS:
			this.vistaPrincipal.getPanelOtrosHorarios().getModeloOtrosHorarios().setRowCount(0);
			mCargarDatosOtrosHorarios();
			break;

		default:
			break;
		}
	}

	/**
	 * Metodo que envia el id del usuario para luego recibir del servidor una Lista
	 * de las Reuniones y mostrarlos en una tabla.
	 */
	private void mCargarReuniones() {
		try {
			ObjectOutputStream sReuniones = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream eReuniones = new ObjectInputStream(socket.getInputStream());

			String[] datosReuniones = { "reuniones", Integer.toString(usuarioLogeado.getId()) };
			sReuniones.writeObject(String.join(",", datosReuniones));

			@SuppressWarnings("unchecked")
			List<Reuniones> reuniones = (List<Reuniones>) eReuniones.readObject();

			String matriz[][] = new String[reuniones.size()][8];

			for (int i = 0; i < reuniones.size(); i++) {

				matriz[i][0] = (reuniones.get(i).getFecha() != null) ? reuniones.get(i).getFecha().toString() : "NULL";

				matriz[i][1] = (reuniones.get(i).getFecha() + " HORAS" != null) ? reuniones.get(i).getFecha().toString()
						: "NULL";

				matriz[i][2] = (Integer.toString(reuniones.get(i).getUsersByProfesorId().getId()) != null)
						? Integer.toString(reuniones.get(i).getUsersByProfesorId().getId())
						: "NULL";

				matriz[i][3] = (reuniones.get(i).getIdReunion() != null) ? reuniones.get(i).getIdReunion().toString()
						: "NULL";

				matriz[i][4] = (reuniones.get(i).getIdCentro() != null) ? reuniones.get(i).getIdCentro() : "NULL";

				matriz[i][5] = (reuniones.get(i).getTitulo() != null) ? reuniones.get(i).getTitulo() : "NULL";

				matriz[i][6] = (reuniones.get(i).getAsunto() != null) ? reuniones.get(i).getAsunto() : "NULL";

				matriz[i][7] = (reuniones.get(i).getAula() != null) ? reuniones.get(i).getAula() : "NULL";

				this.vistaPrincipal.getPanelReuniones().getModeloReuniones().addRow(matriz[i]);
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que recoge del Servidor una Lista de todos los usuarios
	 */
	@SuppressWarnings("unchecked")
	private void mCargarTodosUsuarios() {
		try {
			ObjectOutputStream salidaTodosUsuarios = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entradaTodosUsuarios = new ObjectInputStream(socket.getInputStream());

			String[] datosTodosUsuarios = { "todosUsuarios" };

			salidaTodosUsuarios.writeObject(String.join(",", datosTodosUsuarios));
			List<Users> usuarios = (List<Users>) entradaTodosUsuarios.readObject();

			if (this.vistaPrincipal.getPanelOtrosHorarios().getProfesComboBox().getItemCount() == 0)
				for (Users us : usuarios) {
					if (us.getTipos().getId() != usuarioNoAdmitidoId)
						this.vistaPrincipal.getPanelOtrosHorarios().getProfesComboBox().addItem(us);
				}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que envia el id del usuario para luego recibir del servidor una Lista
	 * de los Otros Horarios y mostrarlos en una tabla.
	 */
	private void mCargarDatosOtrosHorarios() {
		Users usuarioElegido = (Users) this.vistaPrincipal.getPanelOtrosHorarios().getProfesComboBox()
				.getSelectedItem();
		try {
			ObjectOutputStream salidaDatosOtrosHorarios = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entradaResultadoOtrosHorarios = new ObjectInputStream(socket.getInputStream());

			String[] datosOtrosHorarios = { "otrosHorarios", Integer.toString(usuarioElegido.getId()) };
			salidaDatosOtrosHorarios.writeObject(String.join(",", datosOtrosHorarios));

			@SuppressWarnings("unchecked")
			List<Horarios> otrosHorarios = (List<Horarios>) entradaResultadoOtrosHorarios.readObject();

			if (otrosHorarios == null || otrosHorarios.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No se han encontrado horarios para mostrar", "Aviso",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			Object[][] data = new Object[otrosHorarios.size()][6];
			for (int h = 1; h <= 6; h++) {
				data[h][0] = "Hora " + h;
			}

			for (int i = 0; i < otrosHorarios.size(); i++) {
				Horarios horarioIndividual = otrosHorarios.get(i);
				int hora = Integer.parseInt(horarioIndividual.getId().getHora());

				switch (horarioIndividual.getId().getDia()) {
				case "L/A":
					data[hora][1] = horarioIndividual.getModulos().getNombre();
					break;
				case "M/A":
					data[hora][2] = horarioIndividual.getModulos().getNombre();
					break;
				case "X":
					data[hora][3] = horarioIndividual.getModulos().getNombre();
					break;
				case "J/O":
					data[hora][4] = horarioIndividual.getModulos().getNombre();
					break;
				case "V/O":
					data[hora][5] = horarioIndividual.getModulos().getNombre();
					break;
				default:
					break;
				}
			}

			this.vistaPrincipal.getPanelOtrosHorarios().getModeloOtrosHorarios().setRowCount(0);

			for (int h = 1; h <= 6; h++) {
				this.vistaPrincipal.getPanelOtrosHorarios().getModeloOtrosHorarios().addRow(data[h]);
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void visualizarPanel(Principal.enumAcciones panel) {
		this.vistaPrincipal.visualizarPaneles(panel);
	}

	/**
	 * Metodo que envia al Servidor las credenciales del usuario cifradas y el
	 * Servidor le devuelve el Objeto del Usuario entero.
	 */
	public void login() {
		String user = vistaLogin.getTxtFUser().getText();
		String pswd = new String(vistaLogin.getPswdFPassword().getPassword());

		if (user.isEmpty() || pswd.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {

			ObjectOutputStream salidaDatosLogin = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entradaResultadoLogin = new ObjectInputStream(socket.getInputStream());

			String[] datosLogin = { "login", hash(user), hash(pswd) };

			salidaDatosLogin.writeObject(String.join(",", datosLogin));

			usuarioLogeado = (Users) entradaResultadoLogin.readObject();

			if (usuarioLogeado == null) {
				JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error",
						JOptionPane.ERROR_MESSAGE);

			} else if (usuarioLogeado.getTipos().getId() == usuarioNoAdmitidoId) {
				JOptionPane.showMessageDialog(null, "El uso de la aplicación es exclusivo de profesores", "Error",
						JOptionPane.ERROR_MESSAGE);

			} else {
				JOptionPane.showMessageDialog(null, "Bienvenido " + usuarioLogeado.getNombre(),
						"Inicio de sesión exitoso", JOptionPane.INFORMATION_MESSAGE);

				ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
						Principal.enumAcciones.PANEL_MENU.toString());

				actionPerformed(e);

			}

		} catch (IOException | NoSuchAlgorithmException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al iniciar sesión", "Error", JOptionPane.ERROR_MESSAGE);
		}

		clearLogin();

	}

	/**
	 * Limpia los Texts Fields de login
	 */
	public void clearLogin() {
		vistaLogin.getTxtFUser().setText("");
		vistaLogin.getPswdFPassword().setText("");
	}

	/**
	 * Cifra el String que se le envia
	 * 
	 * @param respuesta String sin cifrar que recibe
	 * @return Devuelve el string cifrado
	 * @throws NoSuchAlgorithmException
	 */
	public static String hash(String respuesta) throws NoSuchAlgorithmException {
		StringBuilder resumenString = new StringBuilder();
		MessageDigest md = MessageDigest.getInstance("SHA");

		byte[] dataBytes = respuesta.getBytes();
		md.update(dataBytes);

		byte[] resumen = md.digest();

		for (byte b : resumen) {
			resumenString.append(String.format("%02x", b));
		}

		return resumenString.toString();
	}

	/**
	 * Metodo que carga los horarios del usuario logeado enviandole el id al
	 * servidor y le devuelve una Lista de los Horarios del Usuario
	 */
	public void cargarHorarioProfe() {
		if (panelHorario == null) {
			panelHorario = new PanelHorario();
		}

		try {
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

			String[] horario = { "horario", usuarioLogeado.getId() + "" };

			salida.writeObject(String.join(",", horario));

			@SuppressWarnings("unchecked")
			List<Horarios> horarios = (List<Horarios>) entrada.readObject();

			if (horarios == null || horarios.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No se han encontrado horarios para mostrar", "Aviso",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			Object[][] data = new Object[horarios.size()][6];
			for (int h = 1; h <= 6; h++) {
				data[h][0] = "Hora " + h;
			}

			for (int i = 0; i < horarios.size(); i++) {
				Horarios horarioIndividual = horarios.get(i);
				int hora = Integer.parseInt(horarioIndividual.getId().getHora());

				switch (horarioIndividual.getId().getDia()) {
				case "L/A":
					data[hora][1] = horarioIndividual.getModulos().getNombre();
					break;
				case "M/A":
					data[hora][2] = horarioIndividual.getModulos().getNombre();
					break;
				case "X":
					data[hora][3] = horarioIndividual.getModulos().getNombre();
					break;
				case "J/O":
					data[hora][4] = horarioIndividual.getModulos().getNombre();
					break;
				case "V/O":
					data[hora][5] = horarioIndividual.getModulos().getNombre();
					break;
				default:
					break;
				}
			}

			for (int h = 1; h <= 6; h++) {
				this.vistaPrincipal.getPanelHorario().getModeloHorario().addRow(data[h]);
			}
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar el horario", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}