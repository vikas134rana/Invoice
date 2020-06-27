package com.vikas.invoice.controller.asset;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.entity.InvoicePdf;
import com.vikas.invoice.service.InvoiceService;

@Controller
@RequestMapping("/asset")
public class AssetDownloadInvoiceController {

	@Autowired
	InvoiceService invoiceService;

	@RequestMapping("/invoiceDownload")
	public void downloadPDFResource(HttpServletResponse response, @RequestParam("id") int id) throws IOException {
		// If user is not authorized - he should be thrown out from here itself

		// Authorized user will download the file
		Invoice invoice = invoiceService.getInvoiceById(id);
		InvoicePdf invoicePdf = invoice.getInvoicePdf();
		InputStream invoicePdfInputStream = new ByteArrayInputStream(invoicePdf.getPdfData());

		String fileName = "Invoice_" + invoice.getInvoiceNumber() + "_" + System.currentTimeMillis() + ".pdf";

		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		IOUtils.copy(invoicePdfInputStream, response.getOutputStream());
		response.getOutputStream().flush();
	}
}
