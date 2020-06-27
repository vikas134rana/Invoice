package com.vikas.invoice.controller.asset;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.features.salesregister.SalesRegister;
import com.vikas.invoice.features.salesregister.SalesRegisterFactory;
import com.vikas.invoice.features.salesregister.SalesRegisterFormat;
import com.vikas.invoice.service.InvoiceService;

@Controller
@RequestMapping("/asset")
public class AssetDownloadSalesRegisterController {

	@Autowired
	InvoiceService invoiceService;

	@RequestMapping("/downloadSalesRegister")
	public void downloadSalesRegister(HttpServletResponse response, @RequestParam("id") List<Integer> ids, @RequestParam("format") SalesRegisterFormat format)
			throws IOException {
		
		// If user is not authorized - he should be thrown out from here itself

		// Authorized user will download the file
		List<Invoice> invoices = invoiceService.getInvoicesById(ids);
		SalesRegister salesRegister = SalesRegisterFactory.getSalesRegister(invoices, format);
		byte[] salesRegisterData = salesRegister.generate();
		ByteArrayInputStream salesRegisterInputStream = new ByteArrayInputStream(salesRegisterData);

		String fileName = "SalesRegister_" + System.currentTimeMillis(); 
		
		if(format==SalesRegisterFormat.EXCEL) {
			response.setContentType("application/vnd.ms-excel");
			fileName = fileName+".xlsx";
		}
		else if(format==SalesRegisterFormat.PDF) {
			response.setContentType("application/pdf");
			fileName = fileName+".pdf";
		}
		
		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

		IOUtils.copy(salesRegisterInputStream, response.getOutputStream());
		response.getOutputStream().flush();
	}

}
