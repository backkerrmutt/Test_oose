import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReportsFrame extends JFrame {
    private final JTable reportTable;
    private final DefaultTableModel reportTableModel;

    public ReportsFrame() {
        setTitle("Concert Reports");
        setSize(500, 400);
        setLocationRelativeTo(null);

        String[] columnNames = { "Event Name", "Attendees" };

        // Sample customer data to simulate ticket bookings
        String[][] data = generateReportData();
        reportTableModel = new DefaultTableModel(data, columnNames);
        reportTable = new JTable(reportTableModel);
        JScrollPane scrollPane = new JScrollPane(reportTable);

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private String[][] generateReportData() {
        // Sample data to simulate bookings
        HashMap<String, Integer> eventBookings = new HashMap<>();

        // Simulating customers and their ticket bookings
        for (User user : LoginFrame.users.values()) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                // Assuming customer.bookedTickets is a list of Ticket objects
                for (Ticket ticket : customer.getBookedTickets()) {
                    String eventName = ticket.getEventName();
                    eventBookings.put(eventName, eventBookings.getOrDefault(eventName, 0) + 1);
                }
            }
        }

        // Convert eventBookings map to 2D array for table
        String[][] reportData = new String[eventBookings.size()][2];
        int row = 0;
        for (Map.Entry<String, Integer> entry : eventBookings.entrySet()) {
            reportData[row][0] = entry.getKey();
            reportData[row][1] = String.valueOf(entry.getValue());
            row++;
        }

        return reportData;
    }
}