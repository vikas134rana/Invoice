package com.vikas.invoice.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.entity.enums.InvoiceStatus;
import com.vikas.invoice.features.invoicepdf.InvoiceBuilder;
import com.vikas.invoice.features.invoicepdf.InvoiceBuilderFactory;
import com.vikas.invoice.service.InvoiceService;

@Controller
public class CancelInvoiceController {

	@Autowired
	InvoiceService invoiceService;

	@RequestMapping("/invoiceCancel")
	public String cancelPDFResource(HttpServletResponse response, @RequestParam("id") int id) throws IOException {

		Invoice invoice = invoiceService.getInvoiceById(id);

		InvoiceBuilder invoiceBuilder = InvoiceBuilderFactory.getInvoiceBuilder(invoice);
		byte[] invoicePdfData = invoiceBuilder.cancelInvoicePdf();

		invoice.setStatusCode(InvoiceStatus.CANCELLED);
		invoice.getInvoicePdf().setPdfData(invoicePdfData);
		invoiceService.save(invoice); // Update Invoice with cancel status and new canceled invoice pdf data

		return "redirect:/invoicePrinting?successMsg="+"Invoice "+invoice.getInvoiceNumber()+" cancelled successfully";
		
	}
}
