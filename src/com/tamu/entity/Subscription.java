package com.tamu.entity;

import java.util.Date;

import org.bson.Document;

public class Subscription {

	private long subscriptionId;
	private Book book;
	private User user;
	private Date orderDate;
	private Date dueDate;
	private int isReturned;
	public int getIsReturned() {
		return isReturned;
	}
	public void setIsReturned(int isReturned) {
		this.isReturned = isReturned;
	}
	public long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(long orderId) {
		this.subscriptionId = orderId;
	}	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


    public Document toDocument() {
        return new Document("subscriptionId", subscriptionId)
                .append("book", book.toDocument())
                .append("user", user.toDocument())
                .append("orderDate", orderDate)
                .append("dueDate", dueDate)
                .append("isReturned", isReturned);
    }

    public static Subscription fromDocument(Document document) {
        Subscription subscription = new Subscription();
        subscription.setSubscriptionId(document.getLong("subscriptionId"));
        subscription.setBook(Book.fromDocument((Document) document.get("book")));
        subscription.setUser(User.fromDocument((Document) document.get("user")));
        subscription.setOrderDate(document.getDate("orderDate"));
        subscription.setDueDate(document.getDate("dueDate"));
        subscription.setIsReturned(document.getInteger("isReturned"));
        return subscription;
    }
}
