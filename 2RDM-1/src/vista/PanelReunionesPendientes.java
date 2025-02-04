package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PanelReunionesPendientes extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnVolver, btnRechazar, btnAceptar;
	private DefaultTableModel modeloReuniones;
	private JTable tablaReuniones;
	Map<Point, Color> cellColors = new HashMap<>();

	public PanelReunionesPendientes() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 1050, 650);
		setLayout(null);

		String columnas[] = { "ID", "Título", "Asunto", "Centro", "Fecha", "Estado" };
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
		tablaReuniones.setRowHeight(60);
		tablaReuniones.getColumnModel().getColumn(0).setMaxWidth(70);
		tablaReuniones.setDefaultEditor(Object.class, null);

		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloReuniones);
		tablaReuniones.setRowSorter(sorter);
		int columnIndex = 4;
		sorter.toggleSortOrder(columnIndex);

		scrollPane.setViewportView(tablaReuniones);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(20, 593, 211, 37);
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVolver.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnVolver.setBackground(new Color(100, 100, 100));
		add(btnVolver);

		btnRechazar = new JButton("Rechazar");
		btnRechazar.setForeground(Color.WHITE);
		btnRechazar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRechazar.setBackground(new Color(100, 100, 100));
		btnRechazar.setBounds(685, 593, 145, 37);
		add(btnRechazar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAceptar.setBackground(new Color(100, 100, 100));
		btnAceptar.setBounds(876, 593, 145, 37);
		add(btnAceptar);

	}

	public Map<Point, Color> getCellColors() {
		return cellColors;
	}

	public void setCellColors(Map<Point, Color> cellColors) {
		this.cellColors = cellColors;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public JButton getBtnRechazar() {
		return btnRechazar;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public DefaultTableModel getModeloReuniones() {
		return modeloReuniones;
	}

	public JTable getTablaReuniones() {
		return tablaReuniones;
	}

}