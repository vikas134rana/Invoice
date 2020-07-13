package com.vikas.invoice.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vikas.invoice.entity.Buyer;
import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.entity.InvoiceItem;
import com.vikas.invoice.entity.InvoicePdf;
import com.vikas.invoice.entity.Item;
import com.vikas.invoice.entity.Seller;
import com.vikas.invoice.entity.enums.InvoiceStatus;
import com.vikas.invoice.features.invoicepdf.InvoiceBuilder;
import com.vikas.invoice.features.invoicepdf.InvoiceBuilderFactory;
import com.vikas.invoice.service.BuyerService;
import com.vikas.invoice.service.InvoiceItemService;
import com.vikas.invoice.service.InvoicePdfService;
import com.vikas.invoice.service.InvoiceSeriesService;
import com.vikas.invoice.service.InvoiceService;
import com.vikas.invoice.service.ItemService;
import com.vikas.invoice.service.SellerService;

@Controller
public class InvoiceEntryController {

	@Autowired
	ItemService itemService;

	@Autowired
	SellerService sellerService;

	@Autowired
	BuyerService buyerService;

	@Autowired
	InvoiceService invoiceService;

	@Autowired
	InvoiceItemService invoiceItemService;

	@Autowired
	InvoiceSeriesService generatedInvoiceNumberService;

	@Autowired
	InvoicePdfService invoicePdfService;

	@GetMapping("/invoiceEntry")
	public String invoiceEntry(Model model, @RequestParam(required = false, name = "successMsg") String successMsg) {

		List<Seller> sellers = sellerService.getAllSellers();
		List<Buyer> buyers = buyerService.getAllBuyers();
		List<Item> items = itemService.getAllItems();

		model.addAttribute("sellers", sellers);
		model.addAttribute("buyers", buyers);
		model.addAttribute("items", items);

		// It means it is a redirect call after Invoice is successfully created
		if (successMsg != null) {
			model.addAttribute("successMsg", successMsg);
		}

		return "invoice_entry";
	}

	// @formatter:off
	@GetMapping("/saveInvoiceEntry")
	public String saveForm(
			@RequestParam("serviceType") int serviceType, 
			@RequestParam("buyerId") int buyerId, 
			@RequestParam("sellerId") int sellerId,
			@RequestParam("itemId") Integer[] itemIds, 
			@RequestParam("quantity") Integer[] quantities,
			Model model) throws IOException {
		// @formatter:on

		Invoice invoice = new Invoice();

		Buyer buyer = buyerService.getBuyerById(buyerId);
		Seller seller = sellerService.getSellerById(sellerId);

		// updating itemId and its quantity in the map
		Map<Integer, Integer> itemQuantityMap = new HashMap<>();
		for (int i = 0; i < itemIds.length; i++) {
			if (quantities[i] != null && quantities[i] > 0)
				itemQuantityMap.put(itemIds[i], quantities[i]);
		}

		// create invoiceItem Object (with the help of itemQuantityMap)
		List<InvoiceItem> invoiceItems = new ArrayList<>();
		for (Entry<Integer, Integer> entry : itemQuantityMap.entrySet()) {

			Integer key = entry.getKey();
			Item item = itemService.getItemById(key);
			Integer quantity = entry.getValue();

			InvoiceItem invoiceItem = new InvoiceItem();
			invoiceItem.setItem(item);
			invoiceItem.setQuantity(quantity);
			double totalPrice = item.getItemPrice().getPrice() * quantity;
			invoiceItem.setTotalPrice(totalPrice);
			invoiceItem.setInvoice(invoice);

			invoiceItems.add(invoiceItem);
		}

		// create invoice Object
		invoice.setBuyer(buyer);
		invoice.setSeller(seller);
		invoice.setType(serviceType);
		invoice.setCreationDate(LocalDateTime.now());
		invoice.setStatusCode(InvoiceStatus.ACTIVE);

		int totalQuantity = invoiceItems.stream().map(i -> i.getQuantity()).reduce(0, Integer::sum);
		invoice.setTotalQuantity(totalQuantity);

		double invoiceTotalPrice = invoiceItems.stream().map(i -> i.getTotalPrice()).reduce(0d, Double::sum);
		invoice.setTotalPrice(invoiceTotalPrice);

		String invoiceNumberStr = generatedInvoiceNumberService.generateInvoiceNumber(invoice); // Generate InvoiceNumber
		invoice.setInvoiceNumber(invoiceNumberStr);

		invoice.setInvoiceItems(invoiceItems);

		InvoiceBuilder invoiceBuilder = InvoiceBuilderFactory.getInvoiceBuilder(invoice);
		byte[] invoiceDataStream = invoiceBuilder.createInvoicePdf();

		// create invoicePdf Object
		InvoicePdf invoicePdf = new InvoicePdf();
		invoicePdf.setPdfData(invoiceDataStream);
		invoicePdf.setInvoice(invoice);

		invoice.setInvoicePdf(invoicePdf);

		invoice = invoiceService.save(invoice);// saving Table Invoice and ChildTables (InvoiceItem and InvoicePdf)

		String successMsg = "Invoice " + invoiceNumberStr + " is created successfully";

		return "redirect:/invoiceEntry?successMsg=" + successMsg;
	}

}
