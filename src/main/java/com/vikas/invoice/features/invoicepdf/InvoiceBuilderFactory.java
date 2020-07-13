package com.vikas.invoice.features.invoicepdf;

import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.features.invoicepdf.pdf.AssetTransferInvoicePDF;
import com.vikas.invoice.features.invoicepdf.pdf.CrossInvoicePdf;
import com.vikas.invoice.features.invoicepdf.pdf.EWastePdf;
import com.vikas.invoice.features.invoicepdf.pdf.ExportInvoicePdf;
import com.vikas.invoice.features.invoicepdf.pdf.GeneralWastePdf;
import com.vikas.invoice.features.invoicepdf.pdf.InwardDeliveryChallanInvoicePdf;
import com.vikas.invoice.features.invoicepdf.pdf.OtherInvoicePDF;
import com.vikas.invoice.features.invoicepdf.pdf.OutwardDeliveryChallanInvoicePdf;

public class InvoiceBuilderFactory {

	public static InvoiceBuilder getInvoiceBuilder(Invoice invoice) {

		InvoiceBuilder invoiceBuilder = null;
		int invoiceType = invoice.getType();

		switch (invoiceType) {
		case 1:
			invoiceBuilder = new AssetTransferInvoicePDF(invoice);
			break;
		case 2:
			invoiceBuilder = new OtherInvoicePDF(invoice);
			break;
		case 3:
			invoiceBuilder = new CrossInvoicePdf(invoice);
			break;
		case 4:
			invoiceBuilder = new ExportInvoicePdf(invoice);
			break;
		case 5:
			invoiceBuilder = new InwardDeliveryChallanInvoicePdf(invoice);
			break;
		case 6:
			invoiceBuilder = new OutwardDeliveryChallanInvoicePdf(invoice);
			break;
		case 7:
			invoiceBuilder = new GeneralWastePdf(invoice);
			break;
		case 8:
			invoiceBuilder = new EWastePdf(invoice);
			break;	
		default:
			throw new IllegalArgumentException("Invoice Type value " + invoiceType + " is invalid");
		}
		return invoiceBuilder;
	}

}
