import javax.swing.*;
import java.awt.*;

public class Dashboard {
    public static JPanel newDashboard(App app) {
        //call query for upcoming appointments

        //if null - display none
        //if there's results - loop through results and display
        JPanel container = new JPanel(new BorderLayout());

        // Content
        JLabel apptLabel = new JLabel("Appointments");
        JPanel apptContainer = new JPanel(new FlowLayout());
        apptContainer.setMinimumSize(new Dimension(100, 300));

        // Add components to GridBagLayout panel
        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        gridBagPanel.add(apptLabel, gbc);
        gbc.gridy++;
        gridBagPanel.add(apptContainer, gbc);

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

    //public boolean queryAppointments() {}

}
