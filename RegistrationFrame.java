import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class RegistrationFrame extends JFrame {
    private static final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font SECTION_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 12);
    private static final Dimension BUTTON_SIZE = new Dimension(158, 36);
    private static final int MAX_ACTIVITIES = 8;
    private static final String SEARCH_PLACEHOLDER = "Search by name or matric number";
    private static final String DATA_FILE_NAME = "registrations.csv";

    private JTextField nameField;
    private JTextField matricField;
    private JTextField phoneField;
    private JTextField searchField;
    private JComboBox<String> categoryComboBox;
    private JCheckBox certificateCheckBox;
    private JCheckBox foodCheckBox;
    private JCheckBox tshirtCheckBox;
    private JRadioButton cashRadioButton;
    private JRadioButton onlineBankingRadioButton;
    private JRadioButton eWalletRadioButton;
    private JLabel totalFeeLabel;
    private JLabel totalParticipantsValueLabel;
    private JLabel totalCollectionValueLabel;
    private JLabel mostSelectedCategoryValueLabel;
    private JLabel workshopCountLabel;
    private JLabel seminarCountLabel;
    private JLabel competitionCountLabel;
    private JLabel cashCountLabel;
    private JLabel onlineBankingCountLabel;
    private JLabel eWalletCountLabel;
    private JTable registrationTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private ButtonGroup paymentGroup;
    private DefaultListModel<String> activityListModel;

    private ArrayList<EventRegistration> registrationList;
    private FeeCalculator feeCalculator;
    private boolean suppressSelectionLoad;

    public RegistrationFrame() {
        registrationList = new ArrayList<>();
        feeCalculator = new FeeCalculator();

        setTitle("Java Programming Project - Student Event Registration and Fee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1320, 760);
        setLocationRelativeTo(null);
        setMinimumSize(getSize());

        buildInterface();
        loadRegistrationsFromFile();
        updateDashboardSummary();
        if (!registrationList.isEmpty()) {
            addActivity("Saved registrations loaded");
        }
        updateTotalFeeLabel();
    }

    private void buildInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout(16, 16));
        mainPanel.setBorder(new EmptyBorder(18, 22, 18, 22));
        mainPanel.setBackground(new Color(239, 243, 247));

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        mainPanel.add(createFormPanel(), BorderLayout.WEST);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        mainPanel.add(createSidePanel(), BorderLayout.EAST);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        headerPanel.setBackground(new Color(32, 61, 84));
        headerPanel.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel eventNameLabel = new JLabel("FSKTM TechFest 2026", SwingConstants.CENTER);
        eventNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        eventNameLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Student Event Registration & Fee Management System",
                SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitleLabel.setForeground(new Color(225, 233, 240));

        headerPanel.add(eventNameLabel);
        headerPanel.add(subtitleLabel);
        return headerPanel;
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel(new BorderLayout(0, 12));
        sidePanel.setOpaque(false);
        sidePanel.setPreferredSize(new Dimension(310, 0));

        sidePanel.add(createEventInformationPanel(), BorderLayout.NORTH);
        sidePanel.add(createRecentActivitiesPanel(), BorderLayout.CENTER);
        return sidePanel;
    }

    private JPanel createEventInformationPanel() {
        JPanel eventInfoPanel = createTitledPanel("Event Information", new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 6, 5, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        addEventInfoRow(eventInfoPanel, gbc, 0, "Event Name:", "FSKTM TechFest 2026");
        addEventInfoRow(eventInfoPanel, gbc, 1, "Date:", "15 June 2026");
        addEventInfoRow(eventInfoPanel, gbc, 2, "Venue:", "Auditorium FSKTM");
        addEventInfoRow(eventInfoPanel, gbc, 3, "Organizer:",
                "Faculty of Computer Science and Information Technology");
        addEventInfoRow(eventInfoPanel, gbc, 4, "Objective:",
                "To encourage innovation, networking, and technology awareness among students.");
        return eventInfoPanel;
    }

    private void addEventInfoRow(JPanel panel, GridBagConstraints gbc, int row,
                                 String labelText, String valueText) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        JLabel value = new JLabel("<html><body style='width:170px'>" + valueText + "</body></html>");
        value.setFont(MAIN_FONT);
        panel.add(value, gbc);
    }

    private JPanel createRecentActivitiesPanel() {
        JPanel activitiesPanel = createTitledPanel("Recent Activities", new BorderLayout(8, 8));
        activityListModel = new DefaultListModel<>();
        JList<String> activityList = new JList<>(activityListModel);
        activityList.setFont(MAIN_FONT);
        activityList.setFixedCellHeight(28);
        activityList.setBackground(new Color(250, 252, 254));
        activitiesPanel.add(new JScrollPane(activityList), BorderLayout.CENTER);
        return activitiesPanel;
    }

    private JPanel createFormPanel() {
        JPanel formContainer = new JPanel(new BorderLayout(10, 12));
        formContainer.setBackground(Color.WHITE);
        formContainer.setBorder(createTitledBorder("Student Information"));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        nameField = new JTextField(22);
        matricField = new JTextField(22);
        phoneField = new JTextField(22);
        categoryComboBox = new JComboBox<>(new String[] {
            FeeCalculator.WORKSHOP,
            FeeCalculator.SEMINAR,
            FeeCalculator.COMPETITION
        });

        addSectionLabel(formPanel, gbc, 0, "Student Information");
        addInputRow(formPanel, gbc, 1, "Student Name:", nameField);
        addInputRow(formPanel, gbc, 2, "Matric Number:", matricField);
        addInputRow(formPanel, gbc, 3, "Phone Number:", phoneField);

        addSectionLabel(formPanel, gbc, 4, "Event Details");
        addInputRow(formPanel, gbc, 5, "Event Category:", categoryComboBox);

        certificateCheckBox = new JCheckBox(FeeCalculator.CERTIFICATE);
        foodCheckBox = new JCheckBox(FeeCalculator.FOOD_PACKAGE);
        tshirtCheckBox = new JCheckBox(FeeCalculator.EVENT_TSHIRT);
        setComponentFont(nameField, matricField, phoneField, categoryComboBox,
                certificateCheckBox, foodCheckBox, tshirtCheckBox);

        addSectionLabel(formPanel, gbc, 6, "Optional Add-ons");
        addFullRow(formPanel, gbc, 7, certificateCheckBox);
        addFullRow(formPanel, gbc, 8, foodCheckBox);
        addFullRow(formPanel, gbc, 9, tshirtCheckBox);

        cashRadioButton = new JRadioButton("Cash");
        onlineBankingRadioButton = new JRadioButton("Online Banking");
        eWalletRadioButton = new JRadioButton("E-Wallet");
        setComponentFont(cashRadioButton, onlineBankingRadioButton, eWalletRadioButton);

        paymentGroup = new ButtonGroup();
        paymentGroup.add(cashRadioButton);
        paymentGroup.add(onlineBankingRadioButton);
        paymentGroup.add(eWalletRadioButton);

        addSectionLabel(formPanel, gbc, 10, "Payment Method");
        addFullRow(formPanel, gbc, 11, cashRadioButton);
        addFullRow(formPanel, gbc, 12, onlineBankingRadioButton);
        addFullRow(formPanel, gbc, 13, eWalletRadioButton);

        totalFeeLabel = new JLabel("Total Fee: RM0.00");
        totalFeeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalFeeLabel.setForeground(new Color(31, 104, 72));
        addFullRow(formPanel, gbc, 14, totalFeeLabel);

        addAutoCalculationListeners();

        formContainer.add(formPanel, BorderLayout.CENTER);
        formContainer.add(createButtonPanel(), BorderLayout.SOUTH);
        return formContainer;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        buttonPanel.setOpaque(false);

        JButton calculateButton = new JButton("Calculate Total");
        JButton registerButton = new JButton("Register");
        JButton updateButton = new JButton("Update Selected");
        JButton clearButton = new JButton("Clear");
        JButton deleteButton = new JButton("Delete Selected");
        JButton exitButton = new JButton("Exit");
        setButtonStyle(calculateButton, registerButton, updateButton, clearButton, deleteButton, exitButton);

        calculateButton.addActionListener(event -> updateTotalFeeLabel());
        registerButton.addActionListener(event -> registerStudent());
        updateButton.addActionListener(event -> updateSelectedRegistration());
        clearButton.addActionListener(event -> clearForm());
        deleteButton.addActionListener(event -> deleteSelectedRegistration());
        exitButton.addActionListener(event -> System.exit(0));

        buttonPanel.add(calculateButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(createTitledBorder("Registered Participants"));

        JPanel tableHeaderPanel = new JPanel(new BorderLayout(8, 8));
        tableHeaderPanel.setOpaque(false);

        tableHeaderPanel.add(createDashboardPanel(), BorderLayout.CENTER);
        tableHeaderPanel.add(createSearchPanel(), BorderLayout.SOUTH);
        tablePanel.add(tableHeaderPanel, BorderLayout.NORTH);

        String[] columns = {"Name", "Matric No", "Phone", "Category", "Add-ons", "Payment", "Total Fee"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        registrationTable = new JTable(tableModel);
        registrationTable.setRowHeight(28);
        registrationTable.setFont(MAIN_FONT);
        registrationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        registrationTable.setGridColor(new Color(228, 234, 240));
        registrationTable.setSelectionBackground(new Color(215, 231, 245));
        registrationTable.setSelectionForeground(Color.BLACK);
        registrationTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        registrationTable.getTableHeader().setBackground(new Color(232, 238, 244));
        registrationTable.getTableHeader().setForeground(new Color(35, 52, 67));

        tableSorter = new TableRowSorter<>(tableModel);
        registrationTable.setRowSorter(tableSorter);
        registrationTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && !suppressSelectionLoad
                    && registrationTable.getSelectedRow() != -1) {
                loadSelectedRegistrationIntoForm();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(registrationTable);
        tableScrollPane.setPreferredSize(new Dimension(0, 350));

        JPanel tableCenterPanel = new JPanel(new BorderLayout(0, 12));
        tableCenterPanel.setOpaque(false);
        tableCenterPanel.add(tableScrollPane, BorderLayout.NORTH);
        tableCenterPanel.add(createBottomDashboardPanel(), BorderLayout.CENTER);

        tablePanel.add(tableCenterPanel, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(MAIN_FONT);
        searchField = new JTextField(28);
        searchField.setFont(MAIN_FONT);
        searchField.setText(SEARCH_PLACEHOLDER);
        searchField.setForeground(Color.GRAY);
        searchField.setToolTipText(SEARCH_PLACEHOLDER);

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event) {
                if (isSearchPlaceholderVisible()) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent event) {
                if (searchField.getText().trim().isEmpty()) {
                    showSearchPlaceholder();
                }
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                applyTableFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                applyTableFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                applyTableFilter();
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        return searchPanel;
    }

    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = createTitledPanel("Dashboard Summary", new GridLayout(1, 3, 10, 10));
        totalParticipantsValueLabel = new JLabel("0", SwingConstants.CENTER);
        totalCollectionValueLabel = new JLabel("RM0.00", SwingConstants.CENTER);
        mostSelectedCategoryValueLabel = new JLabel("None", SwingConstants.CENTER);

        dashboardPanel.add(createSummaryCard("Total Participants", totalParticipantsValueLabel));
        dashboardPanel.add(createSummaryCard("Total Collection", totalCollectionValueLabel));
        dashboardPanel.add(createSummaryCard("Most Selected Category", mostSelectedCategoryValueLabel));
        return dashboardPanel;
    }

    private JPanel createSummaryCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout(4, 4));
        card.setBackground(new Color(250, 252, 254));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 230, 237)),
                new EmptyBorder(10, 10, 10, 10)));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(76, 91, 105));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        valueLabel.setForeground(new Color(32, 61, 84));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createBottomDashboardPanel() {
        JPanel bottomDashboardPanel = new JPanel(new GridLayout(1, 2, 12, 12));
        bottomDashboardPanel.setOpaque(false);
        bottomDashboardPanel.setPreferredSize(new Dimension(0, 145));

        bottomDashboardPanel.add(createRegistrationStatisticsPanel());
        bottomDashboardPanel.add(createPaymentSummaryPanel());
        return bottomDashboardPanel;
    }

    private JPanel createRegistrationStatisticsPanel() {
        JPanel statisticsPanel = createTitledPanel("Registration Statistics", new GridLayout(1, 3, 10, 10));
        workshopCountLabel = new JLabel("0");
        seminarCountLabel = new JLabel("0");
        competitionCountLabel = new JLabel("0");

        statisticsPanel.add(createSmallDashboardCard("Workshop", workshopCountLabel));
        statisticsPanel.add(createSmallDashboardCard("Seminar", seminarCountLabel));
        statisticsPanel.add(createSmallDashboardCard("Competition", competitionCountLabel));
        return statisticsPanel;
    }

    private JPanel createPaymentSummaryPanel() {
        JPanel paymentPanel = createTitledPanel("Payment Summary", new GridLayout(1, 3, 10, 10));
        cashCountLabel = new JLabel("0");
        onlineBankingCountLabel = new JLabel("0");
        eWalletCountLabel = new JLabel("0");

        paymentPanel.add(createSmallDashboardCard("Cash", cashCountLabel));
        paymentPanel.add(createSmallDashboardCard("Online Banking", onlineBankingCountLabel));
        paymentPanel.add(createSmallDashboardCard("E-Wallet", eWalletCountLabel));
        return paymentPanel;
    }

    private JPanel createSmallDashboardCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout(4, 4)) {
            @Override
            protected void paintComponent(java.awt.Graphics graphics) {
                java.awt.Graphics2D graphics2D = (java.awt.Graphics2D) graphics.create();
                graphics2D.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                graphics2D.setColor(new Color(0, 0, 0, 18));
                graphics2D.fillRoundRect(4, 5, getWidth() - 8, getHeight() - 8, 10, 10);
                graphics2D.setColor(getBackground());
                graphics2D.fillRoundRect(1, 1, getWidth() - 8, getHeight() - 8, 10, 10);
                graphics2D.dispose();
                super.paintComponent(graphics);
            }
        };
        card.setOpaque(false);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 230, 237)),
                new EmptyBorder(10, 10, 10, 10)));
        card.setPreferredSize(new Dimension(0, 78));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titleLabel.setForeground(new Color(76, 91, 105));

        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(new Color(32, 61, 84));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private void addInputRow(JPanel panel, GridBagConstraints gbc, int row, String labelText,
                             Component inputComponent) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(MAIN_FONT);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(inputComponent, gbc);
    }

    private void addSectionLabel(JPanel panel, GridBagConstraints gbc, int row, String text) {
        JLabel sectionLabel = new JLabel(text);
        sectionLabel.setFont(SECTION_FONT);
        sectionLabel.setForeground(new Color(48, 69, 86));
        addFullRow(panel, gbc, row, sectionLabel);
    }

    private void addFullRow(JPanel panel, GridBagConstraints gbc, int row, Component component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panel.add(component, gbc);
        gbc.gridwidth = 1;
    }

    private void setComponentFont(Component... components) {
        for (Component component : components) {
            component.setFont(MAIN_FONT);
        }
    }

    private void setButtonStyle(JButton... buttons) {
        for (JButton button : buttons) {
            button.setFont(BUTTON_FONT);
            button.setPreferredSize(BUTTON_SIZE);
        }
    }

    private JPanel createTitledPanel(String title, java.awt.LayoutManager layoutManager) {
        JPanel panel = new JPanel(layoutManager);
        panel.setBackground(Color.WHITE);
        panel.setBorder(createTitledBorder(title));
        return panel;
    }

    private javax.swing.border.Border createTitledBorder(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(218, 226, 233)),
                title);
        titledBorder.setTitleFont(SECTION_FONT);
        titledBorder.setTitleColor(new Color(48, 69, 86));
        return BorderFactory.createCompoundBorder(titledBorder, new EmptyBorder(12, 12, 12, 12));
    }

    private void addAutoCalculationListeners() {
        categoryComboBox.addActionListener(event -> updateTotalFeeLabel());
        certificateCheckBox.addItemListener(event -> updateTotalFeeLabel());
        foodCheckBox.addItemListener(event -> updateTotalFeeLabel());
        tshirtCheckBox.addItemListener(event -> updateTotalFeeLabel());
    }

    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty()) {
            showValidationError("Student name cannot be empty.");
            nameField.requestFocus();
            return false;
        }
        if (matricField.getText().trim().isEmpty()) {
            showValidationError("Matric number cannot be empty.");
            matricField.requestFocus();
            return false;
        }
        if (phoneField.getText().trim().isEmpty()) {
            showValidationError("Phone number cannot be empty.");
            phoneField.requestFocus();
            return false;
        }
        if (!phoneField.getText().trim().matches("\\d+")) {
            showValidationError("Phone number should contain digits only.");
            phoneField.requestFocus();
            return false;
        }
        if (paymentGroup.getSelection() == null) {
            showValidationError("Please select a payment method.");
            return false;
        }
        return true;
    }

    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    private double updateTotalFeeLabel() {
        String category = (String) categoryComboBox.getSelectedItem();
        double total = feeCalculator.calculateTotalFee(category, getSelectedAddOns());
        totalFeeLabel.setText(String.format("Total Fee: RM%.2f", total));
        return total;
    }

    private void registerStudent() {
        if (!validateForm()) {
            return;
        }
        if (isDuplicateMatricNumber(matricField.getText().trim(), -1)) {
            JOptionPane.showMessageDialog(this,
                    "This matric number is already registered. Please update the existing record instead.",
                    "Duplicate Matric Number", JOptionPane.WARNING_MESSAGE);
            matricField.requestFocus();
            return;
        }

        EventRegistration registration = createRegistrationFromForm();
        registrationList.add(registration);
        addRegistrationToTable(registration);
        saveRegistrationsToFile();
        updateDashboardSummary();
        addActivity(registration.getStudent().getName() + " registered successfully");
        addActivity("Total collection updated");

        JOptionPane.showMessageDialog(this, "Student registration saved successfully.");
        resetForm(false);
    }

    private EventRegistration createRegistrationFromForm() {
        // Student object keeps personal details separate from event and payment details.
        Student student = new Student(
                nameField.getText().trim(),
                matricField.getText().trim(),
                phoneField.getText().trim()
        );

        String category = (String) categoryComboBox.getSelectedItem();
        List<String> selectedAddOns = getSelectedAddOns();
        String paymentMethod = getSelectedPaymentMethod();
        double totalFee = updateTotalFeeLabel();

        return new EventRegistration(student, category, selectedAddOns, paymentMethod, totalFee);
    }

    private boolean isDuplicateMatricNumber(String matricNumber, int ignoredIndex) {
        for (int index = 0; index < registrationList.size(); index++) {
            if (index != ignoredIndex
                    && registrationList.get(index).getStudent().getMatricNumber().equalsIgnoreCase(matricNumber)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getSelectedAddOns() {
        ArrayList<String> addOns = new ArrayList<>();
        if (certificateCheckBox.isSelected()) {
            addOns.add(FeeCalculator.CERTIFICATE);
        }
        if (foodCheckBox.isSelected()) {
            addOns.add(FeeCalculator.FOOD_PACKAGE);
        }
        if (tshirtCheckBox.isSelected()) {
            addOns.add(FeeCalculator.EVENT_TSHIRT);
        }
        return addOns;
    }

    private String getSelectedPaymentMethod() {
        if (cashRadioButton.isSelected()) {
            return "Cash";
        }
        if (onlineBankingRadioButton.isSelected()) {
            return "Online Banking";
        }
        if (eWalletRadioButton.isSelected()) {
            return "E-Wallet";
        }
        return "";
    }

    private void loadSelectedRegistrationIntoForm() {
        int selectedRow = registrationTable.getSelectedRow();
        int modelRow = registrationTable.convertRowIndexToModel(selectedRow);
        EventRegistration registration = registrationList.get(modelRow);

        nameField.setText(registration.getStudent().getName());
        matricField.setText(registration.getStudent().getMatricNumber());
        phoneField.setText(registration.getStudent().getPhoneNumber());
        categoryComboBox.setSelectedItem(registration.getEventCategory());
        setSelectedAddOns(registration.getAddOns());
        setSelectedPaymentMethod(registration.getPaymentMethod());
        totalFeeLabel.setText(String.format("Total Fee: RM%.2f", registration.getTotalFee()));
    }

    private void setSelectedAddOns(List<String> addOns) {
        certificateCheckBox.setSelected(addOns.contains(FeeCalculator.CERTIFICATE));
        foodCheckBox.setSelected(addOns.contains(FeeCalculator.FOOD_PACKAGE));
        tshirtCheckBox.setSelected(addOns.contains(FeeCalculator.EVENT_TSHIRT));
    }

    private void setSelectedPaymentMethod(String paymentMethod) {
        paymentGroup.clearSelection();
        if ("Cash".equals(paymentMethod)) {
            cashRadioButton.setSelected(true);
        } else if ("Online Banking".equals(paymentMethod)) {
            onlineBankingRadioButton.setSelected(true);
        } else if ("E-Wallet".equals(paymentMethod)) {
            eWalletRadioButton.setSelected(true);
        }
    }

    private void addRegistrationToTable(EventRegistration registration) {
        tableModel.addRow(createTableRow(registration));
    }

    private Object[] createTableRow(EventRegistration registration) {
        String addOnText = registration.getAddOns().isEmpty()
                ? "None"
                : String.join(", ", registration.getAddOns());

        return new Object[] {
            registration.getStudent().getName(),
            registration.getStudent().getMatricNumber(),
            registration.getStudent().getPhoneNumber(),
            registration.getEventCategory(),
            addOnText,
            registration.getPaymentMethod(),
            String.format("RM%.2f", registration.getTotalFee())
        };
    }

    private void updateSelectedRegistration() {
        int selectedRow = registrationTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a participant to update.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validateForm()) {
            return;
        }

        int modelRow = registrationTable.convertRowIndexToModel(selectedRow);
        if (isDuplicateMatricNumber(matricField.getText().trim(), modelRow)) {
            JOptionPane.showMessageDialog(this,
                    "This matric number is already registered. Please update the existing record instead.",
                    "Duplicate Matric Number", JOptionPane.WARNING_MESSAGE);
            matricField.requestFocus();
            return;
        }

        EventRegistration updatedRegistration = createRegistrationFromForm();

        // Keep the ArrayList, JTable row, and CSV file synchronized after editing a participant.
        registrationList.set(modelRow, updatedRegistration);
        Object[] updatedRow = createTableRow(updatedRegistration);
        for (int column = 0; column < updatedRow.length; column++) {
            tableModel.setValueAt(updatedRow[column], modelRow, column);
        }
        saveRegistrationsToFile();
        updateDashboardSummary();
        addActivity(updatedRegistration.getStudent().getName() + " updated registration");
        addActivity("Total collection updated");

        restoreSelectionAfterTableChange(modelRow);
        JOptionPane.showMessageDialog(this, "Selected participant updated successfully.");
    }

    private void deleteSelectedRegistration() {
        int selectedRow = registrationTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a participant to delete.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = registrationTable.convertRowIndexToModel(selectedRow);
        String deletedName = registrationList.get(modelRow).getStudent().getName();
        registrationList.remove(modelRow);
        tableModel.removeRow(modelRow);
        saveRegistrationsToFile();
        updateDashboardSummary();
        addActivity(deletedName + " deleted from registration");
        addActivity("Total collection updated");
        JOptionPane.showMessageDialog(this, "Selected registration deleted.");
        resetForm(false);
    }

    private void clearForm() {
        resetForm(true);
    }

    private void resetForm(boolean logActivity) {
        suppressSelectionLoad = true;
        nameField.setText("");
        matricField.setText("");
        phoneField.setText("");
        categoryComboBox.setSelectedIndex(0);
        certificateCheckBox.setSelected(false);
        foodCheckBox.setSelected(false);
        tshirtCheckBox.setSelected(false);
        paymentGroup.clearSelection();
        totalFeeLabel.setText("Total Fee: RM0.00");
        registrationTable.clearSelection();
        clearSearchField();
        suppressSelectionLoad = false;
        if (logActivity) {
            addActivity("Form cleared");
        }
        nameField.requestFocus();
    }

    private void updateDashboardSummary() {
        int totalParticipants = registrationList.size();
        double totalCollection = 0.0;
        int workshopCount = 0;
        int seminarCount = 0;
        int competitionCount = 0;
        int cashCount = 0;
        int onlineBankingCount = 0;
        int eWalletCount = 0;

        for (EventRegistration registration : registrationList) {
            totalCollection += registration.getTotalFee();
            String category = registration.getEventCategory();
            if (FeeCalculator.WORKSHOP.equals(category)) {
                workshopCount++;
            } else if (FeeCalculator.SEMINAR.equals(category)) {
                seminarCount++;
            } else if (FeeCalculator.COMPETITION.equals(category)) {
                competitionCount++;
            }

            String paymentMethod = registration.getPaymentMethod();
            if ("Cash".equals(paymentMethod)) {
                cashCount++;
            } else if ("Online Banking".equals(paymentMethod)) {
                onlineBankingCount++;
            } else if ("E-Wallet".equals(paymentMethod)) {
                eWalletCount++;
            }
        }

        totalParticipantsValueLabel.setText(String.valueOf(totalParticipants));
        totalCollectionValueLabel.setText(String.format("RM%.2f", totalCollection));
        mostSelectedCategoryValueLabel.setText(
                getMostSelectedCategory(workshopCount, seminarCount, competitionCount));
        workshopCountLabel.setText(String.valueOf(workshopCount));
        seminarCountLabel.setText(String.valueOf(seminarCount));
        competitionCountLabel.setText(String.valueOf(competitionCount));
        cashCountLabel.setText(String.valueOf(cashCount));
        onlineBankingCountLabel.setText(String.valueOf(onlineBankingCount));
        eWalletCountLabel.setText(String.valueOf(eWalletCount));
    }

    private String getMostSelectedCategory(int workshopCount, int seminarCount, int competitionCount) {
        if (workshopCount == 0 && seminarCount == 0 && competitionCount == 0) {
            return "None";
        }
        int highestCount = Math.max(workshopCount, Math.max(seminarCount, competitionCount));
        ArrayList<String> topCategories = new ArrayList<>();

        if (workshopCount == highestCount) {
            topCategories.add("Workshop");
        }
        if (seminarCount == highestCount) {
            topCategories.add("Seminar");
        }
        if (competitionCount == highestCount) {
            topCategories.add("Competition");
        }

        return String.join(" & ", topCategories);
    }

    private void addActivity(String activityText) {
        if (activityListModel == null) {
            return;
        }
        activityListModel.add(0, activityText);
        while (activityListModel.size() > MAX_ACTIVITIES) {
            activityListModel.remove(activityListModel.size() - 1);
        }
    }

    private void applyTableFilter() {
        if (tableSorter == null) {
            return;
        }
        if (isSearchPlaceholderVisible()) {
            tableSorter.setRowFilter(null);
            return;
        }

        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            tableSorter.setRowFilter(null);
        } else {
            String escapedText = Pattern.quote(searchText);
            tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + escapedText, 0, 1));
        }
    }

    private boolean isSearchPlaceholderVisible() {
        return searchField != null
                && SEARCH_PLACEHOLDER.equals(searchField.getText())
                && Color.GRAY.equals(searchField.getForeground());
    }

    private void showSearchPlaceholder() {
        searchField.setForeground(Color.GRAY);
        searchField.setText(SEARCH_PLACEHOLDER);
    }

    private void clearSearchField() {
        if (searchField == null) {
            return;
        }
        searchField.setForeground(Color.BLACK);
        searchField.setText("");
        if (!searchField.hasFocus()) {
            showSearchPlaceholder();
        }
        applyTableFilter();
    }

    private void restoreSelectionAfterTableChange(int modelRow) {
        int viewRow = registrationTable.convertRowIndexToView(modelRow);
        if (viewRow >= 0) {
            registrationTable.setRowSelectionInterval(viewRow, viewRow);
        } else {
            registrationTable.clearSelection();
        }
    }

    private void loadRegistrationsFromFile() {
        File dataFile = new File(DATA_FILE_NAME);
        if (!dataFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> values = parseCsvLine(line);
                if (values.size() != 7) {
                    continue;
                }

                Student student = new Student(values.get(0), values.get(1), values.get(2));
                List<String> addOns = parseAddOns(values.get(4));
                double totalFee = Double.parseDouble(values.get(6));
                EventRegistration registration = new EventRegistration(
                        student, values.get(3), addOns, values.get(5), totalFee);
                registrationList.add(registration);
                addRegistrationToTable(registration);
            }
        } catch (IOException | NumberFormatException exception) {
            JOptionPane.showMessageDialog(this,
                    "Saved registration records could not be loaded.",
                    "File Load Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void saveRegistrationsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE_NAME))) {
            for (EventRegistration registration : registrationList) {
                writer.write(toCsvLine(registration));
                writer.newLine();
            }
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this,
                    "Registration records could not be saved to file.",
                    "File Save Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String toCsvLine(EventRegistration registration) {
        String addOns = String.join(";", registration.getAddOns());
        return escapeCsv(registration.getStudent().getName()) + ","
                + escapeCsv(registration.getStudent().getMatricNumber()) + ","
                + escapeCsv(registration.getStudent().getPhoneNumber()) + ","
                + escapeCsv(registration.getEventCategory()) + ","
                + escapeCsv(addOns) + ","
                + escapeCsv(registration.getPaymentMethod()) + ","
                + registration.getTotalFee();
    }

    private String escapeCsv(String value) {
        String safeValue = value == null ? "" : value;
        return "\"" + safeValue.replace("\"", "\"\"") + "\"";
    }

    private List<String> parseCsvLine(String line) {
        ArrayList<String> values = new ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        boolean insideQuotes = false;

        for (int index = 0; index < line.length(); index++) {
            char currentChar = line.charAt(index);
            if (currentChar == '"') {
                if (insideQuotes && index + 1 < line.length() && line.charAt(index + 1) == '"') {
                    currentValue.append('"');
                    index++;
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (currentChar == ',' && !insideQuotes) {
                values.add(currentValue.toString());
                currentValue.setLength(0);
            } else {
                currentValue.append(currentChar);
            }
        }
        values.add(currentValue.toString());
        return values;
    }

    private List<String> parseAddOns(String addOnText) {
        ArrayList<String> addOns = new ArrayList<>();
        if (addOnText == null || addOnText.trim().isEmpty()) {
            return addOns;
        }

        String[] items = addOnText.split(";");
        for (String item : items) {
            if (!item.trim().isEmpty()) {
                addOns.add(item.trim());
            }
        }
        return addOns;
    }
}
