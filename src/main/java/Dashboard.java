import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Dashboard extends JPanel {
    private JPanel appointmentWrapper;
    public Dashboard(App app) {
        setLayout(new BorderLayout());

        // Content
        JLabel appointmentLabel = new JLabel("Appointments");
        JButton createAppointment = new JButton("Create Appointment");
        appointmentWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        appointmentWrapper.setMinimumSize(new Dimension(100, 300));

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
        add(addToolbar(), BorderLayout.NORTH);
        add(gridBagPanel, BorderLayout.CENTER);
    }

    public JToolBar addToolbar() {
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

    public void queryAppointments(App app) {
        try {
            // Set up the POST request
            URL url = new URL("http://155.248.226.28/getAppointment.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // query based on user id
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("userID=" + app.getUserID() );
            writer.flush();

            // Read the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            // Print the response
            System.out.println("dashboard: " + response);
            if(response.equals("Failed")) {
                JLabel none = new JLabel("No upcoming appointments");
                appointmentWrapper.add(none);
            } else {
                System.out.println("query appointment response: " + response);
                Gson gson = new Gson();
                Appt[] appts  = gson.fromJson(response, Appt[].class);

                for(int i = 0; i < appts.length; i++) {
                    JPanel appointment = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();

                    gbc.gridy = 0;
                    gbc.gridx = 0;
                    appointment.add(new JLabel(appts[i].getDate()));

                    gbc.gridy++;
                    appointment.add(new JLabel(appts[i].getTime()));



                    appointmentWrapper.add(appointment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

