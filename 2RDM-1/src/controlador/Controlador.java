package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vista.Principal;

public class Controlador implements ActionListener {
	private vista.Principal vistaPrincipal;
	private vista.PanelMenu vistaMenu;
	private vista.PanelEjemplo vistaEjemplo;

	public Controlador(vista.Principal vistaPrincipal, vista.PanelMenu vistaMenu, vista.PanelEjemplo vistaEjemplo) {
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
		this.vistaMenu.getBtnNext().addActionListener(this);
		this.vistaMenu.getBtnNext().setActionCommand(Principal.enumAcciones.PANEL_EJEMPLO.toString());

	}

	private void accionesVistaEjemplo() {
		this.vistaEjemplo.getBtnPrevious().addActionListener(this);
		this.vistaEjemplo.getBtnPrevious().setActionCommand(Principal.enumAcciones.PANEL_MENU.toString());

	}

	public void actionPerformed(ActionEvent e) {
		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());
		switch (accion) {
		case PANEL_MENU:
			visualizarPanel(Principal.enumAcciones.PANEL_MENU);
			break;
		case PANEL_EJEMPLO:
			visualizarPanel(Principal.enumAcciones.PANEL_EJEMPLO);
			break;
		default:
			break;
		}
	}

	public void visualizarPanel(Principal.enumAcciones panel) {
		this.vistaPrincipal.visualizarPaneles(panel);
	}

}
