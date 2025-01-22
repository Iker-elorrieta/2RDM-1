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


public class PanelReuniones extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnVolver;
	private DefaultTableModel modeloReuniones;
	private JTable tablaReuniones;

	public PanelReuniones() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 884, 561);
		setLayout(null);
		
		String columnas[] = { "Dia","Hora","Profe Id",
				"Modulo Id","Id Centro", "Titulo","Asunto",
				"Aula"};
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(230, 70, 572, 409);
		add(scrollPane);
		
		modeloReuniones = new DefaultTableModel(columnas, 0);
		tablaReuniones = new JTable(modeloReuniones);
		tablaReuniones.setAutoCreateRowSorter(true);
		tablaReuniones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaReuniones.setRowSelectionAllowed(false);
		tablaReuniones.setCellSelectionEnabled(false);

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
