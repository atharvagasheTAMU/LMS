package com.tamu.entity;
import java.util.Date;

public class Card {
	private int cardID;
    public int getCardID() {
		return cardID;
	}
	public void setCardID(int cardID) {
		this.cardID = cardID;
	}
	private String cardNumber;
    private Date startDate;
    public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Card(String cardNumber, Date startDate, Date endDate, String cardName, String cvv) {
		super();
		this.cardNumber = cardNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.cardName = cardName;
		this.cvv = cvv;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	private Date endDate;
    private String cardName;
    private String cvv;
}
