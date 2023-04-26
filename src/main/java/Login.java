import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Login {
    public static JPanel newLogin(App app) {
        JPanel container = new JPanel(new GridBagLayout()); // Exported panel

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

        JLabel errorLabel = new JLabel("Invalid username or password.");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false); // Initially invisible


        // Listeners
        loginBtn.addActionListener(e -> {
            Login.queryLogin(app, usernameField, passwordField, errorLabel);
        });
        createBtn.addActionListener(e -> {
            app.showCreate();
        });

        // Layout Manager
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Add the panels to the layout
        container.add(usernamePanel, gbc);
        gbc.gridy++;
        container.add(passwordPanel, gbc);
        gbc.gridy++;
        container.add(errorLabel, gbc);
        gbc.gridy++;
        container.add(buttonPanel, gbc);

        return container;
    }

    public static void queryLogin(App app, JTextField usernameField, JPasswordField passwordField, JLabel errorLabel) {
        boolean  success = false; // Replace with actual login validation
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        System.out.println("Login: " + username + ", " + password);

        try {
            // Set up the POST request
            URL url = new URL("http://155.248.226.28/login.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Send the username and password in the request body
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("username=" + username + "&password=" + password);
            writer.flush();

            // Read the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            // Print the response
            System.out.println(response);
            if(!response.equalsIgnoreCase("Failed")) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Logic
        if (success) {// if true - show dashboard
            app.showDashboard();
        } else { // fail login
            errorLabel.setText("Invalid username or password.");
            errorLabel.setVisible(true);
            usernameField.setText("");
            passwordField.setText("");
        }
    }
}
