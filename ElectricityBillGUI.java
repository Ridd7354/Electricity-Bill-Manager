import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class ElectricityBillGUI extends JFrame {

    private Connection conn;
    private JTextField tfName, tfUnits, tfSearch;
    private JButton btnCalculate, btnSearch;

    public ElectricityBillGUI() {
        setTitle("Electricity Bill Manager");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Color theme (slightly darker but readable)
        Color bgColor = new Color(200, 220, 240);     // light bluish-gray
        Color btnBlue = new Color(173, 216, 230);     // light sky blue for calculate
        Color btnGreen = new Color(34, 139, 34);      // forest green for search
        Color labelColor = new Color(25, 25, 25);     // dark text

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(bgColor);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(bgColor);

        JLabel lblName = new JLabel("Customer Name:");
        lblName.setForeground(labelColor);
        JLabel lblUnits = new JLabel("Units Consumed:");
        lblUnits.setForeground(labelColor);

        tfName = new JTextField();
        tfUnits = new JTextField();
        inputPanel.add(lblName);
        inputPanel.add(tfName);
        inputPanel.add(lblUnits);
        inputPanel.add(tfUnits);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(bgColor);
        btnCalculate = new JButton("Calculate & Save");
        btnCalculate.setBackground(btnBlue);
        btnCalculate.setForeground(Color.BLACK); // ✅ Changed to black text
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalculate.setFocusPainted(false);
        buttonPanel.add(btnCalculate);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(bgColor);
        tfSearch = new JTextField(15);
        btnSearch = new JButton("Search");
        btnSearch.setBackground(btnGreen);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnSearch.setFocusPainted(false);
        JLabel lblSearch = new JLabel("Search by Name:");
        lblSearch.setForeground(labelColor);
        searchPanel.add(lblSearch);
        searchPanel.add(tfSearch);
        searchPanel.add(btnSearch);

        // Add panels to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(searchPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Action listeners
        btnCalculate.addActionListener(e -> {
            try {
                String name = tfName.getText().trim();
                int units = Integer.parseInt(tfUnits.getText().trim());
                int amount = calculateBill(units);
                saveBill(name, units, amount);
                JOptionPane.showMessageDialog(this,
                        "Bill saved successfully!\nAmount: ₹" + amount,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid numeric units.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSearch.addActionListener(e -> {
            String name = tfSearch.getText().trim();
            String result = getBill(name);
            JOptionPane.showMessageDialog(this, result,
                    "Search Result", JOptionPane.INFORMATION_MESSAGE);
        });

        connectDatabase();
        setVisible(true);
    }

    private int calculateBill(int units) {
        int amount;
        if (units <= 100) {
            amount = units * 5;
        } else if (units <= 200) {
            amount = 100 * 5 + (units - 100) * 7;
        } else {
            amount = 100 * 5 + 100 * 7 + (units - 200) * 10;
        }
        return amount;
    }

    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/electricity", "root", "password");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS electricity_bill(" +
                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "name VARCHAR(100), units INT, amount INT)");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Database connection failed: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveBill(String name, int units, int amount) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO electricity_bill(name, units, amount) VALUES(?,?,?)");
            ps.setString(1, name);
            ps.setInt(2, units);
            ps.setInt(3, amount);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getBill(String name) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM electricity_bill WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return "ID: " + rs.getInt("id") +
                        "\nName: " + rs.getString("name") +
                        "\nUnits: " + rs.getInt("units") +
                        "\nAmount: ₹" + rs.getInt("amount");
            } else {
                return "No record found for " + name;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving data.";
        }
    }

    public static void main(String[] args) {
        new ElectricityBillGUI();
    }
}
