package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class PanelReuniones extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnVolver;

	public PanelReuniones() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 884, 561);
		setLayout(null);
	

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
	
}
