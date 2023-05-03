import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App {
    private JFrame frame;
    private JPanel contentPane;
    private CardLayout cardLayout;
    private JPanel loginPanel;
    private JPanel dashboardPanel;
    private JPanel createAccountPanel;
    private JPanel createAppointmentPanel;

    private String username;

    public App() {
        // create the frame
        frame = new JFrame("CGJ Hospital");
        frame.setMinimumSize(new Dimension(300, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the content pane and card layout
        contentPane = new JPanel();
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);


        loginPanel = Login.newLogin(this);// Login Panel
        dashboardPanel = Dashboard.newDashboard(this);
        createAccountPanel = new Account(this);
        createAppointmentPanel = new Appointment(this);

        // Add the panels to the content pane
        contentPane.add(loginPanel, "login");
        contentPane.add(dashboardPanel, "dashboard");
        contentPane.add(createAccountPanel, "createAccount");
        contentPane.add(createAppointmentPanel, "createAppointment");

        frame.setContentPane(contentPane);// Add content pane to the frame
        cardLayout.show(contentPane, "login");// show the login by default

        // pack and show the frame
        frame.pack();
        frame.setVisible(true);
    }

    public void showLogin() { cardLayout.show(contentPane, "login"); }
    public void showDashboard() { cardLayout.show(contentPane, "dashboard"); }
    public void showCreateAccount() { cardLayout.show(contentPane, "createAccount"); }
    public void showCreateAppointment() { cardLayout.show(contentPane, "createAppointment"); }

    public void setUsername(String u) { username = u; }
    public String getUsername() { return username; }

    public static void main(String[] args) {
        App app = new App();
        app.showLogin(); //default login
    }
}
