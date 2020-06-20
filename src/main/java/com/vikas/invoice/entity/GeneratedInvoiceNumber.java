package com.vikas.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GeneratedInvoiceNumber {

	@Column
	@Id
	private int id;

	@Column
	private String sequence;

	@Column
	private int count;

	public GeneratedInvoiceNumber() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "GeneratedInvoiceNumber [id=" + id + ", sequence=" + sequence + ", count=" + count + "]";
	}

}
