package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vista.Principal;

public class Controlador implements ActionListener {
	private vista.Principal vistaPrincipal;
	private vista.PanelLogin vistaMenu;
	private vista.PanelMenu vistaEjemplo;

	public Controlador(vista.Principal vistaPrincipal, vista.PanelLogin vistaMenu, vista.PanelMenu vistaEjemplo) {
		this.vistaPrincipal = vistaPrincipal;
		this.vistaMenu = vistaMenu;
		this.vistaEjemplo = vistaEjemplo;

		this.inicializarControlador();
	}

	private void inicializarControlador() {
		accionesVistaMenu();
		accionesVistaEjemplo();

	}

	private void accionesVistaMenu() {
		this.vistaMenu.getBtnLogin().addActionListener(this);
		this.vistaMenu.getBtnLogin().setActionCommand(Principal.enumAcciones.LOGIN_INICIAR_SESION.toString());

	}

	private void accionesVistaEjemplo() {
		this.vistaEjemplo.getBtnPrevious().addActionListener(this);
		this.vistaEjemplo.getBtnPrevious().setActionCommand(Principal.enumAcciones.PANEL_LOGIN.toString());

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
		default:
			break;
		}
	}

	public void visualizarPanel(Principal.enumAcciones panel) {
		this.vistaPrincipal.visualizarPaneles(panel);
	}

	private void login() {
		
	}

}
