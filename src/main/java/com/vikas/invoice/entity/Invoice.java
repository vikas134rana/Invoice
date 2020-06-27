package com.vikas.invoice.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.vikas.invoice.entity.enums.InvoiceStatus;

@Entity
public class Invoice {

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, unique = true)
	private String invoiceNumber;

	@Column(nullable = false)
	private LocalDateTime creationDate;

	@Column
	private int type;

	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Seller seller;

	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;

	@Column
	private int statusCode;

	@Column
	private int totalQuantity;

	@Column
	private double totalPrice;

	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
	private List<InvoiceItem> invoiceItems;

	@OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL)
	private InvoicePdf invoicePdf;

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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
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

	public InvoiceStatus getStatusCode() {
		return InvoiceStatus.getInvoioceStatus(statusCode);
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

	public List<InvoiceItem> getInvoiceItems() {
		return invoiceItems;
	}

	public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

	public InvoicePdf getInvoicePdf() {
		return invoicePdf;
	}

	public void setInvoicePdf(InvoicePdf invoicePdf) {
		this.invoicePdf = invoicePdf;
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", invoiceNumber=" + invoiceNumber + ", creationDate=" + creationDate + ", type=" + type + ", seller=" + seller
				+ ", buyer=" + buyer + ", statusCode=" + statusCode + ", totalQuantity=" + totalQuantity + ", totalPrice=" + totalPrice + "]";
	}

}
