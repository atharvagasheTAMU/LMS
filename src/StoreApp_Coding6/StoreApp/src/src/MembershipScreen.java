import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MembershipScreen extends JFrame implements ActionListener {
    private List<MembershipType> memberships;

    // Change the type of cmbMemberships to JComboBox<String>
    private JComboBox<String> cmbMemberships = new JComboBox<>();

    private JTextField txtLineOne = new JTextField(20);
    private JTextField txtCity = new JTextField(20);
    private JTextField txtState = new JTextField(20);
    private JTextField txtZip = new JTextField(20);
    private JTextField txtCountry = new JTextField(20);
    private JTextField txtContact = new JTextField(20);

    private JTextField txtCardNumber = new JTextField(20);
    private JTextField txtCardName = new JTextField(20);
    private JTextField txtStartDate = new JTextField(20);
    private JTextField txtEndDate = new JTextField(20);
    private JTextField txtCvv = new JTextField(20);

    private JButton btnSelectMembership = new JButton("Add Membership");

    public MembershipScreen(List<MembershipType> memberships) {
        this.memberships = memberships;

        this.setTitle("Membership Selection");
        this.setSize(600, 500);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Populate the membership dropdown with type names
        cmbMemberships.addItem("Gold");
        cmbMemberships.addItem("Silver");
        cmbMemberships.addItem("Bronze");

        mainPanel.add(createLabelAndComponent("Select Membership:", cmbMemberships));
        mainPanel.add(createLabelAndComponent("Address Line 1:", txtLineOne));
        mainPanel.add(createLabelAndComponent("City:", txtCity));
        mainPanel.add(createLabelAndComponent("State:", txtState));
        mainPanel.add(createLabelAndComponent("ZIP Code:", txtZip));
        mainPanel.add(createLabelAndComponent("Country:", txtCountry));
        mainPanel.add(createLabelAndComponent("Contact Number:", txtContact));

        // Align "Enter Card Details" label to the left
        JPanel cardDetailsLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cardDetailsLabelPanel.add(new JLabel("Enter Card Details:"));
        mainPanel.add(cardDetailsLabelPanel);

        mainPanel.add(createLabelAndComponent("Card Number:", txtCardNumber));
        mainPanel.add(createLabelAndComponent("Card Holder's Name:", txtCardName));
        mainPanel.add(createLabelAndComponent("Card Start Date (mm-dd-yyyy):", txtStartDate));
        mainPanel.add(createLabelAndComponent("Card End Date (mm-dd-yyyy):", txtEndDate));
        mainPanel.add(createLabelAndComponent("CVV:", txtCvv));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnSelectMembership);

        this.add(mainPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        btnSelectMembership.addActionListener(this);
    }

    private JPanel createLabelAndComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(200, 20));
        panel.add(label);
        panel.add(component);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSelectMembership) {
            // Retrieve selected membership details
            String selectedMembershipType = (String) cmbMemberships.getSelectedItem();
            MembershipType selectedMembership = findMembershipByType(selectedMembershipType);
            int validity = selectedMembership.getValidity();

            // Rest of the code remains unchanged
            // ...
        }
    }

    // Helper method to find MembershipType by type
    private MembershipType findMembershipByType(String membershipType) {
        for (MembershipType membership : memberships) {
            if (membership.getType().equals(membershipType)) {
                return membership;
            }
        }
        return null; // Handle appropriately if not found
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<MembershipType> memberships = retrieveMembershipsFromDatabase();
            MembershipScreen membershipScreen = new MembershipScreen(memberships);
            membershipScreen.setVisible(true);
        });
    }

    private static List<MembershipType> retrieveMembershipsFromDatabase() {
        // Replace this with your logic to retrieve memberships from the database
        // For simplicity, creating sample memberships
        List<MembershipType> memberships = List.of(
                new MembershipType(1, "Gold", 365, 100, 50.0),
                new MembershipType(2, "Silver", 180, 75, 40.0),
                new MembershipType(3, "Bronze", 90, 50, 30.0)
        );
        return memberships;
    }
}
