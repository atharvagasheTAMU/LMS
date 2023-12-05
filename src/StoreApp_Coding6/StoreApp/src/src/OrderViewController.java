import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderViewController extends JFrame implements ActionListener {
    private Order order = null;
    private JButton btnAdd = new JButton("Add a new item");
    private JButton btnPay = new JButton("Finish and pay");

    private DefaultTableModel items = new DefaultTableModel(); // store information for the table!

    private JTable tblItems = new JTable(items);
    private JLabel labTotal = new JLabel("Total: ");
    
    //Shipping Address
    private JLabel labelShippingAddress = new JLabel("Shipping Address");    
    private JLabel labeStreet = new JLabel("Street:");
    private JLabel labelCity = new JLabel("City:");
    private JLabel labelState = new JLabel("State: ");
    private JLabel labelZip = new JLabel("ZIP: ");
    
    private JTextField street = new JTextField(20);
    private JTextField city = new JTextField(13);
    private JTextField state = new JTextField(15);
    private JTextField zip = new JTextField(10);
    
    //Credit Card
    private JLabel labelCreditCard = new JLabel("Credit Card Details");
    private JLabel labelCardNumber = new JLabel("Card Number: ");
    private JLabel labelExpirationDate = new JLabel("Expiry: ");
    private JLabel labelCardHolderName = new JLabel("Card Holder Name: ");
    private JLabel labelCvv = new JLabel("CVV: ");
    
    private JTextField cardNumber = new JTextField(20);
    private JTextField expiryDate = new JTextField(10);
    private JTextField cardHolderName = new JTextField(30);
    private JTextField cvv = new JTextField(10);
    
    //Billing Address
    private JLabel labelBillingAddress = new JLabel("Billing Address");    
    private JLabel labelStreetBilling = new JLabel("Street:");
    private JLabel labelCityBilling = new JLabel("City:");
    private JLabel labelStateBilling = new JLabel("State: ");
    private JLabel labelZipBilling = new JLabel("ZIP: ");
    
    private JTextField streetBilling = new JTextField(20);
    private JTextField cityBilling = new JTextField(13);
    private JTextField stateBilling = new JTextField(15);
    private JTextField zipBilling = new JTextField(10);

    public OrderViewController() {
        btnAdd.addActionListener(this);
        btnPay.addActionListener(this);
        order = new Order();
        this.setTitle("Order View");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(500, 700);


        items.addColumn("Product ID");
        items.addColumn("Name");
        items.addColumn("Price");
        items.addColumn("Quantity");
        items.addColumn("Cost");

        JPanel panelOrder = new JPanel();
        panelOrder.setPreferredSize(new Dimension(400, 450));
        panelOrder.setLayout(new BoxLayout(panelOrder, BoxLayout.PAGE_AXIS));
        tblItems.setBounds(0, 0, 400, 350);
        panelOrder.add(tblItems.getTableHeader());
        panelOrder.add(tblItems);
        panelOrder.add(labTotal);
        tblItems.setFillsViewportHeight(true);
        this.getContentPane().add(panelOrder);
        
        //Shipping Address
        JPanel panelShippingAddress = new JPanel();
        panelShippingAddress.add(labelShippingAddress);
        this.getContentPane().add(panelShippingAddress);
        
        JPanel panelAddress = new JPanel();
        panelAddress.add(labeStreet);
        panelAddress.add(street);
        this.getContentPane().add(panelAddress);
        
        JPanel panelCityState = new JPanel();
        panelCityState.add(labelCity);
        panelCityState.add(city);
        
        panelCityState.add(labelState);
        panelCityState.add(state);
        this.getContentPane().add(panelCityState);
        
        JPanel panelZip = new JPanel();
        panelZip.add(labelZip);
        panelZip.add(zip);
        this.getContentPane().add(panelZip);
        
        //Credit Card
        JPanel panelCreditCard = new JPanel();
        panelCreditCard.add(labelCreditCard);
        this.getContentPane().add(panelCreditCard);
        
        JPanel panelCardHolderName = new JPanel();
        panelCardHolderName.add(labelCardHolderName);
        panelCardHolderName.add(cardHolderName);
        this.getContentPane().add(panelCardHolderName);

        JPanel panelCardNumber = new JPanel();
        panelCardNumber.add(labelCardNumber);
        panelCardNumber.add(cardNumber);
        this.getContentPane().add(panelCardNumber);
        
        JPanel panelExpCvv = new JPanel();
        panelExpCvv.add(labelExpirationDate);
        panelExpCvv.add(expiryDate);
        
        panelExpCvv.add(labelCvv);
        panelExpCvv.add(cvv);
        this.getContentPane().add(panelExpCvv);
        
      //Billing Address
        JPanel panelBillingAddress = new JPanel();
        panelBillingAddress.add(labelBillingAddress);
        this.getContentPane().add(panelBillingAddress);
        
        JPanel panelAddressBilling = new JPanel();
        panelAddressBilling.add(labelStreetBilling);
        panelAddressBilling.add(streetBilling);
        this.getContentPane().add(panelAddressBilling);
        
        JPanel panelCityStateBilling = new JPanel();
        panelCityStateBilling.add(labelCityBilling);
        panelCityStateBilling.add(cityBilling);
        
        panelCityStateBilling.add(labelStateBilling);
        panelCityStateBilling.add(stateBilling);
        this.getContentPane().add(panelCityStateBilling);
        
        JPanel panelZipBilling = new JPanel();
        panelZipBilling.add(labelZipBilling);
        panelZipBilling.add(zipBilling);
        this.getContentPane().add(panelZipBilling);

        JPanel panelButton = new JPanel();
        panelButton.setPreferredSize(new Dimension(400, 100));
        panelButton.add(btnAdd);
        panelButton.add(btnPay);
        this.getContentPane().add(panelButton);
    }
    
    public void addRow(Object[] row) {
        items.addRow(row);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd)
            addProduct();
        else
        if (e.getSource() == btnPay)
            makeOrder();
    }

    private void makeOrder() {
		String productDetails = "";
		String receiptText = "";
		String creditCardDetails = cardNumber.getText();
		creditCardDetails = creditCardDetails.substring(creditCardDetails.length() - 4);
    	Address shippingAddress = new Address(0,street.getText(), city.getText(), state.getText(), zip.getText());
    	Address billingAddress = new Address(0,streetBilling.getText(), cityBilling.getText(), stateBilling.getText(), zipBilling.getText());
		int shippingAddressId = Application.getInstance().getRedisDataAdapter().saveAddress(shippingAddress);
		if (shippingAddress != null) {
			order.setShippingAddressId(shippingAddressId);
		}
		int blillingAddressId = Application.getInstance().getRedisDataAdapter().saveAddress(billingAddress);
		if (billingAddress != null) {
			order.setBillingAddressId(blillingAddressId);
		}
		CreditCard creditCard = new CreditCard(0, cardNumber.getText(),cardHolderName.getText(),expiryDate.getText(), cvv.getText(), blillingAddressId);
		int cardId = Application.getInstance().getRedisDataAdapter().saveCreditCard(creditCard);
		if (creditCard != null) {
			order.setCardId(cardId);
		}
		for (OrderLine line : this.order.getLines()) {
			int productId = line.getProductID();
			Product product = Application.getInstance().getRedisDataAdapter().loadProduct(productId);
			product.setQuantity(product.getQuantity()-line.getQuantity());
			Application.getInstance().getRedisDataAdapter().saveProduct(product);
			productDetails = productDetails + "Product ID:  " + productId + "; Product Name: " + product.getName()
			 + "; Quantity: " + line.getQuantity() + "; Price Per Item: $" + product.getPrice() + "; Total Price: $" + line.getQuantity()*product.getPrice() + "\n"; 
		}
		Application.getInstance().getMongoDBDataAdapter().saveOrder(order);
		User user = Application.getInstance().getCurrentUser();
		receiptText = "Order Details \n CustomerID: " + user.getUserID()
		+ "\nCustomer Name: " + user.getFullName()
		+ "\nOrderId: " + order.getOrderID()
		+ "\nOrder Datetime: " + new java.util.Date().toString()
		+ "\nOrder Cost: $" + order.getTotalCost()
		+ "\nProduct Details \n" + productDetails
		+ "\nShipping Address: " + shippingAddress.getStreet() + " " + shippingAddress.getCity() + " " + shippingAddress.getState() + " " + shippingAddress.getZip()
		+ "\nCard Number: **** **** **** " + creditCardDetails;
		Receipt receipt = new Receipt(0, order.getOrderID(), receiptText, new java.util.Date().toString());
		Application.getInstance().getMongoDBDataAdapter().saveReceipt(receipt);
		JOptionPane.showMessageDialog(null, "Order Saved Successfully! \n" + receiptText);
    }

    private void addProduct() {
        String id = JOptionPane.showInputDialog("Enter ProductID: ");
        Product product = Application.getInstance().getRedisDataAdapter().loadProduct(Integer.parseInt(id));
        if (product == null) {
            JOptionPane.showMessageDialog(null, "This product does not exist!");
            return;
        }

        double quantity = Double.parseDouble(JOptionPane.showInputDialog(null,"Enter quantity: "));

        if (quantity <= 0 || quantity > product.getQuantity()) {
            JOptionPane.showMessageDialog(null, "This quantity is not valid!");
            return;
        }

        OrderLine line = new OrderLine();
        line.setProductID(product.getProductID());
        line.setQuantity(quantity);
        line.setCost(quantity * product.getPrice());
        order.getLines().add(line);
        order.setTotalCost(order.getTotalCost() + line.getCost());

        Object[] row = new Object[5];
        row[0] = line.getProductID();
        row[1] = product.getName();
        row[2] = product.getPrice();
        row[3] = line.getQuantity();
        row[4] = line.getCost();

        this.addRow(row);
        this.labTotal.setText("Total: $" + order.getTotalCost());
        this.invalidate();
    }

}