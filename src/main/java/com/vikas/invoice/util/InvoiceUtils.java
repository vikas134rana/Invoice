package com.vikas.invoice.util;

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
}
