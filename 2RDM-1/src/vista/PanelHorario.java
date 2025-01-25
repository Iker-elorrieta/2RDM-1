package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class PanelHorario extends JPanel {

	// ERRORES:
	// - Cuando sales del horario y no te desconectas, no puedes volver a entrar en
	// Otros Horarios.
	// - Cuando inician sesión dos usuarios sin cerrar la app, se duplican sus
	// horarios.

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modeloHorario;
	private JButton btnVolver;
	private JTable tablaHorario;

	public PanelHorario() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 884, 561);
		setLayout(null);

		String columnas[] = { "", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" };
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(20, 52, 841, 425);
		add(scrollPane);

		modeloHorario = new DefaultTableModel(columnas, 0);
		tablaHorario = new JTable(modeloHorario);
		tablaHorario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tablaHorario.setAutoCreateRowSorter(true);
		tablaHorario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaHorario.setRowSelectionAllowed(false);
		tablaHorario.setCellSelectionEnabled(false);
		tablaHorario.setRowHeight(67);
		tablaHorario.getColumnModel().getColumn(0).setMaxWidth(70);

		tablaHorario.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 4045963045190976386L;

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

		tablaHorario.setDefaultEditor(Object.class, null);

		scrollPane.setViewportView(tablaHorario);

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

	public DefaultTableModel getModeloHorario() {
		return modeloHorario;
	}

	public void setModeloHorario(DefaultTableModel modeloHorario) {
		this.modeloHorario = modeloHorario;
	}

	public JTable getTablaHorario() {
		return tablaHorario;
	}

	public void setTablaHorario(JTable tablaHorario) {
		this.tablaHorario = tablaHorario;
	}

	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}
}