import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Dashboard {
    public static JPanel newDashboard(App app) {
        JPanel container = new JPanel(new BorderLayout());

        // Content
        JLabel appointmentLabel = new JLabel("Appointments");
        JButton createAppointment = new JButton("Create Appointment");
        JPanel appointmentWrapper = new JPanel(new FlowLayout());
        appointmentWrapper.setMinimumSize(new Dimension(100, 300));

        //call query for upcoming appointments
        queryAppointments(app, appointmentWrapper);

        //create listener
        createAppointment.addActionListener(e -> {
            app.showCreateAppointment();
        });


        // Add components to GridBagLayout panel
        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        gridBagPanel.add(appointmentLabel, gbc);

        gbc.gridx++;
        gridBagPanel.add(createAppointment,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gridBagPanel.add(appointmentWrapper, gbc);

        // Add the GridBagLayout panel to the center of the BorderLayout
        container.add(addToolbar(), BorderLayout.NORTH);
        container.add(gridBagPanel, BorderLayout.CENTER);

        return container;
    }

    public static JToolBar addToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        // Toolbar Left
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(new JLabel("Hospital Name"));

        // Toolbar Right
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(new JButton("Logout"));

        // Add the nested panels to the toolbar
        toolbar.add(leftPanel);
        toolbar.add(Box.createHorizontalGlue()); // Add a stretchable space
        toolbar.add(rightPanel);

        return toolbar;
    }

    public static void queryAppointments(App app, JPanel wrapper) {
        boolean success = false;
        try {
            // Set up the POST request
            URL url = new URL("http://155.248.226.28/getAppointment.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Send the username and password in the request body
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("username=" + app.getUsername() );
            writer.flush();

            // Read the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            // Print the response
            System.out.println(response);
            if(!response.equals("Failed")) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(success) {
            //turn successful response into array to loop through

        } else {
            JLabel none = new JLabel("No upcoming appointments");
            wrapper.add(none);
        }
    }
}
