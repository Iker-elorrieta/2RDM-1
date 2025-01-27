package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import modelo.Users;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class PanelOtrosHorarios extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modeloOtrosHorarios;
	private JButton btnVolver;
	private JTable tablaOtrosHorarios;
	private JComboBox<Users> profesComboBox;

	public PanelOtrosHorarios() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 884, 561);
		setLayout(null);

		String columnas[] = { "", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" };
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 70, 842, 425);
		add(scrollPane);

		modeloOtrosHorarios = new DefaultTableModel(columnas, 0);
		tablaOtrosHorarios = new JTable(modeloOtrosHorarios);
		tablaOtrosHorarios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tablaOtrosHorarios.setAutoCreateRowSorter(true);
		tablaOtrosHorarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaOtrosHorarios.setRowSelectionAllowed(false);
		tablaOtrosHorarios.setCellSelectionEnabled(false);
		tablaOtrosHorarios.setRowHeight(67);
		tablaOtrosHorarios.getColumnModel().getColumn(0).setMaxWidth(70);

		tablaOtrosHorarios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

		tablaOtrosHorarios.setDefaultEditor(Object.class, null);

		scrollPane.setViewportView(tablaOtrosHorarios);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(20, 505, 150, 35);
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVolver.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnVolver.setBackground(new Color(100, 100, 100));
		add(btnVolver);

		profesComboBox = new JComboBox<Users>();
		profesComboBox.setMaximumRowCount(10);
		profesComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		profesComboBox.setBounds(697, 25, 165, 28);
		add(profesComboBox);
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public DefaultTableModel getModeloOtrosHorarios() {
		return modeloOtrosHorarios;
	}

	public void setModeloOtrosHorarios(DefaultTableModel modeloOtrosHorarios) {
		this.modeloOtrosHorarios = modeloOtrosHorarios;
	}

	public JTable getTablaOtrosHorarios() {
		return tablaOtrosHorarios;
	}

	public void setTablaOtrosHorarios(JTable tablaOtrosHorarios) {
		this.tablaOtrosHorarios = tablaOtrosHorarios;
	}

	public JComboBox<Users> getProfesComboBox() {
		return profesComboBox;
	}

	public void setProfesComboBox(JComboBox<Users> profesComboBox) {
		this.profesComboBox = profesComboBox;
	}
}