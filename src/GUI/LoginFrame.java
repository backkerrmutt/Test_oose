
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Anuphong_PC
 */

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
