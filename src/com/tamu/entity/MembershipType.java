package com.tamu.entity;

public class MembershipType {
	private double membershipId;
	private String type;
	private int validity;
	private int numBooks;
	private double price;
	public double getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(double membershipId) {
		this.membershipId = membershipId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getValidity() {
		return validity;
	}
	public void setValidity(int validity) {
		this.validity = validity;
	}
	public int getNumBooks() {
		return numBooks;
	}
	public void setNumBooks(int numBooks) {
		this.numBooks = numBooks;
	}
	
}
