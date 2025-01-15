package modelo;

import conexion.Servidor;

public class HiloServidor extends Thread {
	private Servidor s;
	private String operacion, user, name, pswd;
	private int resultado;

	public HiloServidor(Servidor s, String operacion, String user, String name, String pswd) {
		this.s = s;
		this.operacion = operacion;
		this.user = user;
		this.name = name;
		this.pswd = pswd;
		
	}

	@Override
	public void run() {
		if (operacion.equals("registro")) {
			// resultado = s.registro(user, name, pswd);
		} else if (operacion.equals("login")) {
			resultado = s.login(user, pswd);
		} else {
			System.out.println("Operación no reconocida.");
		}
	}

	public int getResultado() {
		return resultado;
	}
}
