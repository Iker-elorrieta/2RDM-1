package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Horarios;
import modelo.Users;
import vista.Principal;

public class Controlador implements ActionListener {
	private vista.Principal vistaPrincipal;
	private vista.PanelLogin vistaLogin;
	private vista.PanelMenu vistaMenu;
	private vista.PanelHorario vistaHorario;
	private vista.PanelOtrosHorarios vistaOtrosHorarios;
	private vista.PanelReuniones vistaReuniones;
	private Socket socket = null;
	private Users usuariaLogeado = null;
	

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
		this.vistaOtrosHorarios.getProfesComboBox().setActionCommand(Principal.enumAcciones.CARGAR_TABLA_OTROS_HORARIOS.toString());
		
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
			break;
		case PANEL_OTROS_HORARIOS:
			mCargarTodosUsuarios();
			mCargarDatosOtrosHorarios();
			visualizarPanel(Principal.enumAcciones.PANEL_OTROS_HORARIOS);
			break;
		case PANEL_REUNIONES:
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

	private void mCargarTodosUsuarios() {
		try {
			ObjectOutputStream salidaTodosUsuarios = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entradaTodosUsuarios = new ObjectInputStream(socket.getInputStream());
			

			String[] datosTodosUsuarios = { "todosUsuarios"};
			
			salidaTodosUsuarios.writeObject(String.join(",", datosTodosUsuarios));
			List<Users> usuarios = (List<Users>) entradaTodosUsuarios.readObject();
			
			for(Users us : usuarios) {
				this.vistaPrincipal.getPanelOtrosHorarios().getProfesComboBox().addItem(us);
			}
			
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private void mCargarDatosOtrosHorarios() {
		Users usuarioElegido = (Users) this.vistaPrincipal.getPanelOtrosHorarios().getProfesComboBox().getSelectedItem();
		
		try {
			ObjectOutputStream salidaDatosOtrosHorarios = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entradaResultadoOtrosHorarios = new ObjectInputStream(socket.getInputStream());
			
			salidaDatosOtrosHorarios.writeObject(usuarioElegido);
			
			List<Horarios> otrosHorarios = (List<Horarios>) entradaResultadoOtrosHorarios.readObject();
    		
			String matriz[][] = new String[otrosHorarios.size()][4];
			for(int i =0;i<otrosHorarios.size();i++) {
	    		matriz[i][0] = (otrosHorarios.get(i).getId().getDia() != null) ? otrosHorarios.get(i).getId().getDia().toString() : "NULL";
	    		matriz[i][1] = (otrosHorarios.get(i).getId().getHora() != null) ? otrosHorarios.get(i).getId().getHora().toString() : "NULL";
	    		matriz[i][2] = (otrosHorarios.get(i).getUsers().getId()+"");
	    		matriz[i][3] = (otrosHorarios.get(i).getModulos().getId()+"");
				
    		    this.vistaPrincipal.getPanelOtrosHorarios().getModeloOtrosHorarios().addRow(matriz[i]);

	    		
			}
			

			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			usuariaLogeado = (Users) entradaResultadoLogin.readObject();
			

		} catch (IOException | NoSuchAlgorithmException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (usuariaLogeado != null) {
			JOptionPane.showMessageDialog(null, "Bienvenido " + usuariaLogeado.getNombre() + " !!",
					"Inicio de sesión exitoso", JOptionPane.INFORMATION_MESSAGE);

			ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
					Principal.enumAcciones.PANEL_MENU.toString());

			actionPerformed(e);

		} else if (usuariaLogeado == null) {
			JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
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

}