package com.vikas.invoice.util;

import com.vikas.invoice.entity.Buyer;
import com.vikas.invoice.entity.Seller;

public class InvoiceUtils {

	public static char invoiceTypeChar(int invoiceType) {
		char type;

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
			type = 'X';
			break;
		case 5:
			type = 'N';
			break;
		case 6:
			type = 'U';
			break;
		case 7:
			type = 'G';
			break;
		case 8:
			type = 'E';
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
		case 7:
			type = "General Waste";
			break;
		case 8:
			type = "E-Waste";
			break;
		default:
			throw new IllegalArgumentException("Invoice Type value " + invoiceType + " is invalid");
		}

		return type;
	}

	public static boolean isIGST(Seller seller, Buyer buyer) {
		return seller.getAddress().getState().getId() != buyer.getAddress().getState().getId();
	}

}
