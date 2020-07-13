package com.vikas.invoice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vikas.invoice.features.salesregister.SalesRegisterFormat;
import com.vikas.invoice.service.InvoiceService;

@Controller
public class SalesRegisterController {

	@Autowired
	InvoiceService invoiceService;

	@RequestMapping("/salesRegister")
	public String salesRegister() {
		return "sales_register";
	}

	// @formatter:off
		@GetMapping("/salesRegisterGenerate")
		public String salesRegisterSearch(
				@RequestParam("invoiceNumber_search") String invoiceNumber,
				@RequestParam("buyerId_search") Integer buyerId,
				@RequestParam("invoiceType_search") Integer invoiceType,
				@RequestParam("startDate_search") String startDateStr,
				@RequestParam("endDate_search") String endDateStr,
				@RequestParam("format_search") SalesRegisterFormat format,
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

		String ids = invoiceService.searchInvoice(invoiceNumber, buyerId, invoiceType, startDate, endDate).stream().map(i -> String.valueOf(i.getId()))
				.collect(Collectors.joining(","));

		return "redirect:/downloadSalesRegister?" + "format=" + format + "&id=" + ids;
	}

}
