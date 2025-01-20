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

import modelo.Users;

import javax.swing.JComboBox;

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
		
		String columnas[] = { "Dia","Hora","Profe Id","Modulo Id"};
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(230, 70, 572, 409);
		add(scrollPane);
		
		modeloOtrosHorarios = new DefaultTableModel(columnas, 0);
		tablaOtrosHorarios = new JTable(modeloOtrosHorarios);
		tablaOtrosHorarios.setAutoCreateRowSorter(true);
		tablaOtrosHorarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaOtrosHorarios.setRowSelectionAllowed(false);
		tablaOtrosHorarios.setCellSelectionEnabled(false);

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
		profesComboBox.setBounds(20, 70, 185, 35);
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
