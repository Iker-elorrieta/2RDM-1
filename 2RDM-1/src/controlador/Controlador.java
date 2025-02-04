package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import vista.Principal;

import modelo.Tipos;
import modelo.Users;

public class Controlador implements ActionListener {

	private vista.Principal vistaPrincipal;
	private vista.PanelLogin vistaLogin;
	private vista.PanelMenu vistaMenu;
	private vista.PanelHorario vistaHorario;
	private vista.PanelOtrosHorarios vistaOtrosHorarios;
	private vista.PanelReuniones vistaReuniones;
	private vista.PanelReunionesPendientes vistaReunionesPendientes;

	private Socket socket = null;
	private int idUsuarioLogeado = 0;
	private LocalDate fechaSemanaActual = LocalDate.now()
			.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
	private int usuarioNoAdmitidoId = 4;

	private String ipCliente = "localhost";
	private final static String error = "Error", aviso = "Aviso", info = "Información";
	private final static String lunes = "L/A", martes = "M/A", miercoles = "X", jueves = "J/O", viernes = "V/O";
	private final static String lunesFull = "lunes", martesFull = "martes", miercolesFull = "miércoles",
			juevesFull = "jueves", viernesFull = "viernes";
	private final static String a = "aceptada", d = "denegada", p = "pendiente", c = "conflicto";
	private final static String btn = "btn";
	private final static String conectado = "Conectado al servidor.";
	private final static String loginRelleneCampos = "Por favor, rellene todos los campos.",
			loginIncorrecto = "Usuario o contraseña incorrectos",
			loginExclusivoProfes = "El uso de la aplicación es exclusivo de profesores",
			loginExitoso = "Inicio de sesión exitoso.", loginBienvenida = " Bienvenid@ ",
			loginError = "Error al iniciar sesión", horarioNoEncontrado = "No se han encontrado horarios para mostrar",
			horarioError = "Error al cargar el horario",
			reunionesNoEncontradas = "No se encontraron reuniones para mostrar.",
			reunionesEstadoConflicto = "Se le ha establecido el estado de" + c + "' a la reunión ID: ",
			reunionPorFavorSelecciona = "Por favor, selecciona una reunión.",
			reunionNoSePudoObtenerId = "No se pudo obtener el ID de la reunión.",
			reunionErrorAlModificar = "Error al modificar reunión: ", reunionId = "La reunión ID: ",
			reunionModificoCorrectamente = " se modificó correctamente.";
	private final boolean next = true;

	public Controlador(vista.Principal vistaPrincipal, vista.PanelLogin vistaLogin, vista.PanelMenu vistaMenu,
			vista.PanelHorario vistaHorario, vista.PanelOtrosHorarios vistaOtrosHorarios,
			vista.PanelReuniones vistaReuniones, vista.PanelReunionesPendientes vistaReunionesPendientes) {
		this.vistaPrincipal = vistaPrincipal;
		this.vistaLogin = vistaLogin;
		this.vistaMenu = vistaMenu;
		this.vistaHorario = vistaHorario;
		this.vistaOtrosHorarios = vistaOtrosHorarios;
		this.vistaReuniones = vistaReuniones;
		this.vistaReunionesPendientes = vistaReunionesPendientes;

		this.iniciarConexionConServidor();
		this.inicializarControlador();

	}

	/**
	 * Método que hace la conexion con el Servidor
	 */
	private void iniciarConexionConServidor() {
		int puerto = 2000;
		String ip = ipCliente;

		try {
			socket = new Socket(ip, puerto);
			System.out.println(conectado);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Controlador() {
	}

	private void inicializarControlador() {
		accionesVistaLogin();
		accionesVistaMenu();
		accionesVistaHorario();
		accionesVistaOtrosHorarios();
		accionesVistaReuniones();
		accionesVistaReunionesPendientes();
	}

	private void accionesVistaLogin() {
		this.vistaLogin.getBtnLogin().addActionListener(this);
		this.vistaLogin.getBtnLogin().setActionCommand(Principal.enumAcciones.LOGIN_INICIAR_SESION.toString());

		this.vistaLogin.getRootPane().setDefaultButton(this.vistaLogin.getBtnLogin());
	}

	private void accionesVistaMenu() {
		this.vistaMenu.getBtnHorario().addActionListener(this);
		this.vistaMenu.getBtnHorario().setActionCommand(Principal.enumAcciones.PANEL_HORARIO.toString());

		this.vistaMenu.getBtnOtrosHorarios().addActionListener(this);
		this.vistaMenu.getBtnOtrosHorarios().setActionCommand(Principal.enumAcciones.PANEL_OTROS_HORARIOS.toString());

		this.vistaMenu.getBtnReuniones().addActionListener(this);
		this.vistaMenu.getBtnReuniones().setActionCommand(Principal.enumAcciones.PANEL_REUNIONES.toString());

		this.vistaMenu.getBtnDesconectarse().addActionListener(this);
		this.vistaMenu.getBtnDesconectarse().setActionCommand(Principal.enumAcciones.PANEL_LOGIN.toString());
	}

	private void accionesVistaHorario() {
		this.vistaHorario.getBtnVolver().addActionListener(this);
		this.vistaHorario.getBtnVolver().setActionCommand(Principal.enumAcciones.PANEL_MENU.toString());
	}

	private void accionesVistaOtrosHorarios() {
		this.vistaOtrosHorarios.getProfesComboBox().addActionListener(this);
		this.vistaOtrosHorarios.getProfesComboBox()
				.setActionCommand(Principal.enumAcciones.CARGAR_TABLA_OTROS_HORARIOS.toString());

		this.vistaOtrosHorarios.getBtnVolver().addActionListener(this);
		this.vistaOtrosHorarios.getBtnVolver().setActionCommand(Principal.enumAcciones.PANEL_MENU.toString());
	}

	private void accionesVistaReuniones() {
		this.vistaReuniones.getBtnVolver().addActionListener(this);
		this.vistaReuniones.getBtnVolver().setActionCommand(Principal.enumAcciones.PANEL_MENU.toString());

		this.vistaReuniones.getBtnNextWeek().addActionListener(this);
		this.vistaReuniones.getBtnNextWeek()
				.setActionCommand(Principal.enumAcciones.PANEL_REUNIONES_NEXT_WEEK.toString());

		this.vistaReuniones.getBtnPreviousWeek().addActionListener(this);
		this.vistaReuniones.getBtnPreviousWeek()
				.setActionCommand(Principal.enumAcciones.PANEL_REUNIONES_PREVIOUS_WEEK.toString());

		this.vistaReuniones.getBtnPendientes().addActionListener(this);
		this.vistaReuniones.getBtnPendientes()
				.setActionCommand(Principal.enumAcciones.PANEL_REUNIONES_PENDIENTES.toString());

	}

	private void accionesVistaReunionesPendientes() {
		this.vistaReunionesPendientes.getBtnVolver().addActionListener(this);
		this.vistaReunionesPendientes.getBtnVolver()
				.setActionCommand(Principal.enumAcciones.PANEL_REUNIONES.toString());

		this.vistaReunionesPendientes.getBtnAceptar().addActionListener(this);
		this.vistaReunionesPendientes.getBtnAceptar()
				.setActionCommand(Principal.enumAcciones.PANEL_REUNIONES_ACEPTAR.toString());

		this.vistaReunionesPendientes.getBtnRechazar().addActionListener(this);
		this.vistaReunionesPendientes.getBtnRechazar()
				.setActionCommand(Principal.enumAcciones.PANEL_REUNIONES_RECHAZAR.toString());
	}

	public void actionPerformed(ActionEvent e) {
		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());
		switch (accion) {
		case PANEL_LOGIN:
			visualizarPanel(Principal.enumAcciones.PANEL_LOGIN);
			break;
		case LOGIN_INICIAR_SESION:
			login();
			break;
		case PANEL_MENU:
			visualizarPanel(Principal.enumAcciones.PANEL_MENU);
			break;
		case PANEL_HORARIO:
			this.vistaPrincipal.getPanelHorario().getModeloHorario().setRowCount(0);
			visualizarPanel(Principal.enumAcciones.PANEL_HORARIO);
			cargarHorarioProfe();
			break;
		case PANEL_OTROS_HORARIOS:
			visualizarPanel(Principal.enumAcciones.PANEL_OTROS_HORARIOS);
			mCargarTodosUsuarios();
			mCargarDatosOtrosHorarios();
			break;
		case CARGAR_TABLA_OTROS_HORARIOS:
			this.vistaPrincipal.getPanelOtrosHorarios().getModeloOtrosHorarios().setRowCount(0);
			mCargarDatosOtrosHorarios();
			break;
		case PANEL_REUNIONES:
			this.vistaPrincipal.getPanelReuniones().getLblFecha().setText(fechaSemanaActual + "");
			this.vistaPrincipal.getPanelReuniones().getModeloReuniones().setRowCount(0);
			visualizarPanel(Principal.enumAcciones.PANEL_REUNIONES);
			mCargarReuniones();
			break;
		case PANEL_REUNIONES_NEXT_WEEK:
			cambiarSemana(next);
			break;
		case PANEL_REUNIONES_PREVIOUS_WEEK:
			cambiarSemana(!next);
			break;
		case PANEL_REUNIONES_ACEPTAR:
			modificarReunion(a, btn);
			break;
		case PANEL_REUNIONES_RECHAZAR:
			modificarReunion(d, btn);
			break;
		case PANEL_REUNIONES_PENDIENTES:
			visualizarPanel(Principal.enumAcciones.PANEL_REUNIONES_PENDIENTES);
			mCargarReuniones();
			break;
		default:
			break;
		}
	}

	public void visualizarPanel(Principal.enumAcciones panel) {
		this.vistaPrincipal.visualizarPaneles(panel);
	}

	/**
	 * Metodo que envia al Servidor las credenciales del usuario cifradas y el
	 * Servidor le devuelve el Objeto del Usuario entero.
	 */
	public void login() {
		String user = vistaLogin.getTxtFUser().getText();
		String pswd = new String(vistaLogin.getPswdFPassword().getPassword());

		if (user.isEmpty() || pswd.isEmpty()) {
			JOptionPane.showMessageDialog(null, loginRelleneCampos, aviso, JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			ObjectOutputStream salidaLogin = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entradaLogin = new ObjectInputStream(socket.getInputStream());

			String[] datosLogin = { vista.Principal.enumAccionesHiloServidor.LOGIN.name(), hash(user), hash(pswd) };

			salidaLogin.writeObject(datosLogin);

			String[] datosUsuario = (String[]) entradaLogin.readObject();

			if (datosUsuario == null || datosUsuario[0].equals("-1")) {
				// Login incorrecto, si usuario y/o contraseña incorrectos, muestra aviso
				JOptionPane.showMessageDialog(null, loginIncorrecto, error, JOptionPane.ERROR_MESSAGE);

			} else if (Integer.parseInt(datosUsuario[1]) == usuarioNoAdmitidoId) {
				// Login correcto - Tipo usuario NO admitido, solo pueden logearse profesores
				JOptionPane.showMessageDialog(null, loginExclusivoProfes, error, JOptionPane.ERROR_MESSAGE);

			} else {
				// Login correcto - Tipo usuario admitido, se logea correctamente
				idUsuarioLogeado = Integer.parseInt(datosUsuario[0]);

				JOptionPane.showMessageDialog(null, loginBienvenida + datosUsuario[2], loginExitoso,
						JOptionPane.INFORMATION_MESSAGE);

				ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
						Principal.enumAcciones.PANEL_MENU.toString());

				actionPerformed(e);

			}

		} catch (IOException | NoSuchAlgorithmException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, loginError, error, JOptionPane.ERROR_MESSAGE);
		}

		clearLogin();
	}

	/**
	 * Limpia los Texts Fields de login
	 */
	public void clearLogin() {
		vistaLogin.getTxtFUser().setText("");
		vistaLogin.getPswdFPassword().setText("");
	}

	/**
	 * Cifra el String que se le envia
	 * 
	 * @param respuesta String sin cifrar que recibe
	 * @return Devuelve el string cifrado
	 * @throws NoSuchAlgorithmException
	 */
	public static String hash(String respuesta) throws NoSuchAlgorithmException {
		StringBuilder resumenString = new StringBuilder();
		MessageDigest md = MessageDigest.getInstance("SHA");

		byte[] dataBytes = respuesta.getBytes();
		md.update(dataBytes);

		byte[] resumen = md.digest();

		for (byte b : resumen)
			resumenString.append(String.format("%02x", b));

		return resumenString.toString();
	}

	/**
	 * Carga los horarios del usuario logeado enviandole el id al servidor y le
	 * devuelve una Lista de los Horarios del Usuario
	 */
	public void cargarHorarioProfe() {
		cargarTablaHorario(idUsuarioLogeado, this.vistaPrincipal.getPanelHorario().getModeloHorario());

		/**
		 * EN EL PANEL HORARIOS HAY UN RENDER QUE SE ENCARGA DE ADAPTAR EL TEXTO A LA
		 * CELDA
		 */
	}

	/**
	 * Metodo que recoge del Servidor una Lista de todos los usuarios, se cargan los
	 * nombres de los diferentes profesores
	 */
	@SuppressWarnings("unchecked")
	private void mCargarTodosUsuarios() {
		try {
			ObjectOutputStream salidaUsuarios = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entradaUsuarios = new ObjectInputStream(socket.getInputStream());

			String[] datosTodosUsuarios = { vista.Principal.enumAccionesHiloServidor.TODOSUSUARIOS.name() };
			salidaUsuarios.writeObject(datosTodosUsuarios);

			// Se recibe la lista de usuarios como una lista de arrays de objetos.
			List<Object[]> usuariosData = (List<Object[]>) entradaUsuarios.readObject();

			// Si la lista está vacía o es nula, se muestra un mensaje y se detiene el
			// proceso.
			if (usuariosData == null || usuariosData.isEmpty()) {
				System.out.println("No se recibieron usuarios.");
				return;
			}

			// Se crea una lista para almacenar los objetos de tipo Users.
			List<Users> usuarios = new ArrayList<>();

			// Se recorre la lista recibida y se transforman los datos en objetos Users.
			for (Object[] usuarioData : usuariosData) {
				Users us = new Users();
				us.setId((Integer) usuarioData[0]); // ID del usuario
				us.setUsername((String) usuarioData[1]); // Nombre de usuario
				us.setNombre((String) usuarioData[2]); // Nombre real
				us.setEmail((String) usuarioData[3]); // Email

				// Se crea un objeto Tipos y se le asigna su ID.
				Tipos tipo = new Tipos();
				tipo.setId((Integer) usuarioData[4]);
				us.setTipos(tipo); // Se asigna el tipo de usuario.

				us.setPassword((String) usuarioData[5]); // Contraseña
				us.setApellidos((String) usuarioData[6]); // Apellidos
				us.setDni((String) usuarioData[7]); // DNI
				us.setDireccion((String) usuarioData[8]); // Dirección
				us.setTelefono1((Integer) usuarioData[9]); // Teléfono 1
				us.setTelefono2((Integer) usuarioData[10]);// Teléfono 2

				// Se agrega el usuario a la lista de usuarios.
				usuarios.add(us);
			}

			if (this.vistaPrincipal.getPanelOtrosHorarios().getProfesComboBox().getItemCount() == 0) {
				// Se recorre la lista de usuarios y se añaden al ComboBox, excepto los de un
				// tipo específico.
				for (Users us : usuarios) {
					if (us.getTipos().getId() != usuarioNoAdmitidoId) {
						this.vistaPrincipal.getPanelOtrosHorarios().getProfesComboBox().addItem(us);
					}
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Envia el id del usuario para luego recibir del servidor una Lista de los
	 * Otros Horarios y mostrarlos en una tabla.
	 */
	private void mCargarDatosOtrosHorarios() {
		// Se obtiene el usuario seleccionado del combo box, se muestra el horario del
		// profesor elegido
		Users usuarioElegido = (Users) this.vistaPrincipal.getPanelOtrosHorarios().getProfesComboBox()
				.getSelectedItem();

		cargarTablaHorario(usuarioElegido.getId(),
				this.vistaPrincipal.getPanelOtrosHorarios().getModeloOtrosHorarios());

		/**
		 * EN EL PANEL OTROS HORARIOS HAY UN RENDER QUE SE ENCARGA DE ADAPTAR EL TEXTO A
		 * LA CELDA
		 */
	}

	/**
	 * Método que recibe los parámetros necesarios para crear la tabla de horarios
	 * del usuario seleccionado. Funciona para los dos paneles de horarios (Horarios
	 * y OtrosHorarios).
	 * 
	 * @param accion
	 * @param userId
	 * @param modelo
	 */
	@SuppressWarnings("unchecked")
	private void cargarTablaHorario(int userId, DefaultTableModel modelo) {
		try {
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

			String[] horario = { vista.Principal.enumAccionesHiloServidor.HORARIO.name(), String.valueOf(userId) };
			salida.writeObject(horario);

			List<Object[]> horarios = (List<Object[]>) entrada.readObject();

			if (horarios == null || horarios.isEmpty()) {
				JOptionPane.showMessageDialog(null, horarioNoEncontrado, aviso, JOptionPane.WARNING_MESSAGE);
				return;
			}

			modelo.setRowCount(6);
			modelo.setColumnCount(6);

			for (int h = 0; h < 6; h++)
				modelo.setValueAt("Hora " + (h + 1), h, 0);

			for (Object[] horarioData : horarios) {
				String dia = (String) horarioData[0];
				int hora = Integer.parseInt((String) horarioData[1]) - 1;
				String modulo = (String) horarioData[2];

				switch (dia) {
				case lunes:
					modelo.setValueAt(modulo, hora, 1);
					break;
				case martes:
					modelo.setValueAt(modulo, hora, 2);
					break;
				case miercoles:
					modelo.setValueAt(modulo, hora, 3);
					break;
				case jueves:
					modelo.setValueAt(modulo, hora, 4);
					break;
				case viernes:
					modelo.setValueAt(modulo, hora, 5);
					break;
				default:
					break;
				}

			}

		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, horarioError, error, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Carga la tabla de reuniones la cual está formada por el Horario y las
	 * Reuniones del profesor que esté usando la aplicación.
	 */
	private void mCargarReuniones() {
		// Carga el horario del profesor.
		cargarTablaHorario(idUsuarioLogeado, this.vistaPrincipal.getPanelReuniones().getModeloReuniones());

		try {
			ObjectOutputStream sReuniones = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream eReuniones = new ObjectInputStream(socket.getInputStream());

			// Carga las reuniones del profesor logeado
			String[] datosReuniones = { vista.Principal.enumAccionesHiloServidor.REUNIONES.name(),
					String.valueOf(idUsuarioLogeado) };

			sReuniones.writeObject(datosReuniones);

			@SuppressWarnings("unchecked")
			List<Object[]> reuniones = (List<Object[]>) eReuniones.readObject();

			// Si no se obtiene ninguna reunión muestra un aviso y vuelve al menú.
			if (reuniones == null || reuniones.isEmpty()) {
				JOptionPane.showMessageDialog(null, reunionesNoEncontradas, aviso, JOptionPane.WARNING_MESSAGE);

				ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
						Principal.enumAcciones.PANEL_MENU.toString());

				actionPerformed(e);
			}
			// Se leen los centros a través JSON, se carga un mapa de centros con IDs y
			// nombres
			Map<Integer, String> mapaCentros = cargarCentros();

			// Se obtiene la fecha de la semana actual desde el label
			LocalDate fechaSemanaActual = LocalDate.parse(this.vistaReuniones.getLblFecha().getText());

			// Se determina el inicio y fin de la semana actual.
			LocalDate inicioSemana = fechaSemanaActual.with(DayOfWeek.MONDAY);
			LocalDate finSemana = fechaSemanaActual.with(DayOfWeek.SUNDAY);

			// Array bidimensional de Strings para almacenar reuniones aceptadas.
			String[][] dataAceptadas = new String[6][6];

			// Lista para almacenar reuniones pendientes, representadas como arrays de
			// Strings.
			List<String[]> dataPendientes = new ArrayList<>();

			// Se declara un mapa para almacenar los colores de las celdas de reuniones
			// aceptadas.
			// La clave es un objeto Point (para referenciar rowIndex y columnIndex).
			// El valor es un Color para representar visualmente el estado de la reunión.
			Map<Point, Color> cellColorsAceptadas = new HashMap<>();

			// Se declara otro mapa similar, pero para las reuniones pendientes.
			Map<Point, Color> cellColorsPendientes = new HashMap<>();

			int rowPendiente = 0;

			for (int h = 0; h < 6; h++)
				dataAceptadas[h][0] = "Hora " + (h + 1);

			for (Object[] reunionData : reuniones) {
				// Convierte la fecha en un array con [año, mes,día].
				int[] fecha = formatearFecha(reunionData[3].toString());

				// Se crea un objeto LocalDate con los valores obtenidos (año, mes y día).
				LocalDate localDate = LocalDate.of(fecha[0], fecha[1], fecha[2]);

				// Se obtiene el nombre completo del día de la semana en español.
				String dia = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es"));

				// Se obtiene el nombre del centro mapaCentros.getOrDefault(...) busca el
				// centro en mapaCentros usando el ID almacenado en reunionData[4].
				// Si no lo encuentra, devuelve "Desconocido".
				String nombreCentro = mapaCentros.getOrDefault(Integer.parseInt(reunionData[4].toString()),
						"Desconocido");

				// Se construye una cadena con la información de la reunión.
				String reunionInfo = reunionData[5] + "\n" + nombreCentro + "\n Aula: " + reunionData[1];

				int rowIndex = fecha[6] - 1;
				int columnIndex = switch (dia.toLowerCase()) {
				case lunesFull -> 1;
				case martesFull -> 2;
				case miercolesFull -> 3;
				case juevesFull -> 4;
				case viernesFull -> 5;
				default -> -1;
				};

				if (columnIndex != -1) {
					switch (reunionData[2].toString().toLowerCase()) {
					case a:
						if (!localDate.isBefore(inicioSemana) && !localDate.isAfter(finSemana)) {
							Object a = this.vistaPrincipal.getPanelReuniones().getModeloReuniones().getValueAt(rowIndex,
									columnIndex);

							// Si 'a' no es nulo, significa que en esa celda hay contenido, por lo que se
							// entiende que ese día a esa hora el profesor está ocupado (tiene clase).
							// Por lo tanto, se llama al método modificar reunión enviándole el id de la
							// misma y el estado de conflicto
							if (a != null) {
								// Se actualizan las reuniones mostradas
								modificarReunion(c, reunionData[6].toString());

								JOptionPane.showMessageDialog(null,
										reunionesEstadoConflicto + reunionData[6] + " (" + reunionData[5] + ")", aviso,
										JOptionPane.WARNING_MESSAGE);
								mCargarReuniones();
								return;
							} else {
								this.vistaPrincipal.getPanelReuniones().getModeloReuniones().setValueAt(reunionInfo,
										rowIndex, columnIndex);
								cellColorsAceptadas.put(new Point(rowIndex, columnIndex), Color.GREEN);
							}
						}
						break;
					case d:
						break;
					// Los siguientes cases se encargan de guardar las reuniones
					// pendientes/en conflicto para posteriormente cargarlas en la tabla de
					// reuniones pendientes
					case p:
					case c:
						String[] reunionPendiente = new String[6];
						reunionPendiente[0] = reunionData[6].toString();
						reunionPendiente[1] = reunionData[5].toString();
						reunionPendiente[2] = reunionData[0].toString();
						reunionPendiente[3] = nombreCentro;
						reunionPendiente[4] = localDate.toString();
						reunionPendiente[5] = reunionData[2].toString();

						dataPendientes.add(reunionPendiente);
						Color color = Color.WHITE;

						if (reunionData[2].toString().equalsIgnoreCase(p))
							color = Color.YELLOW;
						else
							color = Color.GRAY.brighter();

						for (int col = 0; col <= 5; col++)
							cellColorsPendientes.put(new Point(rowPendiente, col), color);

						rowPendiente++;

						break;
					}
				}
			}

			this.vistaPrincipal.getPanelReunionesPendientes().getModeloReuniones().setRowCount(0);

			for (String[] row : dataPendientes)
				this.vistaPrincipal.getPanelReunionesPendientes().getModeloReuniones().addRow(row);

			this.vistaPrincipal.getPanelReuniones().setCellColors(cellColorsAceptadas);
			this.vistaPrincipal.getPanelReunionesPendientes().setCellColors(cellColorsPendientes);

			renderTable(cellColorsAceptadas, true, this.vistaPrincipal.getPanelReuniones().getTablaReuniones());
			renderTable(cellColorsPendientes, true,
					this.vistaPrincipal.getPanelReunionesPendientes().getTablaReuniones());

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Llama al método que lee el JSON del HiloServidor y convierte los datos
	 * obtenidos en un Map, donde se relaciona cada centro con su ID. Se usa el Map
	 * porque es más eficiente
	 * 
	 * @return Map<Integer, String> mapaCentros
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Map<Integer, String> cargarCentros() throws IOException, ClassNotFoundException {

		ObjectOutputStream sCentros = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream eCentros = new ObjectInputStream(socket.getInputStream());

		String[] datosCentros = { vista.Principal.enumAccionesHiloServidor.OBTENERCENTROS.name() };
		sCentros.writeObject(datosCentros);

		@SuppressWarnings("unchecked")
		List<Object[]> listaCentros = (List<Object[]>) eCentros.readObject();

		Map<Integer, String> mapaCentros = new HashMap<>();
		for (Object[] centro : listaCentros) {
			int idCentro = (int) centro[0];
			String nombreCentro = (String) centro[1];
			mapaCentros.put(idCentro, nombreCentro);
		}

		return mapaCentros;

	}

	/**
	 * Obtiene la fecha de la reunión y la separa en un array.
	 * 
	 * @param string
	 * @return int[] resultado
	 */
	private int[] formatearFecha(String string) {

		String[] partesFechaHora = string.toString().split(" ");

		String soloFecha = partesFechaHora[0];
		String soloHora = partesFechaHora[1];

		String[] date = soloFecha.split("-");
		String[] time = soloHora.split(":");

		int[] resultado = new int[7];
		resultado[0] = Integer.parseInt(date[0]); // Año
		resultado[1] = Integer.parseInt(date[1]); // Mes
		resultado[2] = Integer.parseInt(date[2]); // Día
		resultado[3] = Integer.parseInt(time[0]); // Hora
		resultado[4] = Integer.parseInt(time[1]); // Minutos
		resultado[5] = Integer.parseInt(time[2].split("\\.")[0]); // Segundos (eliminar ".0")

		for (int i = 0; i <= 8; i++) {
			if (Integer.parseInt(time[0]) - 7 == i) {
				resultado[6] = i;
				i = 8;
			}
		}

		return resultado;
	}

	/**
	 * Avanza o retrocede una semana según lo que el usuario indique en el
	 * panelReuniones para visualizar sus reuniones.
	 * 
	 * @param aceptar2
	 */
	private void cambiarSemana(boolean aceptar) {
		LocalDate fechaActual = LocalDate.parse(this.vistaReuniones.getLblFecha().getText());

		if (aceptar)
			this.vistaReuniones.getLblFecha().setText(fechaActual.plusWeeks(1) + "");
		else
			this.vistaReuniones.getLblFecha().setText(fechaActual.minusWeeks(1) + "");

		this.vistaPrincipal.getPanelReuniones().getModeloReuniones().setRowCount(0);

		mCargarReuniones();
	}

	/**
	 * Se encarga de aplicar todos los colores de las tablas y de adaptar el texto
	 * al espacio de la celda.
	 * 
	 * @param cellColors
	 * @param reuniones
	 * @param tabla
	 */
	private void renderTable(Map<Point, Color> cellColors, boolean reuniones, JTable tabla) {
		tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				label.setText(value != null ? "<html>" + value.toString().replace("\n", "<br>") + "</html>" : "");
				label.setOpaque(true);
				label.setHorizontalAlignment(JLabel.LEFT);
				label.setVerticalAlignment(JLabel.TOP);
				if (reuniones)
					label.setBackground(cellColors.getOrDefault(new Point(row, column), Color.WHITE));
				return label;
			}
		});
	}

	/**
	 * Modifica la reunión que se le envía por parámetro (o la que selecciona el
	 * usuario cuando se le manda "btn" al método) al estado que también se le envía
	 * por parámetro.
	 * 
	 * @param estado
	 * @param id
	 */
	private void modificarReunion(String estado, String id) {
		String idReunion = String.valueOf(id);

		// se le envia -1 si queremos que haga las acciones de los botones aceptar o
		// rechazar, sino, obtiene el id que le estamos mandando
		if (id.equalsIgnoreCase("btn")) {
			JTable tabla = this.vistaPrincipal.getPanelReunionesPendientes().getTablaReuniones();

			int filaSeleccionada = tabla.getSelectedRow();
			if (filaSeleccionada != -1) {
				idReunion = tabla.getValueAt(filaSeleccionada, 0).toString().trim(); // Columna "ID"
			} else {
				JOptionPane.showMessageDialog(null, reunionPorFavorSelecciona, aviso, JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		if (idReunion.isEmpty()) {
			JOptionPane.showMessageDialog(null, reunionNoSePudoObtenerId, aviso, JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

			String[] datosReunion = { vista.Principal.enumAccionesHiloServidor.MODIFICARREUNION.name(), idReunion,
					estado };

			salida.writeObject(datosReunion);

			boolean resultado = (boolean) entrada.readObject();

			if (resultado) {
				if (id.equalsIgnoreCase(btn)) {
					JOptionPane.showMessageDialog(null, reunionId + idReunion + reunionModificoCorrectamente, info,
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, reunionErrorAlModificar, error, JOptionPane.ERROR_MESSAGE);
			}
			mCargarReuniones();

		} catch (IOException | ClassNotFoundException e) {
			System.err.println(reunionErrorAlModificar + e.getMessage());
			e.printStackTrace();
		}

	}

}