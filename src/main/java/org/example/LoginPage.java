package org.example;
import javax.swing.*;
import java.awt.*;


public class LoginPage extends JFrame {
    public LoginPage() {

        // Login Components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginBtn = new JButton("Login");
        JButton createBtn = new JButton("Create Account");

        // Set the minimum height of the text fields
        Dimension fieldSize = new Dimension(usernameField.getPreferredSize().width, usernameField.getPreferredSize().height);
        usernameField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);


        // Panels
        JPanel usernamePanel = new JPanel(new FlowLayout());
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        usernamePanel.setMinimumSize(new Dimension(100,50));

        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        passwordPanel.setMinimumSize(new Dimension(100,50));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginBtn);
        buttonPanel.add(createBtn);
        buttonPanel.setMinimumSize(new Dimension(100,50));






        // Layout Manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        //gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Add the panels to the layout
        add(usernamePanel, gbc);
        gbc.gridy++;
        add(passwordPanel, gbc);
        gbc.gridy++;
        add(buttonPanel, gbc);

        // Window
        setTitle("Login"); // page title
        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
