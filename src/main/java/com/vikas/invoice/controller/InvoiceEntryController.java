package com.vikas.invoice.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vikas.invoice.entity.Buyer;
import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.entity.InvoiceItem;
import com.vikas.invoice.entity.InvoicePdf;
import com.vikas.invoice.entity.Item;
import com.vikas.invoice.entity.Seller;
import com.vikas.invoice.entity.TaxRate;
import com.vikas.invoice.entity.enums.InvoiceStatus;
import com.vikas.invoice.features.invoicepdf.InvoiceBuilder;
import com.vikas.invoice.features.invoicepdf.InvoiceBuilderFactory;
import com.vikas.invoice.service.BuyerService;
import com.vikas.invoice.service.InvoiceItemService;
import com.vikas.invoice.service.InvoicePdfService;
import com.vikas.invoice.service.InvoiceSeriesService;
import com.vikas.invoice.service.InvoiceService;
import com.vikas.invoice.service.ItemPriceService;
import com.vikas.invoice.service.ItemService;
import com.vikas.invoice.service.SellerService;
import com.vikas.invoice.service.TaxRateService;
import com.vikas.invoice.util.InvoiceUtils;
import com.vikas.invoice.util.Util;

@Controller
public class InvoiceEntryController {

	@Autowired
	ItemService itemService;

	@Autowired
	ItemPriceService itemPriceService;

	@Autowired
	TaxRateService taxRateService;

	@Autowired
	SellerService sellerService;

	@Autowired
	BuyerService buyerService;

	@Autowired
	InvoiceService invoiceService;

	@Autowired
	InvoiceItemService invoiceItemService;

	@Autowired
	InvoiceSeriesService invoiceSeriesService;

	@Autowired
	InvoicePdfService invoicePdfService;

	@GetMapping("/invoiceEntry")
	public String invoiceEntry(Model model, @RequestParam(required = false, name = "sellerId") Integer sellerId,
			@RequestParam(required = false, name = "successMsg") String successMsg) {

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

		// Seller is selected
		if (sellerId != null && sellerId != 0) {
			Seller seller = sellerService.getSellerById(sellerId);
			model.addAttribute("selectedSeller", seller);
		}

		model.addAttribute("itemPriceService", itemPriceService);
		model.addAttribute("taxRateService", taxRateService);
		model.addAttribute("buyerService", buyerService);

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

		boolean isIgst = InvoiceUtils.isIGST(seller, buyer);

		// updating itemId and its quantity in the map
		Map<Integer, Integer> itemQuantityMap = new HashMap<>();
		for (int i = 0; i < itemIds.length; i++) {
			if (quantities[i] != null && quantities[i] > 0)
				itemQuantityMap.put(itemIds[i], quantities[i]);
		}

		int totalQuantity = 0;
		double totalNetAmount = 0;
		double totalCgstAmount = 0;
		double totalSgstAmount = 0;
		double totalIgstAmount = 0;
		double invoiceTotalAmount = 0;

		// create invoiceItem Object (with the help of itemQuantityMap)
		List<InvoiceItem> invoiceItems = new ArrayList<>();
		for (Entry<Integer, Integer> entry : itemQuantityMap.entrySet()) {

			Integer key = entry.getKey();
			Item item = itemService.getItemById(key);
			Integer quantity = entry.getValue();

			InvoiceItem invoiceItem = new InvoiceItem();
			invoiceItem.setItem(item);
			invoiceItem.setQuantity(quantity);
			invoiceItem.setUnit(item.getUnit());

			double itemPrice = itemPriceService.getItemPrice(item, seller).getPrice();
			invoiceItem.setUnitPrice(itemPrice);

			double netAmount = Util.roundToTwoDigit(itemPrice * quantity);
			invoiceItem.setNetAmount(netAmount);

			TaxRate taxRate = taxRateService.getTaxRate(item.getCategory());
			double cgstRate = taxRate.getCgstRate();
			double sgstRate = taxRate.getSgstRate();

			double taxAmount;

			if (isIgst) {
				double cgstAmount = Util.roundToTwoDigit(netAmount * cgstRate / 100);
				double sgstAmount = Util.roundToTwoDigit(netAmount * sgstRate / 100);
				double igstRate = cgstRate + sgstRate;
				invoiceItem.setIgstRate(igstRate);
				double igstAmount = Util.roundToTwoDigit(cgstAmount + sgstAmount);
				invoiceItem.setIgstAmount(igstAmount);
				taxAmount = igstAmount;
				totalIgstAmount += igstAmount;

			} else {
				double cgstAmount = Util.roundToTwoDigit(netAmount * cgstRate / 100);
				double sgstAmount = Util.roundToTwoDigit(netAmount * sgstRate / 100);
				invoiceItem.setCgstRate(cgstRate);
				invoiceItem.setSgstRate(sgstRate);
				invoiceItem.setCgstAmount(cgstAmount);
				invoiceItem.setSgstAmount(sgstAmount);
				taxAmount = cgstAmount + sgstAmount;

				totalCgstAmount += cgstAmount;
				totalSgstAmount += sgstAmount;
			}

			double totalAmount = Util.roundToTwoDigit(netAmount + taxAmount);
			invoiceItem.setTotalAmount(totalAmount);

			invoiceItem.setInvoice(invoice);

			invoiceItems.add(invoiceItem);

			totalQuantity += quantity;
			totalNetAmount += netAmount;
			invoiceTotalAmount += totalAmount;
		}

		// create invoice Object
		invoice.setBuyer(buyer);
		invoice.setSeller(seller);
		invoice.setType(serviceType);
		invoice.setCreationDate(LocalDateTime.now());
		invoice.setStatusCode(InvoiceStatus.ACTIVE);
		invoice.setTotalQuantity(totalQuantity);
		invoice.setTotalNetAmount(Util.roundToTwoDigit(totalNetAmount));
		invoice.setTotalCgstAmount(Util.roundToTwoDigit(totalCgstAmount));
		invoice.setTotalSgstAmount(Util.roundToTwoDigit(totalSgstAmount));
		invoice.setTotalIgstAmount(Util.roundToTwoDigit(totalIgstAmount));
		invoice.setTotalAmount(Util.roundToTwoDigit(invoiceTotalAmount));

		String invoiceNumber = invoiceSeriesService.generateInvoiceNumber(invoice); // Generate InvoiceNumber
		invoice.setInvoiceNumber(invoiceNumber);

		invoice.setInvoiceItems(invoiceItems);

		InvoiceBuilder invoiceBuilder = InvoiceBuilderFactory.getInvoiceBuilder(invoice);
		byte[] invoiceDataStream = invoiceBuilder.createInvoicePdf();

		// create invoicePdf Object
		InvoicePdf invoicePdf = new InvoicePdf();
		invoicePdf.setPdfData(invoiceDataStream);
		invoicePdf.setInvoice(invoice);

		invoice.setInvoicePdf(invoicePdf);

		invoice = invoiceService.save(invoice);// saving Table Invoice and ChildTables (InvoiceItem and InvoicePdf)

		String successMsg = "Invoice " + invoiceNumber + " is created successfully";

		return "redirect:/invoiceEntry?successMsg=" + successMsg;
	}

	@RequestMapping(value = "/getBuyer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Buyer getBuyer(@RequestParam("id") int id) {
		Buyer buyer = buyerService.getBuyerById(id);
		return buyer;
	}
}
