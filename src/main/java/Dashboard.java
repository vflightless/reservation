import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Dashboard extends JPanel {
    private JScrollPane appointmentPane;
    private JPanel innerPanel;
    private JLabel none;

    public Dashboard(App app) {
        setLayout(new BorderLayout());
        this.innerPanel = new JPanel();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Content
        JLabel appointmentLabel = new JLabel("Appointments");
        appointmentPane = new JScrollPane();
        JButton createAppointment = new JButton("Create Appointment");
        createAppointment.addActionListener(e -> { app.showCreateAppointment(); });

        contentPane.add(appointmentLabel);
        contentPane.add(appointmentPane);
        contentPane.add(createAppointment);

        add(addToolbar(app), BorderLayout.NORTH);
        add(contentPane, BorderLayout.CENTER);
    }

    public JToolBar addToolbar(App app) {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        // Toolbar Left
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(new JLabel("Hospital Name"));

        // Toolbar Right
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = new JButton("Logout");
        rightPanel.add(logoutBtn);

        logoutBtn.addActionListener(e -> {
            innerPanel.removeAll();
            appointmentPane.setViewportView(none);
            app.showLogin();
        });

        // Add the nested panels to the toolbar
        toolbar.add(leftPanel);
        toolbar.add(Box.createHorizontalGlue()); // Add a stretchable space
        toolbar.add(rightPanel);

        return toolbar;
    }

    public void queryAppointments(App app) {
        try {
            URL url = new URL("http://155.248.226.28/getAppointment.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // query based on user id
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("userID=" + app.getUserID());
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            System.out.println("dashboard: " + response);
            if (response.equals("Failed")) {
                JLabel none = new JLabel("No upcoming appointments");
                JPanel innerPanel = new JPanel();
                innerPanel.add(none);
                appointmentPane.setViewportView(innerPanel);
            } else { // loop appointments and add to appointment pane
                Gson gson = new Gson();
                Appt[] appts = gson.fromJson(response, Appt[].class);

                JPanel innerPanel = new JPanel();
                innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

                for (int i = 0; i < appts.length; i++) {
                    JPanel apptPanel = new JPanel();
                    apptPanel.setMinimumSize(new Dimension(200, 20));
                    apptPanel.setPreferredSize(new Dimension(200, 50));
                    apptPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    String str = "<html>Dr. " + appts[i].getDoctor() + " - " + appts[i].getDate() + ", " + appts[i].getTime() + "<br>" + appts[i].getReason() + "</html>";
                    apptPanel.add(new JLabel(str));
                    innerPanel.add(apptPanel);
                }

                appointmentPane.setViewportView(innerPanel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
