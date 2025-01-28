package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PanelReuniones extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnVolver;
	private DefaultTableModel modeloReuniones;
	private JTable tablaReuniones;

	public PanelReuniones() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 884, 561);
		setLayout(null);

		String columnas[] = { "", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" };
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 70, 842, 425);
		add(scrollPane);

		modeloReuniones = new DefaultTableModel(columnas, 0);
		tablaReuniones = new JTable(modeloReuniones);
		tablaReuniones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tablaReuniones.setAutoCreateRowSorter(true);
		tablaReuniones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaReuniones.setRowSelectionAllowed(false);
		tablaReuniones.setCellSelectionEnabled(false);
		tablaReuniones.setRowHeight(67);
		tablaReuniones.getColumnModel().getColumn(0).setMaxWidth(70);

		tablaReuniones.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = -2911240376199387811L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				label.setText(value != null ? value.toString() : "");
				label.setOpaque(true);
				label.setHorizontalAlignment(JLabel.LEFT);
				label.setVerticalAlignment(JLabel.TOP);
				label.setText("<html>" + label.getText().replace("\n", "<br>") + "</html>");

				return label;
			}
		});

		tablaReuniones.setDefaultEditor(Object.class, null);

		scrollPane.setViewportView(tablaReuniones);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(20, 505, 150, 35);
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVolver.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnVolver.setBackground(new Color(100, 100, 100));
		add(btnVolver);

	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public DefaultTableModel getModeloReuniones() {
		return modeloReuniones;
	}

	public void setModeloReuniones(DefaultTableModel modeloReuniones) {
		this.modeloReuniones = modeloReuniones;
	}

	public JTable getTablaReuniones() {
		return tablaReuniones;
	}

	public void setTablaReuniones(JTable tablaReuniones) {
		this.tablaReuniones = tablaReuniones;
	}

}