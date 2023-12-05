import java.util.Date;

public class Receipt {
    private int receiptId;
    private int orderId;
    private String receiptText;
    private String timeStamp;

    // Constructors
    public Receipt() {
        // Default constructor
    }

    public Receipt(int receiptId, int orderId, String receiptText, String timeStamp) {
        this.receiptId = receiptId;
        this.orderId = orderId;
        this.receiptText = receiptText;
        this.timeStamp = timeStamp;
    }

    // Getters and setters
    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getReceiptText() {
        return receiptText;
    }

    public void setReceiptText(String receiptText) {
        this.receiptText = receiptText;
    }
    
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
