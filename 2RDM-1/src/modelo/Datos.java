package modelo;

import java.io.Serializable;

public class Datos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String operacion;
	private String nombre;
	private String username;
	private String contrasenna;
	
	public Datos() {}

	public Datos(String operacion, String nombre, String username, String contrasenna) {
		this.operacion = operacion;
		this.nombre = nombre;
		this.username = username;
		this.contrasenna = contrasenna;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContrasenna() {
		return contrasenna;
	}

	public void setContrasenna(String contrasenna) {
		this.contrasenna = contrasenna;
	}

}