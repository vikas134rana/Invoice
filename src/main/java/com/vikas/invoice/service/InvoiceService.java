package com.vikas.invoice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.repository.InvoiceRepository;
import com.vikas.invoice.util.Util;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	public Invoice save(Invoice invoice) {
		return invoiceRepository.save(invoice);
	}

	public void deleteById(int id) {
		invoiceRepository.deleteById(id);
	}

	public Invoice getInvoiceById(int id) {
		Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);
		return invoiceOpt.isPresent() ? invoiceOpt.get() : null;
	}

	public List<Invoice> getInvoicesById(List<Integer> ids) {
		List<Invoice> invoices = (List<Invoice>) invoiceRepository.findAllById(ids);
		return invoices;
	}

	public List<Invoice> getAllInvoices() {
		return (List<Invoice>) invoiceRepository.findAll();
	}

	public List<Invoice> searchInvoice(String invoiceNumber, Integer buyerId, Integer invoiceType, LocalDate startDate, LocalDate endDate) throws Exception {

		if (startDate == null || endDate == null)
			throw new Exception("Start Date and End Date is mandatory.");

		List<Invoice> invoices = getAllInvoices().stream().filter(i -> {

			LocalDate invoiceCreationDate = i.getCreationDate().toLocalDate();

			// ignore invoice where creation date is not in start and end date range
			if (!Util.isDateInRange(invoiceCreationDate, startDate, endDate))
				return false;

			// ignore invoice where invoice type doesn't match
			if (invoiceType != null && invoiceType != 0 && invoiceType != i.getType())
				return false;

			// ignore invoice where buyerId doesn't match
			if (buyerId != null && i.getBuyer().getId() != buyerId)
				return false;

			// ignore invoice where invoiceNumber doesn't match
			if (invoiceNumber != null && !invoiceNumber.trim().isEmpty() && !i.getInvoiceNumber().equals(invoiceNumber))
				return false;

			return true;
		}).collect(Collectors.toList());

		return invoices;
	}

}
