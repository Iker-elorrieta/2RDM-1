package modelo;
// Generated 14 ene 2025, 11:50:45 by Hibernate Tools 6.5.1.Final

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Ciclos generated by hbm2java
 */
public class Ciclos implements java.io.Serializable {

	private int id;
	private String nombre;
	private Set matriculacioneses = new HashSet(0);
	private Set moduloses = new HashSet(0);

	public Ciclos() {
	}
	

	public Ciclos(int id) {
		this.id = id;
	}

	public Ciclos(int id, String nombre, Set matriculacioneses, Set moduloses) {
		this.id = id;
		this.nombre = nombre;
		this.matriculacioneses = matriculacioneses;
		this.moduloses = moduloses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getMatriculacioneses() {
		return this.matriculacioneses;
	}

	public void setMatriculacioneses(Set matriculacioneses) {
		this.matriculacioneses = matriculacioneses;
	}

	public Set getModuloses() {
		return this.moduloses;
	}

	public void setModuloses(Set moduloses) {
		this.moduloses = moduloses;
	}

	public static String guardarCiclo(int id, String nombre, Session session) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Ciclos existingCiclo = session.get(Ciclos.class, id);
			if (existingCiclo != null) {
				return "El ciclo con ID " + id + " ya existe.";
			}

			Ciclos newCiclo = new Ciclos();
			newCiclo.setId(id);
			newCiclo.setNombre(nombre);

			session.save(newCiclo);

			tx.commit();
			return "";

		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

}