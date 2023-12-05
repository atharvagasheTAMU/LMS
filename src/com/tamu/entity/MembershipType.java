package com.tamu.entity;

import org.bson.Document;

public class MembershipType {
	private int membershipTypeId;
	private String type;
	private int validity;
	private int numBooks;
	private double price;
	public int getMembershipId() {
		return membershipTypeId;
	}
	public void setMembershipId(int membershipId) {
		this.membershipTypeId = membershipId;
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
    public Document toDocument() {
        return new Document("membershipTypeId", membershipTypeId)
                .append("type", type)
                .append("numBooks", numBooks)
                .append("validity", validity)
                .append("price", price);
    }

    public static MembershipType fromDocument(Document document) {
        MembershipType membershipType = new MembershipType();
        membershipType.setMembershipId(document.getInteger("membershipTypeId"));
        membershipType.setType(document.getString("type"));
        membershipType.setNumBooks(document.getInteger("numBooks"));
        membershipType.setValidity(document.getInteger("subscriptionValidity"));
        membershipType.setPrice(document.getDouble("price"));
        return membershipType;
    }
}
