package principal;

import controlador.Controlador;

public class Main {

	public static void main(String[] args) {
		try {
			vista.Principal ventanaPrincipal = new vista.Principal();
			ventanaPrincipal.setVisible(true);

			new Controlador(ventanaPrincipal, ventanaPrincipal.getPanelLogin(), ventanaPrincipal.getPanelMenu(),
					ventanaPrincipal.getPanelHorario(), ventanaPrincipal.getPanelOtrosHorarios(),
					ventanaPrincipal.getPanelReuniones(), ventanaPrincipal.getPanelReunionesPendientes());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}