import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Account extends JPanel {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField regCodeField;

    public Account(App app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10,10,10,10);

        JLabel nameLabel = new JLabel("Full Name: ");
        add(nameLabel, gbc);

        gbc.gridx++;
        nameField = new JTextField(20);
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel emailLabel = new JLabel("Email: ");
        add(emailLabel, gbc);

        gbc.gridx++;
        emailField = new JTextField(20);
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password: ");
        add(passwordLabel, gbc);

        gbc.gridx++;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel regCodeLabel = new JLabel("Registration Code (optional): ");
        add(regCodeLabel, gbc);

        gbc.gridx++;
        regCodeField = new JTextField(20);
        add(regCodeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JButton createButton = new JButton("Submit");
        createButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (validateFields(name, email, password)) {
                queryAccount(app, nameField, emailField, passwordField, regCodeField);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(createButton, gbc);
    }
    public void queryAccount(App app, JTextField name, JTextField email, JPasswordField password, JTextField code) {
        String pass = new String(password.getPassword());

        try {
            URL url = new URL("http://155.248.226.28/create.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("fullname=" + name.getText() + "&email=" + email.getText() + "&password=" + pass + "&code=" + code.getText());
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            System.out.println("POST Response: " + response);
            if (response.equals("Success")) {
                app.showLogin();
            } else {
                // TODO: Implement error showing and notifying of errored fields
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean validateFields(String name, String email, String password) {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }
}
