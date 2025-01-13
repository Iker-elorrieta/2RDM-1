package vista;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {
	private static final long serialVersionUID = 1L;

	public static enum enumAcciones {
		PANEL_MENU, PANEL_EJEMPLO
	}

	private JPanel panelContenedor;
	private PanelMenu panelMenu;
	private PanelEjemplo panelEjemplo;

	public Principal() {

		crearPanelContenedor();
		crearPanelMenu();
		crearPanelEjemplo();

		// Mostrar el panel de login al inicio.
		visualizarPaneles(enumAcciones.PANEL_MENU);
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

	private void crearPanelMenu() {
		panelMenu = new PanelMenu();
		panelContenedor.add(panelMenu);
		panelMenu.setVisible(false); // Ocultar inicialmente

	}

	private void crearPanelEjemplo() {
		panelEjemplo = new PanelEjemplo();
		panelContenedor.add(panelEjemplo);
		panelEjemplo.setVisible(false); // Ocultar inicialmente

	}

	public void visualizarPaneles(enumAcciones panel) {
		// Ocultar ambos paneles primero.
		panelMenu.setVisible(false);
		panelEjemplo.setVisible(false);

		switch (panel) {
		case PANEL_MENU:
			panelMenu.setVisible(true);
			break;
		case PANEL_EJEMPLO:
			panelEjemplo.setVisible(true);
			break;
		default:
			break;

		}
	}

	public PanelMenu getPanelMenu() {
		return panelMenu;
	}

	public void setPanelMenu(PanelMenu panelMenu) {
		this.panelMenu = panelMenu;
	}

	public PanelEjemplo getPanelEjemplo() {
		return panelEjemplo;
	}

	public void setPanelEjemplo(PanelEjemplo panelEjemplo) {
		this.panelEjemplo = panelEjemplo;
	}

}
