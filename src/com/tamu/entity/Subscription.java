package com.tamu.entity;

public class Subscription {

	private long subscriptionId;
	private int bookId;
	private int userId;
	private String orderDate;
	private String dueDate;
	private int isReturned;
	public int getIsReturned() {
		return isReturned;
	}
	public void setIsReturned(int isReturned) {
		this.isReturned = isReturned;
	}
	public long getOrderId() {
		return subscriptionId;
	}
	public void setOrderId(long orderId) {
		this.subscriptionId = orderId;
	}	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

}
