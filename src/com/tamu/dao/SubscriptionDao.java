package com.tamu.dao;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Sorts;
import com.tamu.dto.MyBookDto;
import com.tamu.entity.Subscription;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.mongodb.client.model.Filters.eq;

public class SubscriptionDao {

	private MongoClient mongoClient = null;
	private static String conStr;

	public void connect(String str) {

		Properties properties = Config.loadProperties();

		String mongoPassword = properties.getProperty("mongo.password");
		conStr = "mongodb+srv://admin:" + mongoPassword + "@csce606.xubngdw.mongodb.net/?retryWrites=true&w=majority";
		System.out.println(conStr);

		if (mongoClient != null)
			mongoClient.close();
		mongoClient = new MongoClient(new MongoClientURI(str));
	}

	public void insertSubscription(Subscription subscription) {
		this.connect(conStr);
		MongoDatabase database = mongoClient.getDatabase("csce606"); // Replace with your database name
		MongoCollection<Document> subscriptionsCollection = database.getCollection("subscriptions");

		subscriptionsCollection.insertOne(subscription.toDocument());
	}

	public List<MyBookDto> getSubscription(String username) {
		this.connect(conStr);

		MongoDatabase database = mongoClient.getDatabase("csce606"); // Replace with your database name
		MongoCollection<Document> subscriptionsCollection = database.getCollection("subscriptions");

		FindIterable<Document> result = subscriptionsCollection.find(
				Filters.and(
						Filters.eq("user.username", username),
						Filters.eq("isReturned", 0)))
				.sort(Sorts.descending("orderDate"));

		List<MyBookDto> subscriptions = new ArrayList<>();
		for (Document doc : result) {
			subscriptions.add(mapDocumentToMyBookDto(doc));
		}

		return subscriptions;

	}

	private MyBookDto mapDocumentToMyBookDto(Document document) {
		MyBookDto myBookDto = new MyBookDto();
		myBookDto.setBookId(document.getInteger("bookId"));
		myBookDto.setBookName(document.getString("bookName"));
		myBookDto.setBookDescription(document.getString("bookDescription"));
		myBookDto.setAuthorName(document.getString("authorName"));
		myBookDto.setBookPrice(document.getDouble("bookPrice"));
		myBookDto.setQuantity(document.getInteger("quantity"));
		myBookDto.setGenre(document.getString("genre"));
		myBookDto.setImageURL(document.getString("imageURL"));
		myBookDto.setOrderDate(document.getString("orderDate"));
		myBookDto.setDueDate(document.getString("dueDate"));
		return myBookDto;
	}

	public int getSubscriptionsByUserId(int userId) {
		this.connect(conStr);
		MongoDatabase database = mongoClient.getDatabase("csce606"); // Replace with your database name
		MongoCollection<Document> subscriptionsCollection = database.getCollection("subscriptions");

		return (int) subscriptionsCollection.countDocuments(
				Filters.and(
						Filters.eq("user.userId", userId),
						Filters.eq("isReturned", 0)));

	}
}
