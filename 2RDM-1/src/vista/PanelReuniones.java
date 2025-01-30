package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

public class PanelReuniones extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnVolver, btnNextWeek, btnPreviousWeek;
	private DefaultTableModel modeloReuniones;
	private JTable tablaReuniones;
	private JLabel lblFecha;
	Map<Point, Color> cellColors = new HashMap<>();

	public PanelReuniones() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 1050, 650);
		setLayout(null);

		String columnas[] = { "", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" };
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 72, 990, 503);
		add(scrollPane);

		modeloReuniones = new DefaultTableModel(columnas, 0);
		tablaReuniones = new JTable(modeloReuniones);
		tablaReuniones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tablaReuniones.setAutoCreateRowSorter(true);
		tablaReuniones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaReuniones.setRowSelectionAllowed(false);
		tablaReuniones.setCellSelectionEnabled(false);
		tablaReuniones.setRowHeight(80);
		tablaReuniones.getColumnModel().getColumn(0).setMaxWidth(70);
		tablaReuniones.setDefaultEditor(Object.class, null);

		scrollPane.setViewportView(tablaReuniones);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(20, 593, 211, 37);
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVolver.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnVolver.setBackground(new Color(100, 100, 100));
		add(btnVolver);

		lblFecha = new JLabel("");
		lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFecha.setBounds(829, 29, 145, 25);
		add(lblFecha);

		btnNextWeek = new JButton(">");
		btnNextWeek.setBounds(972, 29, 49, 25);
		btnNextWeek.setForeground(Color.WHITE);
		btnNextWeek.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNextWeek.setBackground(new Color(100, 100, 100));
		add(btnNextWeek);

		btnPreviousWeek = new JButton("<");
		btnPreviousWeek.setBounds(781, 29, 49, 25);
		btnPreviousWeek.setForeground(Color.WHITE);
		btnPreviousWeek.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPreviousWeek.setBackground(new Color(100, 100, 100));
		add(btnPreviousWeek);

	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public DefaultTableModel getModeloReuniones() {
		return modeloReuniones;
	}

	public JButton getBtnNextWeek() {
		return btnNextWeek;
	}

	public JButton getBtnPreviousWeek() {
		return btnPreviousWeek;
	}

	public JLabel getLblFecha() {
		return lblFecha;
	}

	public JTable getTablaReuniones() {
		return tablaReuniones;
	}

	public Map<Point, Color> getCellColors() {
		return cellColors;
	}

	public void setCellColors(Map<Point, Color> cellColors) {
		this.cellColors = cellColors;
	}

}