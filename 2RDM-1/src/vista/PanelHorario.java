package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class PanelHorario extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnVolver;
	private JTable tablaHorario;
	private DefaultTableModel model;

	public PanelHorario() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 884, 561);
		setLayout(null);

		String[] columnas = { "Día", "Hora", "Módulo" };
		model = new DefaultTableModel(columnas, 0);
		tablaHorario = new JTable(model);
		tablaHorario.setBounds(20, 20, 840, 450);

		JScrollPane scrollPane = new JScrollPane(tablaHorario);
		scrollPane.setBounds(20, 20, 840, 450);
		add(scrollPane);

		btnVolver = new JButton("Volver");
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVolver.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnVolver.setBackground(new Color(100, 100, 100));
		btnVolver.setBounds(20, 505, 150, 35);
		add(btnVolver);
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public void actualizarTabla(Object[][] data) {
		if (data == null || data.length == 0) {
			System.out.println("No se recibieron datos para mostrar.");
			return;
		}

		model.setRowCount(0);

		for (Object[] fila : data) {
			model.addRow(fila);
		}

		System.out.println("Tabla actualizada con " + data.length + " filas.");
	}
}
