import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoDBDataAdapter {
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> ordersCollection;
	private Gson gson;
	
	public MongoDBDataAdapter() {
		String connectionString = "mongodb+srv://Dipam:8PhIxP2Myj2ccM6P@cluster0.ytsb3ae.mongodb.net/?retryWrites=true&w=majority";
	
	  MongoClientSettings settings = MongoClientSettings.builder()
		                .applyConnectionString(new ConnectionString(connectionString))
		                .build();
	  mongoClient = MongoClients.create(settings);
      this.database = mongoClient.getDatabase("SE_Assignment");
      this.ordersCollection = database.getCollection("orders");
      this.gson = new Gson();

  }
	
  public void saveOrder(Order order) {
	  ObjectId objectId = new ObjectId();
      order.setOrderID(objectId.getTimestamp());
	  Document orderDocument = orderToDocument(order);
	  ordersCollection.insertOne(orderDocument);
	  for (OrderLine line : order.getLines()) {
          Document orderLineDocument = new Document("OrderID", order.getOrderID())
                  .append("ProductID", line.getProductID())
                  .append("Quantity", line.getQuantity())
                  .append("Cost", line.getCost());  
          ordersCollection.insertOne(orderLineDocument);
      }
	}
  
  public void saveReceipt(Receipt receipt) {
	  ObjectId objectId = new ObjectId();
	  receipt.setReceiptId(objectId.getTimestamp());
	  Document receiptDocument = receiptToDocument(receipt);
	  ordersCollection.insertOne(receiptDocument);
	}
	
	public Order loadOrder(int orderId) {
	  Document query = new Document("orderID", orderId);
	  Document orderDocument = ordersCollection.find(query).first();
	  if (orderDocument != null) {
			return gson.fromJson(orderDocument.toJson(), Order.class);
		}
	  return null;
	}
	
	private Document orderToDocument(Order order) {
	  Document orderDocument = new Document();
	  orderDocument.append("orderID", order.getOrderID());
	  orderDocument.append("buyerID", order.getBuyerID());
	  orderDocument.append("totalCost", order.getTotalCost());
	  orderDocument.append("totalTax", order.getTotalTax());
	  orderDocument.append("billingAddressId", order.getBillingAddressId());
	  orderDocument.append("shippingAddressId", order.getShippingAddressId());
	  orderDocument.append("cardId", order.getCardId());
	  return orderDocument;
	}
	
	private Document receiptToDocument(Receipt receipt) {
		  Document receiptDocument = new Document();
		  receiptDocument.append("receiptID", receipt.getReceiptId());
		  receiptDocument.append("orderID", receipt.getOrderId());
		  receiptDocument.append("receiptText", receipt.getReceiptText());
		  receiptDocument.append("timestamp",receipt.getTimeStamp());
		  return receiptDocument;
		}
}