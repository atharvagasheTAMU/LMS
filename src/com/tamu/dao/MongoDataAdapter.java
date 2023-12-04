//package com.tamu.dao;
//import com.google.gson.Gson;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Filters;
//import com.mongodb.client.model.ReplaceOptions;
//
//import org.bson.Document;
//import org.bson.types.ObjectId;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.mongodb.client.model.Filters.eq;
//
//public class MongoDataAdapter {
//
//	private MongoClient mongoClient = null;
//	private static String conStr = "mongodb+srv://admin:tamu123@csce606.xubngdw.mongodb.net/?retryWrites=true&w=majority";
//
//	public void connect(String str) {
//		if (mongoClient != null)
//			mongoClient.close();
//		mongoClient = new MongoClient(new MongoClientURI(str));
//	}
//
//	public Order loadOrder(int id) {
//		this.connect(conStr);
//
//		MongoDatabase database = mongoClient.getDatabase("csce606"); // Replace with your database name
//		MongoCollection<Document> ordersCollection = database.getCollection("orders");
//
//		Gson gson = new Gson();
//		Document orderDocument = ordersCollection.find(Filters.eq("orderID", id)).first();
//
//		if (orderDocument != null) {
//			return gson.fromJson(orderDocument.toJson(), Order.class);
//		}
//		this.mongoClient.close();
//		return null;
//
//	}
//
//	public ObjectId saveOrder(Order order) {
//		this.connect(conStr);
//		Gson gson = new Gson();
//		MongoDatabase database = mongoClient.getDatabase("StoreApp"); // Replace with your database name
//		MongoCollection<Document> ordersCollection = database.getCollection("orders");
//		String json = gson.toJson(order);
//		Document orderDocument = Document.parse(json);
//
//		ObjectId id = order.getOrderID() != null ? order.getOrderID() : new ObjectId();
//		order.setOrderID(id); // Set the generated id back to the order object
//		orderDocument.put("_id", id); // Set the _id field for MongoDB document
//
//		ordersCollection.replaceOne(Filters.eq("_id", id), orderDocument, new ReplaceOptions().upsert(true));
//
//		this.mongoClient.close();
//		return id;
//	}
//
//}
