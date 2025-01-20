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
import modelo.Users;
import vista.PanelHorario;
import vista.Principal;

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
			visualizarPanel(Principal.enumAcciones.PANEL_HORARIO);
			cargarHorarioProfe();
			break;
		case PANEL_OTROS_HORARIOS:
			visualizarPanel(Principal.enumAcciones.PANEL_OTROS_HORARIOS);
			break;
		case PANEL_REUNIONES:
			visualizarPanel(Principal.enumAcciones.PANEL_REUNIONES);
			break;

		default:
			break;
		}
	}

	public void visualizarPanel(Principal.enumAcciones panel) {
		this.vistaPrincipal.visualizarPaneles(panel);
	}

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
				JOptionPane.showMessageDialog(null, "Bienvenido", "Inicio de sesión exitoso",
						JOptionPane.INFORMATION_MESSAGE);

				ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
						Principal.enumAcciones.PANEL_MENU.toString());

				actionPerformed(e);

			}

		} catch (IOException | NoSuchAlgorithmException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al iniciar sesión", "Error", JOptionPane.ERROR_MESSAGE);
		}

		clearLogin();

	}

	public void clearLogin() {
		vistaLogin.getTxtFUser().setText("");
		vistaLogin.getPswdFPassword().setText("");
	}

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
				System.out.println("No se han encontrado horarios.");
				return;
			}

			Object[][] data = new Object[horarios.size()][3];

			for (int i = 0; i < horarios.size(); i++) {
				Horarios horarioIndividual = horarios.get(i);

				System.out.println("Horarios recibidos: " + horarioIndividual);

				data[i][0] = horarioIndividual.getId().getDia();
				data[i][1] = horarioIndividual.getId().getHora();
				data[i][2] = horarioIndividual.getModulos().getNombre();
			}

			panelHorario.actualizarTabla(data);

			System.out.println("Datos a mostrar en la tabla:");
			for (int i = 0; i < data.length; i++) {
				System.out.println("Día: " + data[i][0] + ", Hora: " + data[i][1] + ", Módulo: " + data[i][2]);
			}

		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar el horario", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}