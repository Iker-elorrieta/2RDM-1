package modelo;
// Generated 14 ene 2025, 11:50:45 by Hibernate Tools 6.5.1.Final

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Reuniones generated by hbm2java
 */
public class Reuniones implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idReunion;
	private Users usersByProfesorId;
	private Users usersByAlumnoId;
	private String estado;
	private String estadoEus;
	private String idCentro;
	private String titulo;
	private String asunto;
	private String aula;
	private Timestamp fecha;

	public Reuniones() {
	}

	public Reuniones(Users usersByProfesorId, Users usersByAlumnoId) {
		this.usersByProfesorId = usersByProfesorId;
		this.usersByAlumnoId = usersByAlumnoId;
	}

	public Reuniones(Users usersByProfesorId, Users usersByAlumnoId, String estado, String estadoEus, String idCentro,
			String titulo, String asunto, String aula, Timestamp fecha) {
		this.usersByProfesorId = usersByProfesorId;
		this.usersByAlumnoId = usersByAlumnoId;
		this.estado = estado;
		this.estadoEus = estadoEus;
		this.idCentro = idCentro;
		this.titulo = titulo;
		this.asunto = asunto;
		this.aula = aula;
		this.fecha = fecha;
	}

	public Integer getIdReunion() {
		return this.idReunion;
	}

	public void setIdReunion(Integer idReunion) {
		this.idReunion = idReunion;
	}

	public Users getUsersByProfesorId() {
		return this.usersByProfesorId;
	}

	public void setUsersByProfesorId(Users usersByProfesorId) {
		this.usersByProfesorId = usersByProfesorId;
	}

	public Users getUsersByAlumnoId() {
		return this.usersByAlumnoId;
	}

	public void setUsersByAlumnoId(Users usersByAlumnoId) {
		this.usersByAlumnoId = usersByAlumnoId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoEus() {
		return this.estadoEus;
	}

	public void setEstadoEus(String estadoEus) {
		this.estadoEus = estadoEus;
	}

	public String getIdCentro() {
		return this.idCentro;
	}

	public void setIdCentro(String idCentro) {
		this.idCentro = idCentro;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAsunto() {
		return this.asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getAula() {
		return this.aula;
	}

	public void setAula(String aula) {
		this.aula = aula;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	@SuppressWarnings("unchecked")
	public List<Reuniones> getReunionesById(int i) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
	    Session session = sesion.openSession();
	    
	    String hql = "FROM Reuniones r JOIN FETCH r.usersByProfesorId WHERE r.usersByProfesorId.id = :id";
	    Query query = session.createQuery(hql);
	    query.setParameter("id", i);
	    
	    List<Reuniones> reuniones = query.list();
	    
	    
	    return reuniones;

	}

	public boolean modificarReunion(int id, String estado) {
		Transaction tx = null;
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		boolean exito = false;

		try {
			tx = session.beginTransaction();

			String hql = "FROM Reuniones WHERE idReunion = :id";
			Query q = session.createQuery(hql);
			q.setParameter("id", id);

			Reuniones reunion = (Reuniones) q.uniqueResult();

			if (reunion != null) {
				reunion.setEstado(estado);
				session.update(reunion);
				tx.commit();
				exito = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();

		}

		return exito;
	}

}