package com.vikas.invoice.controller.asset;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.service.InvoiceService;

@Controller
@RequestMapping("/asset")
public class AssetInvoicePrintingController {

	@Autowired
	InvoiceService invoiceService;

	@GetMapping("/invoicePrinting")
	public String invoicePrinting() {
		return "invoice_printing";
	}
			
	
	/**
	 * Invoice Printing with Custom Search.<br>
	 * Custom Search - Search Combination of invoiceNumber, buyerId, invoiceType,
	 * startDate and endDate all of them will be provided by user.
	 * 
	 * @param invoiceNumber
	 * @param buyerId
	 * @param invoiceType
	 * @param startDate
	 * @param endDate
	 * @param model
	 * @return
	 * @throws Exception
	 */
	// @formatter:off
	@GetMapping("/invoicePrintingSearch")
	public String invoicePrintingSearch(
			@RequestParam("invoiceNumber_search") String invoiceNumber,
			@RequestParam("buyerId_search") Integer buyerId,
			@RequestParam("invoiceType_search") Integer invoiceType,
			@RequestParam("startDate_search") String startDateStr,
			@RequestParam("endDate_search") String endDateStr,
			Model model) throws Exception {
		// @formatter:on

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate startDate = LocalDate.parse(startDateStr, formatter);
		LocalDate endDate = LocalDate.parse(endDateStr, formatter);

		System.out.println("invoiceNumber: " + invoiceNumber);
		System.out.println("buyerId: " + buyerId);
		System.out.println("invoiceType: " + invoiceType);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);

		List<Invoice> invoices = invoiceService.searchInvoice(invoiceNumber, buyerId, invoiceType, startDate, endDate);
		model.addAttribute("invoices", invoices);

		return "invoice_printing";
	}

}
