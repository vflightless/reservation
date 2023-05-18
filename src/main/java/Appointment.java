import com.github.lgooddatepicker.components.DatePicker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
public class Appointment extends JPanel {
    private JLabel failLabel;
    private JComboBox<String> doctorPicker;
    private DatePicker datePicker;
    private JComboBox<String> timePicker;
    private JTextArea reasonField;

    public Appointment(App app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10,10,10,10);
        JLabel newAppointment = new JLabel("New Appointment");
        add(newAppointment, gbc);

        gbc.gridy++;
        JLabel doctorLabel = new JLabel("Choose Doctor:");
        add(doctorLabel, gbc);
        String[] doctors = queryDoctors();
        doctorPicker = new JComboBox<String>(doctors);
        gbc.gridx++;
        add(doctorPicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel dateLabel = new JLabel("Choose Date:");
        add(dateLabel, gbc);
        datePicker = new DatePicker();
        datePicker.setDateToToday();
        gbc.gridx++;
        add(datePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel timeLabel = new JLabel("Select Time:");
        add(timeLabel, gbc);
        String[] times = {"9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM"};
        timePicker = new JComboBox<>(times);
        gbc.gridx++;
        add(timePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel reasonLabel = new JLabel("Enter Reason:");
        add(reasonLabel, gbc);
        reasonField = new JTextArea(5, 20);
        gbc.gridx++;
        add(reasonField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JButton createAppointmentButton = new JButton("Submit");
        createAppointmentButton.addActionListener(e -> {
            String result = queryCreateAppointment(app, doctorPicker, datePicker, timePicker, reasonField.getText());
            doctorPicker.setSelectedIndex(-1);
            timePicker.setSelectedIndex(-1);
            datePicker.setDateToToday();
            reasonField.setText("");
        });
        add(createAppointmentButton, gbc);

        gbc.gridx++;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            doctorPicker.setSelectedIndex(-1);
            timePicker.setSelectedIndex(-1);
            datePicker.setDateToToday();
            reasonField.setText("");
            app.showDashboard();
        });
        add(backButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        failLabel = new JLabel("Error when creating appointment. Please try a different date/time.");
        failLabel.setVisible(false);
        gbc.gridwidth = 2;
        add(failLabel, gbc);
    }
    public String queryCreateAppointment(App app, JComboBox<String> doctor, DatePicker date, JComboBox<String> time, String reason) {
        String doc = (String) doctor.getSelectedItem();
        String d = date.getDateStringOrEmptyString();
        String t = (String) time.getSelectedItem();
        try {
            URL url = new URL("http://155.248.226.28/createAppointment.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Send the username and password in the request body
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("userID=" + app.getUserID() + "&name=" + app.getUsername() + "&doctor=" + doc + "&date=" + d + "&time=" + t + "&reason=" + reason);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            System.out.println("create appointment response: " + response);
            if(response.equals("Failed")) {
                //failLabel.setVisible(true);
                JOptionPane.showMessageDialog(this, "Time/Date already taken. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                failLabel.setVisible(false);
                app.showDashboard();
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "oops";
        }
    }
    public String[] queryDoctors() {
        try {
            URL url = new URL("http://155.248.226.28/getDoctors.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            if(!response.equals(null)) {
                Type listType = new TypeToken<List<String>>() {}.getType();
                List<String> rows = new Gson().fromJson(response, listType);
                String[] drArray = new String[rows.size()];

                int i = 0;
                for (String row : rows) {
                    drArray[i] = row;
                    i++;
                }
                return drArray;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("NULL");
        String[] none = {"null"};
        return none;
    }
}
