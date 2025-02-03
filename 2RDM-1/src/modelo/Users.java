package modelo;
// Generated 14 ene 2025, 11:50:45 by Hibernate Tools 6.5.1.Final

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Users generated by hbm2java
 */
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Tipos tipos;
	private String email;
	private String username;
	private String password;
	private String nombre;
	private String apellidos;
	private String dni;
	private String direccion;
	private Integer telefono1;
	private Integer telefono2;
	private byte[] argazkia;
	private Set matriculacioneses = new HashSet(0);
	private Set reunionesesForProfesorId = new HashSet(0);
	private Set reunionesesForAlumnoId = new HashSet(0);
	private Set horarioses = new HashSet(0);

	public Users() {
	}

	public Users(int id, Tipos tipos) {
		this.id = id;
		this.tipos = tipos;
	}

	public Users(int id, Tipos tipos, String email, String username, String password, String nombre, String apellidos,
			String dni, String direccion, Integer telefono1, Integer telefono2, byte[] argazkia, Set matriculacioneses,
			Set reunionesesForProfesorId, Set reunionesesForAlumnoId, Set horarioses) {
		this.id = id;
		this.tipos = tipos;
		this.email = email;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.direccion = direccion;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.argazkia = argazkia;
		this.matriculacioneses = matriculacioneses;
		this.reunionesesForProfesorId = reunionesesForProfesorId;
		this.reunionesesForAlumnoId = reunionesesForAlumnoId;
		this.horarioses = horarioses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Tipos getTipos() {
		return this.tipos;
	}

	public void setTipos(Tipos tipos) {
		this.tipos = tipos;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getTelefono1() {
		return this.telefono1;
	}

	public void setTelefono1(Integer telefono1) {
		this.telefono1 = telefono1;
	}

	public Integer getTelefono2() {
		return this.telefono2;
	}

	public void setTelefono2(Integer telefono2) {
		this.telefono2 = telefono2;
	}

	public byte[] getArgazkia() {
		return this.argazkia;
	}

	public void setArgazkia(byte[] argazkia) {
		this.argazkia = argazkia;
	}

	public Set getMatriculacioneses() {
		return this.matriculacioneses;
	}

	public void setMatriculacioneses(Set matriculacioneses) {
		this.matriculacioneses = matriculacioneses;
	}

	public Set getReunionesesForProfesorId() {
		return this.reunionesesForProfesorId;
	}

	public void setReunionesesForProfesorId(Set reunionesesForProfesorId) {
		this.reunionesesForProfesorId = reunionesesForProfesorId;
	}

	public Set getReunionesesForAlumnoId() {
		return this.reunionesesForAlumnoId;
	}

	public void setReunionesesForAlumnoId(Set reunionesesForAlumnoId) {
		this.reunionesesForAlumnoId = reunionesesForAlumnoId;
	}

	public Set getHorarioses() {
		return this.horarioses;
	}

	public void setHorarioses(Set horarioses) {
		this.horarioses = horarioses;
	}

	public Users login(Session session) {
		String hql = "FROM Users WHERE username='" + username + "' AND password='" + password + "'";
		Query q = session.createQuery(hql);
		return (Users) q.uniqueResult();

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> todosUsers(Session session) {
	    String hql = "FROM Users";
	    Query q = session.createQuery(hql);
	    List<Object[]> usuariosTodosData = new ArrayList<>();

	    // Añadir este criterio para que no se repitan los usuarios en el combobox de otros horarios
	    q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	    List<Users> usuariosTodos = q.list();

	    for (Users usuario : usuariosTodos) {
	        // Crear un Object[] para almacenar los datos del usuario
	        Object[] usuarioData = new Object[] {
	            usuario.getId(),              // [0] --> ID
	            usuario.getUsername(),        // [1] --> Username
	            usuario.getNombre(),          // [2] --> Nombre
	            usuario.getEmail(),           // [3] --> Email
	            usuario.getTipos() != null ? usuario.getTipos().getId() : -1, // [4] --> Tipo de usuario
	            usuario.getPassword(),        // [5] --> Password (considera omitir por seguridad)
	            usuario.getApellidos(),       // [6] --> Apellidos
	            usuario.getDni(),             // [7] --> DNI
	            usuario.getDireccion(),       // [8] --> Dirección
	            usuario.getTelefono1() != null ? usuario.getTelefono1() : 0,  // [9] --> Telefono1
	            usuario.getTelefono2() != null ? usuario.getTelefono2() : 0   // [10] --> Telefono2
	        };

	        // Añadir el Object[] a la lista de datos de usuarios
	        usuariosTodosData.add(usuarioData);
	    }

	    return usuariosTodosData; // Devuelve la lista de usuarios como Object[]
	}


	@Override
	public String toString() {
		return nombre;
	}

	public void guardarImagen(Session session) {
		Transaction tx = null;
		tx = session.beginTransaction();
		Users a = new Users();
		a.setId(id);
		a.setArgazkia(argazkia);
		a.setApellidos("Actualizado");
		session.update(a);
		tx.commit();
		System.out.println("Actualizado");
	}

}