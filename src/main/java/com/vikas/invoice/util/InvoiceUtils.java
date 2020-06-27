package com.vikas.invoice.util;

import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.features.invoicepdf.InvoiceBuilder;
import com.vikas.invoice.features.invoicepdf.pdf.AssetTransferInvoicePDF;
import com.vikas.invoice.features.invoicepdf.pdf.CrossInvoicePdf;
import com.vikas.invoice.features.invoicepdf.pdf.ExportInvoicePdf;
import com.vikas.invoice.features.invoicepdf.pdf.InwardDeliveryChallanInvoicePdf;
import com.vikas.invoice.features.invoicepdf.pdf.OtherInvoicePDF;
import com.vikas.invoice.features.invoicepdf.pdf.OutwardDeliveryChallanInvoicePdf;

public class InvoiceUtils {

	public static char invoiceTypeChar(int invoiceType) {
		char type = 'A';

		switch (invoiceType) {
		case 1:
			type = 'A';
			break;
		case 2:
			type = 'O';
			break;
		case 3:
			type = 'C';
			break;
		case 4:
			type = 'E';
			break;
		case 5:
			type = 'N';
			break;
		case 6:
			type = 'U';
			break;
		default:
			throw new IllegalArgumentException("Invoice Type value " + invoiceType + " is invalid");
		}

		return type;
	}
	
	public static String invoiceTypeValue(int invoiceType) {
		String type;

		switch (invoiceType) {
		case 1:
			type = "Asset Transfer";
			break;
		case 2:
			type = "Other";
			break;
		case 3:
			type = "Cross";
			break;
		case 4:
			type = "Export";
			break;
		case 5:
			type = "Inward Delivery Challan";
			break;
		case 6:
			type = "Outward Delivery Challan";
			break;
		default:
			throw new IllegalArgumentException("Invoice Type value " + invoiceType + " is invalid");
		}

		return type;
	}
	
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

		default:
			throw new IllegalArgumentException("Invoice Type value " + invoiceType + " is invalid");
		}
		return invoiceBuilder;
	}
}
