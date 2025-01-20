package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PanelMenu extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnHorario, btnOtrosHorarios, btnReuniones, btnDesconectarse;

	public PanelMenu() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 884, 561);
		setLayout(null);

		btnHorario = new JButton("Consultar horario");
		btnHorario.setForeground(Color.WHITE);
		btnHorario.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnHorario.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnHorario.setBackground(new Color(100, 100, 100));
		btnHorario.setBounds(341, 183, 218, 35);
		add(btnHorario);

		btnOtrosHorarios = new JButton("Consultar otros horarios");
		btnOtrosHorarios.setForeground(Color.WHITE);
		btnOtrosHorarios.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnOtrosHorarios.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnOtrosHorarios.setBackground(new Color(100, 100, 100));
		btnOtrosHorarios.setBounds(341, 263, 218, 35);
		add(btnOtrosHorarios);

		btnReuniones = new JButton("Ver reuniones");
		btnReuniones.setForeground(Color.WHITE);
		btnReuniones.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReuniones.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnReuniones.setBackground(new Color(100, 100, 100));
		btnReuniones.setBounds(341, 343, 218, 35);
		add(btnReuniones);

		btnDesconectarse = new JButton("Desconectarse");
		btnDesconectarse.setForeground(Color.WHITE);
		btnDesconectarse.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDesconectarse.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnDesconectarse.setBackground(new Color(100, 100, 100));
		btnDesconectarse.setBounds(20, 505, 150, 35);
		add(btnDesconectarse);

	}

	public JButton getBtnHorario() {
		return btnHorario;
	}

	public void setBtnHorario(JButton btnHorario) {
		this.btnHorario = btnHorario;
	}

	public JButton getBtnOtrosHorarios() {
		return btnOtrosHorarios;
	}

	public void setBtnOtrosHorarios(JButton btnOtrosHorarios) {
		this.btnOtrosHorarios = btnOtrosHorarios;
	}

	public JButton getBtnReuniones() {
		return btnReuniones;
	}

	public void setBtnReuniones(JButton btnReuniones) {
		this.btnReuniones = btnReuniones;
	}

	public JButton getBtnDesconectarse() {
		return btnDesconectarse;
	}

	public void setBtnDesconectarse(JButton btnDesconectarse) {
		this.btnDesconectarse = btnDesconectarse;
	}

}
