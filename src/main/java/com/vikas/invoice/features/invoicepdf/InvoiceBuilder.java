package com.vikas.invoice.features.invoicepdf;

import java.io.IOException;

public interface InvoiceBuilder {

	public byte[] createInvoicePdf() throws IOException;

	public byte[] cancelInvoicePdf() throws IOException;
}
