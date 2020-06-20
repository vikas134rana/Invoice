package com.vikas.invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.repository.InvoiceRepository;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	public void save(Invoice invoice) {
		invoiceRepository.save(invoice);
	}

	public void deleteById(int id) {
		invoiceRepository.deleteById(id);
	}

	public Invoice getInvoiceById(int id) {
		Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);
		return invoiceOpt.isPresent() ? invoiceOpt.get() : null;
	}

	public List<Invoice> getAllInvoices() {
		return (List<Invoice>) invoiceRepository.findAll();
	}

}
