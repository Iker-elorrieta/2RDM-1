package vista;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {
	private static final long serialVersionUID = 1L;

	public static enum enumAcciones {
		PANEL_LOGIN, LOGIN_INICIAR_SESION, PANEL_MENU
	}

	private JPanel panelContenedor;
	private PanelLogin panelLogin;
	private PanelMenu panelMenu;

	public Principal() {

		crearPanelContenedor();
		crearPanelLogin();
		crearPanelMenu();

		// Mostrar el panel de login al inicio.
		visualizarPaneles(enumAcciones.PANEL_LOGIN);
	}

	private void crearPanelContenedor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 900, 600);
		panelContenedor = new JPanel();
		panelContenedor.setBackground(new Color(141, 204, 235));
		panelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelContenedor);
		panelContenedor.setLayout(null);
	}

	private void crearPanelLogin() {
		panelLogin = new PanelLogin();
		panelLogin.setBounds(0, 0, 884, 561);
		panelContenedor.add(panelLogin);
		panelLogin.setVisible(false); // Ocultar inicialmente

	}

	private void crearPanelMenu() {
		panelMenu = new PanelMenu();
		panelMenu.setBounds(0, 0, 884, 561);
		panelContenedor.add(panelMenu);
		panelMenu.setVisible(false); // Ocultar inicialmente

	}

	public void visualizarPaneles(enumAcciones panel) {
		// Ocultar ambos paneles primero.
		panelLogin.setVisible(false);
		panelMenu.setVisible(false);

		switch (panel) {
		case PANEL_LOGIN:
			panelLogin.setVisible(true);
			break;
		case PANEL_MENU:
			panelMenu.setVisible(true);
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

}
