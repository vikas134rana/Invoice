package com.vikas.invoice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.vikas.invoice.entity.enums.InvoiceStatus;

@Entity
public class Invoice {

	@Column
	@Id
	private int id;

	@Column(nullable = false, unique = true)
	private String invoiceNumber;

	@Column(nullable = false)
	private Date creationDate;
	
	@Column
	private int type;
	
	@ManyToOne
	@JoinColumn(name = "seller_id", referencedColumnName = "id")
	private Seller seller;
	
	@ManyToOne
	@JoinColumn(name = "buyer_id", referencedColumnName = "id")
	private Buyer buyer;
	
	@Column
	private int statusCode;
	
	@Column
	private int totalQuantity;
	
	@Column
	private double totalPrice;

	public Invoice() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(InvoiceStatus invoiceStatus) {
		this.statusCode = invoiceStatus.getStatusCode();
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", invoiceNumber=" + invoiceNumber + ", creationDate=" + creationDate + ", type=" + type + ", seller=" + seller
				+ ", buyer=" + buyer + ", statusCode=" + statusCode + ", totalQuantity=" + totalQuantity + ", totalPrice=" + totalPrice + "]";
	}

}
