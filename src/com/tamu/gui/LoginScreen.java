package com.tamu.gui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame implements ActionListener {
    private JTextField txtUserName = new JTextField(10);
    private JPasswordField txtPassword = new JPasswordField(10);
    private JButton btnLogin = new JButton("Login");
    private JButton btnRegister = new JButton("Register"); // New Register button

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public JTextField getTxtUserName() {
        return txtUserName;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public LoginScreen() {
        this.setSize(300, 150);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.getContentPane().add(new JLabel("Library Management System"));

        JPanel main = new JPanel(new SpringLayout());
        main.add(new JLabel("Username:"));
        main.add(txtUserName);
        main.add(new JLabel("Password:"));
        main.add(txtPassword);

        SpringUtilities.makeCompactGrid(main,
                2, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        this.getContentPane().add(main);

        // Add both Login and Register buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);
        this.getContentPane().add(buttonPanel);

        btnLogin.addActionListener(this);
        btnRegister.addActionListener(this); // Register button action listener
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            // Existing login logic
            // ...
        } else if (e.getSource() == btnRegister) {
            // Open the RegistrationScreen when the Register button is clicked
            RegistrationScreen registrationScreen = new RegistrationScreen();
            registrationScreen.setVisible(true);
            this.setVisible(false);
        }
    }
}
