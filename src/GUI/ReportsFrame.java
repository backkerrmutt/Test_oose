package GUI;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Customer;
import Ticket;
import User;

public class ReportsFrame extends JFrame {
    private JTable reportTable = null;
    private DefaultTableModel reportTableModel = null;

    public ReportsFrame(Map<String, User> users) {
        setTitle("Concert Reports");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Allows closing without exiting the app

        String[] columnNames = { "Event Name", "Attendees" };

        // ตรวจสอบว่า users มีข้อมูลหรือไม่
        if (users == null || users.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No users data available.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // หยุดทำงานหากไม่มีข้อมูล
        }

        // Generate report data dynamically
        String[][] data = generateReportData(users);
        if (data.length == 0) {
            JOptionPane.showMessageDialog(this, "No tickets booked for any events.", "No Data", JOptionPane.INFORMATION_MESSAGE);
        }

        reportTableModel = new DefaultTableModel(data, columnNames);
        reportTable = new JTable(reportTableModel);
        JScrollPane scrollPane = new JScrollPane(reportTable);

        add(scrollPane, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> setVisible(true)); // Ensure UI thread safety
    }

    private String[][] generateReportData(Map<String, User> users) {
        HashMap<String, Integer> eventBookings = new HashMap<>();

        for (User user : users.values()) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                List<Ticket> tickets = customer.getBookedTickets();

                if (tickets != null) {
                    for (Ticket ticket : tickets) {
                        String eventName = ticket.getEventName();
                        eventBookings.put(eventName, eventBookings.getOrDefault(eventName, 0) + 1);
                    }
                }
            }
        }

        // ตรวจสอบว่า eventBookings มีข้อมูลหรือไม่
        if (eventBookings.isEmpty()) {
            return new String[0][0]; // คืนค่าตารางว่างถ้าไม่มีข้อมูล
        }

        // Convert to 2D array
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
