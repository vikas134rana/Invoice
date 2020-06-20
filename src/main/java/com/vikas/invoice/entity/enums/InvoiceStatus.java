package com.vikas.invoice.entity.enums;

public enum InvoiceStatus {

	ACTIVE(100), CANCEL(200);
	
	private int statusCode;
	
	InvoiceStatus(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public InvoiceStatus getInvoioceStatus(int statusCode) {
		if(statusCode == 100)
			return ACTIVE;
		if(statusCode == 200)
			return CANCEL;
		return null;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
