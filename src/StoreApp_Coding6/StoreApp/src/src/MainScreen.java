import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {

    private JButton btnBuy = new JButton("Order View");
    private JButton btnSell = new JButton("Product View");
    private int userId;
    private String userName;
    private String fullName;

    public MainScreen(User user) {
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);

        btnSell.setPreferredSize(new Dimension(120, 50));
        btnBuy.setPreferredSize(new Dimension(120, 50));


        JLabel title = new JLabel("Store Management System");
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        JPanel panelTitle = new JPanel();
        panelTitle.add(title);
        this.getContentPane().add(panelTitle);
        
        userId = user.getUserID();
        userName = user.getUsername();
        fullName = user.getFullName();

        JPanel userIdPanelMain = new JPanel();
        JLabel userIdPanel = new JLabel("UserId: " + userId);
        userIdPanel.setFont(new Font("Sans Serif", Font.BOLD, 18));
        userIdPanelMain.add(userIdPanel);
        this.getContentPane().add(userIdPanelMain);    
        
        JPanel userNamePanelMain = new JPanel();
        JLabel userNamePanel = new JLabel("User Name: " + userName);
        userNamePanel.setFont(new Font("Sans Serif", Font.BOLD, 18));
        userNamePanelMain.add(userNamePanel);
        this.getContentPane().add(userNamePanelMain);
        
        JPanel fullNamePanelMain = new JPanel();
        JLabel fullNamePanel = new JLabel("Full Name: " + fullName);
        fullNamePanel.setFont(new Font("Sans Serif", Font.BOLD, 18));
        fullNamePanelMain.add(fullNamePanel);
        this.getContentPane().add(fullNamePanelMain);

        JPanel panelButton = new JPanel();
        panelButton.add(btnBuy);
        panelButton.add(btnSell);

        this.getContentPane().add(panelButton);

        btnBuy.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getOrderViewController().setVisible(true);            }
        });


        btnSell.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getProductViewController().setVisible(true);
            }
        });
    }


}
