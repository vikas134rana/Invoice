package com.vikas.invoice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.service.BuyerService;
import com.vikas.invoice.service.InvoiceService;
import com.vikas.invoice.service.SellerService;

@Controller
public class InvoicePrintingController {

	@Autowired
	InvoiceService invoiceService;

	@Autowired
	SellerService sellerService;

	@Autowired
	BuyerService buyerService;

	@GetMapping("/invoicePrinting")
	public String invoicePrinting(Model model, @RequestParam(required = false, name = "successMsg") String successMsg) {

		// It means it is a redirect call after Invoice is successfully modified
		if (successMsg != null) {
			model.addAttribute("successMsg", successMsg);
		}

		model.addAttribute("sellers", sellerService.getAllSellers());
		model.addAttribute("buyers", buyerService.getAllBuyers());

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
			@RequestParam("sellerId_search") Integer sellerId,
			@RequestParam("buyerId_search") Integer buyerId,
			@RequestParam("invoiceType_search") Integer invoiceType,
			@RequestParam("startDate_search") String startDateStr,
			@RequestParam("endDate_search") String endDateStr,
			Model model) throws Exception {
		// @formatter:on

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate startDate = LocalDate.parse(startDateStr, formatter);
		LocalDate endDate = LocalDate.parse(endDateStr, formatter);

		System.out.println("=== Search Invoice ===");

		System.out.println("invoiceNumber: " + invoiceNumber);
		System.out.println("sellerId: " + sellerId);
		System.out.println("buyerId: " + buyerId);
		System.out.println("invoiceType: " + invoiceType);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);

		List<Invoice> invoices = invoiceService.searchInvoice(invoiceNumber, sellerId, buyerId, invoiceType, startDate, endDate);

		model.addAttribute("invoices", invoices);
		model.addAttribute("sellers", sellerService.getAllSellers());
		model.addAttribute("buyers", buyerService.getAllBuyers());

		return "invoice_printing";
	}

	// @formatter:off
	@GetMapping("/modifyInvoice")
	public String modifyInvoice(Model model, 
			@RequestParam("id") int id, 
			@RequestParam("vehicleNumber") String vehicleNumber,
			@RequestParam("transporterName") String transporterName, 
			@RequestParam("arDocNumber") String arDocNumber,
			@RequestParam("apDocNumber") String apDocNumber) {
		// @formatter:on

		System.out.println("=== Modify Invoice ===");

		System.out.println("id: " + id);
		System.out.println("vehicleNumber: " + vehicleNumber);
		System.out.println("transporterName: " + transporterName);
		System.out.println("arDocNumber: " + arDocNumber);
		System.out.println("apDocNumber: " + apDocNumber);

		Invoice invoice = invoiceService.getInvoiceById(id);
		invoice.setVehicleNumber(vehicleNumber);
		invoice.setTransporterName(transporterName);
		invoice.setArDocNumber(arDocNumber);
		invoice.setApDocNumber(apDocNumber);

		invoiceService.save(invoice); // modify/update invoice

		model.addAttribute("invoiceModifySuccessMsg", "Invoice " + invoice.getInvoiceNumber() + " modified successfully.");
		model.addAttribute("sellers", sellerService.getAllSellers());
		model.addAttribute("buyers", buyerService.getAllBuyers());

		return "invoice_printing";
	}

}
