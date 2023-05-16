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
    private int userID;

    public App() {
        // create the frame
        frame = new JFrame("CGJ Hospital");
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the content pane and card layout
        contentPane = new JPanel();
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);

        loginPanel = Login.newLogin(this);
        dashboardPanel = new Dashboard(this);
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

    public void showLogin() { setUsername(null); setUserID(0); cardLayout.show(contentPane, "login"); }
    public void showDashboard() { ((Dashboard)dashboardPanel).queryAppointments(this); dashboardPanel.repaint(); cardLayout.show(contentPane, "dashboard"); }
    public void showCreateAccount() { cardLayout.show(contentPane, "createAccount"); }
    public void showCreateAppointment() { cardLayout.show(contentPane, "createAppointment"); }
    public void setUsername(String u) { username = u; }
    public String getUsername() { return username; }
    public void setUserID(int id) { userID = id; }
    public int getUserID() { return userID; }
    public static void main(String[] args) {
        App app = new App();
        app.showLogin(); //default login
    }
}
