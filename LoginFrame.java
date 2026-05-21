import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "1234";

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Java Programming Project - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        buildInterface();
    }

    private void buildInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 28, 20, 28));
        mainPanel.setBackground(new Color(241, 246, 252));

        JLabel titleLabel = new JLabel("Student Event Registration System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(22, 68, 100));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        usernameField = new JTextField(18);
        passwordField = new JPasswordField(18);

        addFormRow(formPanel, gbc, 0, "Username:", usernameField);
        addFormRow(formPanel, gbc, 1, "Password:", passwordField);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");
        loginButton.addActionListener(event -> validateLogin());
        exitButton.addActionListener(event -> System.exit(0));
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        getRootPane().setDefaultButton(loginButton);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText,
                            JTextField inputField) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(inputField, gbc);
    }

    private void validateLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (DEFAULT_USERNAME.equals(username) && DEFAULT_PASSWORD.equals(password)) {
            JOptionPane.showMessageDialog(this, "Login successful. Welcome, event staff!");
            RegistrationFrame registrationFrame = new RegistrationFrame();
            registrationFrame.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.",
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            passwordField.requestFocus();
        }
    }
}
