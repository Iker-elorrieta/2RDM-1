package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PanelLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnLogin;
	private JLabel lblUser, lblPswd, lblTitle;
	private JTextField txtFUser;
	private JPasswordField pswdFPassword;

	public PanelLogin() {
		setBackground(new Color(220, 220, 220));
		setBounds(0, 0, 884, 561);
		setLayout(null);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogin.setBounds(341, 337, 218, 35);
		btnLogin.setBackground(new Color(100, 100, 100));
		btnLogin.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnLogin.setForeground(Color.WHITE);
		add(btnLogin);

		txtFUser = new JTextField();
		txtFUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtFUser.setBounds(466, 174, 248, 35);
		add(txtFUser);
		txtFUser.setColumns(10);

		pswdFPassword = new JPasswordField();
		pswdFPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		pswdFPassword.setBounds(466, 228, 248, 35);
		add(pswdFPassword);

		lblUser = new JLabel("Usuario");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblUser.setBounds(281, 174, 151, 35);
		add(lblUser);

		lblPswd = new JLabel("Contraseña");
		lblPswd.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPswd.setBounds(281, 228, 151, 35);
		add(lblPswd);

		lblTitle = new JLabel("Elorrieta-Errekamari");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(253, 69, 394, 71);
		add(lblTitle);
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public JLabel getLblUser() {
		return lblUser;
	}

	public JLabel getLblPswd() {
		return lblPswd;
	}

	public JLabel getLblTitle() {
		return lblTitle;
	}

	public JTextField getTxtFUser() {
		return txtFUser;
	}

	public JPasswordField getPswdFPassword() {
		return pswdFPassword;
	}

}
