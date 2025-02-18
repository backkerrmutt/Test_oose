import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


class User {
    protected String username, password, name, lastname, gender;
    protected int age;

    public User(String username, String password, String name, String lastname, int age, String gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
    }
}

class Admin extends User {
    public Admin(String username, String password, String name, String lastname, int age, String gender) {
        super(username, password, name, lastname, age, gender);
    }
}

class Customer extends User {
    private final ArrayList<Ticket> bookedTickets = new ArrayList<>();
    private final int balance;

    public Customer(String username, String password, String name, String lastname, int age, String gender,
            int balance) {
        super(username, password, name, lastname, age, gender);
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public void setBookedTickets(List<Ticket> bookedTickets) {
        this.bookedTickets.clear();
        this.bookedTickets.addAll(bookedTickets);
    }
}

class LoginFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton, registerButton;
    static final Map<String, User> users = new HashMap<>();

    static {
        users.put("admin", new Admin("admin", "admin", "Admin", "User", 35, "Male"));
        users.put("test", new Customer("test", "test", "John", "Doe", 28, "Male", 1000));
        users.put("customer2", new Customer("customer2", "pass456", "Jane", "Smith", 25, "Female", 1500));
    }

    public LoginFrame() {
        setTitle("Concert Ticket System - Login");
        setSize(400, 600);

        // Get screen size to calculate position
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Set the position to the right side of the screen, centered vertically with
        // some padding
        int x = screenWidth - 400 - 10; // 10 px padding from the right
        int y = (screenHeight - 600) / 2; // Vertically centered with padding

        setLocation(x, y); // Se

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
        setVisible(true);

        loginButton.addActionListener(e -> authenticateUser());
        registerButton.addActionListener(e -> new RegisterFrame());
    }

    private void authenticateUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        User user = users.get(username);

        if (user != null && user.password.equals(password)) {
            JOptionPane.showMessageDialog(this, "Login successful! Welcome " + user.name + ".");
            if (user instanceof Admin) {
                new AdminDashboard();
            } else {
                new CustomerDashboard((Customer) user);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

class RegisterFrame extends JFrame {
    private final JTextField usernameField, nameField, lastnameField, ageField, genderField, balanceField;
    private final JPasswordField passwordField;
    private final JButton registerButton;

    public RegisterFrame() {
        setTitle("Register New Customer");
        setSize(300, 350);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Lastname:"));
        lastnameField = new JTextField();
        panel.add(lastnameField);

        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);

        panel.add(new JLabel("Gender:"));
        genderField = new JTextField();
        panel.add(genderField);

        panel.add(new JLabel("Balance:"));
        balanceField = new JTextField();
        panel.add(balanceField);

        registerButton = new JButton("Register");
        panel.add(registerButton);

        add(panel);
        setVisible(true);

        registerButton.addActionListener(e -> registerCustomer());
    }

    private void registerCustomer() {
        try {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String name = nameField.getText().trim();
            String lastname = lastnameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String gender = genderField.getText().trim();
            int balance = Integer.parseInt(balanceField.getText().trim());

            if (username.isEmpty() || password.isEmpty() || name.isEmpty() || lastname.isEmpty() || gender.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!LoginFrame.users.containsKey(username)) {
                Customer newCustomer = new Customer(username, password, name, lastname, age, gender, balance);
                LoginFrame.users.put(username, newCustomer);
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for age and balance!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

class CustomerDashboard extends JFrame {
    private Customer customer;

    public CustomerDashboard(Customer customer) {
        this.customer = customer;

        setTitle("Customer Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        add(new JLabel("Welcome " + customer.name + "!", JLabel.CENTER));

        // Add logout button
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(e -> logOut());
        add(logoutButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void logOut() {
        dispose(); // Close the current dashboard
        new LoginFrame(); // Open login screen
    }
}

public class ConcertTicketSystem {
    public static void main(String[] args) {
        new LoginFrame();
    }
}

class ManageEventsFrame extends JFrame {
    private final JTable eventsTable;
    private final DefaultTableModel eventsTableModel;
    private final JButton addButton, editButton, deleteButton;
    private final ArrayList<Event> eventsList;

    public ManageEventsFrame() {
        eventsList = new ArrayList<>();

        // Sample data
        eventsList.add(new Event("Concert 1", "2025-03-01", "Arena 1", 1000));
        eventsList.add(new Event("Concert 2", "2025-04-01", "Arena 2", 500));

        setTitle("Manage Concert Events");
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Column names for the events table
        String[] columnNames = { "Event Name", "Date", "Venue", "Tickets Available" };

        // Data for the events table
        String[][] data = new String[eventsList.size()][4];
        for (int i = 0; i < eventsList.size(); i++) {
            data[i][0] = eventsList.get(i).getName();
            data[i][1] = eventsList.get(i).getDate();
            data[i][2] = eventsList.get(i).getVenue();
            data[i][3] = String.valueOf(eventsList.get(i).getTicketsAvailable());
        }

        eventsTableModel = new DefaultTableModel(data, columnNames);
        eventsTable = new JTable(eventsTableModel);
        JScrollPane scrollPane = new JScrollPane(eventsTable);

        add(scrollPane, BorderLayout.CENTER);

        // Buttons for adding, editing, deleting events
        JPanel panel = new JPanel();
        addButton = new JButton("Add Event");
        editButton = new JButton("Edit Event");
        deleteButton = new JButton("Delete Event");

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);

        // Action listeners for buttons
        addButton.addActionListener(e -> addEvent());
        editButton.addActionListener(e -> editEvent());
        deleteButton.addActionListener(e -> deleteEvent());
    }

    private void addEvent() {
        String eventName = JOptionPane.showInputDialog(this, "Enter event name:");
        String eventDate = JOptionPane.showInputDialog(this, "Enter event date (yyyy-mm-dd):");
        String eventVenue = JOptionPane.showInputDialog(this, "Enter event venue:");
        String ticketsAvailableStr = JOptionPane.showInputDialog(this, "Enter tickets available:");

        try {
            int ticketsAvailable = Integer.parseInt(ticketsAvailableStr);
            Event newEvent = new Event(eventName, eventDate, eventVenue, ticketsAvailable);
            eventsList.add(newEvent);
            updateTable();
            JOptionPane.showMessageDialog(this, "Event added successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number for tickets available.");
        }
    }

    private void editEvent() {
        int selectedRow = eventsTable.getSelectedRow();
        if (selectedRow != -1) {
            Event selectedEvent = eventsList.get(selectedRow);
            String newName = JOptionPane.showInputDialog(this, "Enter new event name:", selectedEvent.getName());
            String newDate = JOptionPane.showInputDialog(this, "Enter new event date (yyyy-mm-dd):",
                    selectedEvent.getDate());
            String newVenue = JOptionPane.showInputDialog(this, "Enter new event venue:", selectedEvent.getVenue());
            String newTicketsStr = JOptionPane.showInputDialog(this, "Enter new number of tickets:",
                    selectedEvent.getTicketsAvailable());

            try {
                int newTickets = Integer.parseInt(newTicketsStr);
                selectedEvent.setName(newName);
                selectedEvent.setDate(newDate);
                selectedEvent.setVenue(newVenue);
                selectedEvent.setTicketsAvailable(newTickets);
                updateTable();
                JOptionPane.showMessageDialog(this, "Event edited successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number for tickets.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an event to edit.");
        }
    }

    private void deleteEvent() {
        int selectedRow = eventsTable.getSelectedRow();
        if (selectedRow != -1) {
            eventsList.remove(selectedRow);
            updateTable();
            JOptionPane.showMessageDialog(this, "Event deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an event to delete.");
        }
    }

    private void updateTable() {
        // Update the table model with the current events list
        eventsTableModel.setRowCount(0); // Clear the existing rows
        for (Event event : eventsList) {
            eventsTableModel.addRow(
                    new Object[] { event.getName(), event.getDate(), event.getVenue(), event.getTicketsAvailable() });
        }
    }
}

class TicketBookingFrame extends JFrame {
    private final JComboBox<String> eventComboBox;
    private final JTextField ticketCountField;
    private final JButton bookButton;

    public TicketBookingFrame() {
        setTitle("Ticket Booking");
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Select Event:"));
        eventComboBox = new JComboBox<>(new String[] { "Concert 1", "Concert 2" });
        panel.add(eventComboBox);

        panel.add(new JLabel("Number of Tickets:"));
        ticketCountField = new JTextField();
        panel.add(ticketCountField);

        bookButton = new JButton("Book Tickets");
        panel.add(bookButton);

        add(panel);

        setVisible(true);
    }
}

class TicketVerificationFrame extends JFrame {
    private final JTextField ticketIDField;
    private final JButton verifyButton;

    public TicketVerificationFrame() {
        setTitle("Ticket Verification");
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        panel.add(new JLabel("Enter Ticket ID:"));
        ticketIDField = new JTextField();
        panel.add(ticketIDField);

        verifyButton = new JButton("Verify Ticket");
        panel.add(verifyButton);

        add(panel);

        setVisible(true);
    }
}


class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1)); // Create a simple layout for buttons

        // Add buttons for various actions
        JButton manageEventsButton = new JButton("Manage Events");
        JButton manageCustomersButton = new JButton("Manage Customers");
        JButton viewReportsButton = new JButton("View Reports");

        // Add listeners to buttons
        manageEventsButton.addActionListener(e -> new ManageEventsFrame());
        manageCustomersButton.addActionListener(e -> new CustomerManagementFrame());
        viewReportsButton.addActionListener(e -> new ReportsFrame());

        // Add buttons to frame
        add(manageEventsButton);
        add(manageCustomersButton);
        add(viewReportsButton);

        // Add logout button
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(e -> logOut());
        add(logoutButton);

        setVisible(true);
    }

    private void logOut() {
        dispose(); // Close the current dashboard
        new LoginFrame(); // Open login screen
    }
}

class CustomerManagementFrame extends JFrame {
    private final JTable customerTable;
    private final JButton addCustomerButton, editCustomerButton, deleteCustomerButton;
    private final DefaultTableModel customerTableModel;

    public CustomerManagementFrame() {
        setTitle("Customer Management");
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Column names and sample customer data
        String[] columnNames = { "Username", "Name", "Age", "Gender", "Balance" };
        String[][] data = {
                { "customer1", "John Doe", "28", "Male", "1000" },
                { "customer2", "Jane Smith", "25", "Female", "1500" }
        };

        customerTableModel = new DefaultTableModel(data, columnNames);
        customerTable = new JTable(customerTableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);

        add(scrollPane, BorderLayout.CENTER);

        // Buttons for adding, editing, deleting customers
        JPanel panel = new JPanel();
        addCustomerButton = new JButton("Add Customer");
        editCustomerButton = new JButton("Edit Customer");
        deleteCustomerButton = new JButton("Delete Customer");

        panel.add(addCustomerButton);
        panel.add(editCustomerButton);
        panel.add(deleteCustomerButton);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);

        // Action Listeners for buttons
        addCustomerButton.addActionListener(e -> new RegisterFrame()); // Open register customer frame
        editCustomerButton.addActionListener(e -> editCustomer());
        deleteCustomerButton.addActionListener(e -> deleteCustomer());
    }

    private void editCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) customerTable.getValueAt(selectedRow, 0);
            // You can use this username to load customer data for editing
            JOptionPane.showMessageDialog(this, "Editing customer: " + username);
            // Implement the customer edit functionality as needed
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to edit!");
        }
    }

    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) customerTable.getValueAt(selectedRow, 0);
            // You can delete the customer using the username
            LoginFrame.users.remove(username);
            customerTableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Deleted customer: " + username);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete!");
        }
    }
}

// class CustomerManagementFrame extends JFrame {
// private final JTable customerTable;
// private final JButton addCustomerButton, editCustomerButton,
// deleteCustomerButton;

// public CustomerManagementFrame() {
// setTitle("Customer Management");
// setSize(500, 400);
// setLocationRelativeTo(null);

// // Sample customer data
// String[] columnNames = { "Username", "Name", "Age", "Gender", "Balance" };
// String[][] data = {
// { "customer1", "John Doe", "28", "Male", "1000" },
// { "customer2", "Jane Smith", "25", "Female", "1500" }
// };

// customerTable = new JTable(data, columnNames);
// JScrollPane scrollPane = new JScrollPane(customerTable);

// add(scrollPane, BorderLayout.CENTER);

// // Buttons for adding, editing, deleting customers
// JPanel panel = new JPanel();
// addCustomerButton = new JButton("Add Customer");
// editCustomerButton = new JButton("Edit Customer");
// deleteCustomerButton = new JButton("Delete Customer");

// panel.add(addCustomerButton);
// panel.add(editCustomerButton);
// panel.add(deleteCustomerButton);
// add(panel, BorderLayout.SOUTH);

// setVisible(true);

// // Add Action Listeners
// addCustomerButton.addActionListener(e -> new RegisterFrame()); // Open
// register customer frame
// editCustomerButton.addActionListener(e -> editCustomer());
// deleteCustomerButton.addActionListener(e -> deleteCustomer());
// }

// private void editCustomer() {
// int selectedRow = customerTable.getSelectedRow();
// if (selectedRow != -1) {
// String username = (String) customerTable.getValueAt(selectedRow, 0);
// // You can use this username to load customer data for editing
// JOptionPane.showMessageDialog(this, "Editing customer: " + username);
// } else {
// JOptionPane.showMessageDialog(this, "Please select a customer to edit!");
// }
// }

// private void deleteCustomer() {
// int selectedRow = customerTable.getSelectedRow();
// if (selectedRow != -1) {
// String username = (String) customerTable.getValueAt(selectedRow, 0);
// // You can delete the customer using the username
// JOptionPane.showMessageDialog(this, "Deleted customer: " + username);
// } else {
// JOptionPane.showMessageDialog(this, "Please select a customer to delete!");
// }
// }
// }

class Event {
    private String name;
    private String date;
    private String venue;
    private int ticketsAvailable;

    public Event(String name, String date, String venue, int ticketsAvailable) {
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.ticketsAvailable = ticketsAvailable;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }
}
