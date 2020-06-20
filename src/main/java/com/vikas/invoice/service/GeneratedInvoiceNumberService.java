package com.vikas.invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.invoice.entity.GeneratedInvoiceNumber;
import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.entity.Seller;
import com.vikas.invoice.repository.GeneratedInvoiceNumberRepository;
import com.vikas.invoice.util.InvoiceUtils;

@Service
public class GeneratedInvoiceNumberService {

	@Autowired
	private GeneratedInvoiceNumberRepository generatedInvoiceNumberRepository;

	public void save(GeneratedInvoiceNumber generatedInvoiceNumber) {
		generatedInvoiceNumberRepository.save(generatedInvoiceNumber);
	}

	public void deleteById(int id) {
		generatedInvoiceNumberRepository.deleteById(id);
	}

	public GeneratedInvoiceNumber getGeneratedInvoiceNumberById(int id) {
		Optional<GeneratedInvoiceNumber> generatedInvoiceNumberOpt = generatedInvoiceNumberRepository.findById(id);
		return generatedInvoiceNumberOpt.isPresent() ? generatedInvoiceNumberOpt.get() : null;
	}

	public List<GeneratedInvoiceNumber> getAllGeneratedInvoiceNumbers() {
		return (List<GeneratedInvoiceNumber>) generatedInvoiceNumberRepository.findAll();
	}

	public GeneratedInvoiceNumber getGeneratedInvoiceNumberBySequence(String sequence) {
		return generatedInvoiceNumberRepository.findBySequence(sequence);
	}

	public String generateInvoiceNumber(Invoice invoice) {
		Seller seller = invoice.getSeller();
		String sellerStateNumber = seller.getAddress().getState().getStateNumber();

		String sequence = sellerStateNumber + InvoiceUtils.invoiceTypeChar(invoice.getType());
		String invoiceNumber = sequence + invoiceNumberCounting(sequence);
		return invoiceNumber;
	}

	private String invoiceNumberCounting(String sequence) {
		GeneratedInvoiceNumber generatedInvoiceNumber = getGeneratedInvoiceNumberBySequence(sequence);

		if (generatedInvoiceNumber == null) {
			// add new GeneratedInvoiceNumber in table
			generatedInvoiceNumber = new GeneratedInvoiceNumber();
			generatedInvoiceNumber.setSequence(sequence);
			generatedInvoiceNumber.setCount(0);
		}

		generatedInvoiceNumber.setCount(generatedInvoiceNumber.getCount() + 1);

		save(generatedInvoiceNumber);

		String counting = "" + generatedInvoiceNumber.getCount();

		// add extra digits to make counting length equal to 7
		int loops = 7 - counting.length();
		for (int i = 0; i < loops; i++) {
			counting = "0" + counting;
		}

		return counting;
	}

}
