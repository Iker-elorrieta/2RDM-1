package vista;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {
	private static final long serialVersionUID = 1L;

	public static enum enumAcciones {
		PANEL_LOGIN, LOGIN_INICIAR_SESION, PANEL_MENU, PANEL_HORARIO, PANEL_OTROS_HORARIOS, PANEL_REUNIONES,
		CARGAR_TABLA_OTROS_HORARIOS, PANEL_REUNIONES_NEXT_WEEK, PANEL_REUNIONES_PREVIOUS_WEEK
	}

	public static enum enumAccionesHiloServidor {
		LOGIN, HORARIO, TODOSUSUARIOS, REUNIONES, OBTENERCENTROS;
	}

	private JPanel panelContenedor;
	private PanelLogin panelLogin;
	private PanelMenu panelMenu;
	private PanelHorario panelHorario;
	private PanelOtrosHorarios panelOtrosHorarios;
	private PanelReuniones panelReuniones;

	public Principal() {

		crearPanelContenedor();
		crearPanelLogin();
		crearPanelMenu();
		crearPanelHorario();
		crearPanelOtrosHorarios();
		crearPanelReuniones();

		// Mostrar el panel de login al inicio.
		visualizarPaneles(enumAcciones.PANEL_LOGIN);
	}

	private void crearPanelContenedor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1068, 691);
		panelContenedor = new JPanel();
		panelContenedor.setBackground(new Color(220, 220, 220));
		panelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelContenedor);
		panelContenedor.setLayout(null);
	}

	private void crearPanelLogin() {
		panelLogin = new PanelLogin();
		panelLogin.setBounds(0, 0, 1050, 650);
		panelContenedor.add(panelLogin);
		panelLogin.setVisible(false);

	}

	private void crearPanelMenu() {
		panelMenu = new PanelMenu();
		panelMenu.setBounds(0, 0, 1050, 650);
		panelContenedor.add(panelMenu);
		panelMenu.setVisible(false);

	}

	private void crearPanelHorario() {
		panelHorario = new PanelHorario();
		panelHorario.setBounds(0, 0, 1050, 650);
		panelContenedor.add(panelHorario);
		panelHorario.setVisible(false);

	}

	private void crearPanelOtrosHorarios() {
		panelOtrosHorarios = new PanelOtrosHorarios();
		panelOtrosHorarios.setBounds(0, 0, 1050, 650);
		panelContenedor.add(panelOtrosHorarios);
		panelOtrosHorarios.setVisible(false);

	}

	private void crearPanelReuniones() {
		panelReuniones = new PanelReuniones();
		panelReuniones.setBounds(0, 0, 1050, 650);
		panelContenedor.add(panelReuniones);
		panelReuniones.setVisible(false);

	}

	public void visualizarPaneles(enumAcciones panel) {
		// Ocultar ambos paneles primero.
		panelLogin.setVisible(false);
		panelMenu.setVisible(false);
		panelHorario.setVisible(false);
		panelOtrosHorarios.setVisible(false);
		panelReuniones.setVisible(false);

		switch (panel) {
		case PANEL_LOGIN:
			panelLogin.setVisible(true);
			break;
		case PANEL_MENU:
			panelMenu.setVisible(true);
			break;
		case PANEL_HORARIO:
			panelHorario.setVisible(true);
			break;
		case PANEL_OTROS_HORARIOS:
			panelOtrosHorarios.setVisible(true);
			break;
		case PANEL_REUNIONES:
			panelReuniones.setVisible(true);
			break;
		default:
			break;

		}
	}

	public PanelLogin getPanelLogin() {
		return panelLogin;
	}

	public void setPanelLogin(PanelLogin panelLogin) {
		this.panelLogin = panelLogin;
	}

	public PanelMenu getPanelMenu() {
		return panelMenu;
	}

	public void setPanelMenu(PanelMenu panelMenu) {
		this.panelMenu = panelMenu;
	}

	public PanelHorario getPanelHorario() {
		return panelHorario;
	}

	public void setPanelHorario(PanelHorario panelHorario) {
		this.panelHorario = panelHorario;
	}

	public PanelOtrosHorarios getPanelOtrosHorarios() {
		return panelOtrosHorarios;
	}

	public void setPanelOtrosHorarios(PanelOtrosHorarios panelOtrosHorarios) {
		this.panelOtrosHorarios = panelOtrosHorarios;
	}

	public PanelReuniones getPanelReuniones() {
		return panelReuniones;
	}

	public void setPanelReuniones(PanelReuniones panelReuniones) {
		this.panelReuniones = panelReuniones;
	}
}