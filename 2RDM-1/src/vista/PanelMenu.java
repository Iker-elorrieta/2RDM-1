package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PanelMenu extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnNext;

	public PanelMenu() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 900, 600);
		setLayout(null);

		btnNext = new JButton("Panel Menu");
		btnNext.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNext.setBackground(new Color(100, 100, 100));
		btnNext.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnNext.setBounds(630, 500, 218, 35);
		btnNext.setForeground(Color.WHITE);
		add(btnNext);
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public void setBtnNext(JButton btnNext) {
		this.btnNext = btnNext;
	}
	
}
