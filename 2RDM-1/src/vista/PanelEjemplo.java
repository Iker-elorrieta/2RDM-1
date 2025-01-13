package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PanelEjemplo extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnPrevious;

	public PanelEjemplo() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 900, 600);
		setLayout(null);

		btnPrevious = new JButton("Panel Ejemplo");
		btnPrevious.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPrevious.setBackground(new Color(100, 100, 100));
		btnPrevious.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnPrevious.setBounds(630, 500, 218, 35);
		btnPrevious.setForeground(Color.WHITE);
		add(btnPrevious);

	}

	public JButton getBtnPrevious() {
		return btnPrevious;
	}

	public void setBtnPrevious(JButton btnPrevious) {
		this.btnPrevious = btnPrevious;
	}

}
