package com.tamu.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationScreen extends JFrame implements ActionListener {
    private JTextField txtUsername = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);
    private JTextField txtFirstName = new JTextField(20);
    private JTextField txtLastName = new JTextField(20);
    private JTextField txtAge = new JTextField(20);
    private JTextField txtActivity = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JButton btnRegister = new JButton("Register");

    public RegistrationScreen() {
        this.setTitle("User Registration");
        this.setSize(400, 300);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(createLabelAndField("Username:", txtUsername));
        mainPanel.add(createLabelAndField("Password:", txtPassword));
        mainPanel.add(createLabelAndField("First Name:", txtFirstName));
        mainPanel.add(createLabelAndField("Last Name:", txtLastName));
        mainPanel.add(createLabelAndField("Age:", txtAge));
        mainPanel.add(createLabelAndField("Activity:", txtActivity));
        mainPanel.add(createLabelAndField("Email:", txtEmail));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnRegister);

        this.add(mainPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        btnRegister.addActionListener(this);
    }

    private JPanel createLabelAndField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 20));  // Set a fixed width for labels
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            // Perform registration logic here
            // For simplicity, let's assume registration is successful
            // You may want to validate input and interact with your backend

            // Show success message
            JOptionPane.showMessageDialog(this, "Registration Successful!");

            // Hide the registration screen
            this.setVisible(false);

            // Show the login screen
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationScreen().setVisible(true));
    }
}
