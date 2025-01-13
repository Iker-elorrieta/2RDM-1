package principal;

import controlador.Controlador;

public class Main {

	public static void main(String[] args) {
		try {
			// Creamos el objeto vista (Principal)
			vista.Principal ventanaPrincipal = new vista.Principal();
			ventanaPrincipal.setVisible(true);

			// Crear el controlador con las referencias correctas a los paneles existentes
			new Controlador(ventanaPrincipal, ventanaPrincipal.getPanelMenu(), ventanaPrincipal.getPanelEjemplo());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
