package com.vikas.invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.invoice.entity.InvoiceItem;
import com.vikas.invoice.repository.InvoiceItemRepository;

@Service
public class InvoiceItemService {

	@Autowired
	private InvoiceItemRepository invoiceItemRepository;

	public void save(InvoiceItem invoiceItem) {
		invoiceItemRepository.save(invoiceItem);
	}

	public void deleteById(int id) {
		invoiceItemRepository.deleteById(id);
	}

	public InvoiceItem getInvoiceItemById(int id) {
		Optional<InvoiceItem> invoiceItemOpt = invoiceItemRepository.findById(id);
		return invoiceItemOpt.isPresent() ? invoiceItemOpt.get() : null;
	}

	public List<InvoiceItem> getAllInvoiceItems() {
		return (List<InvoiceItem>) invoiceItemRepository.findAll();
	}

}
