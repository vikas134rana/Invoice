package com.vikas.invoice.controller.asset;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vikas.invoice.entity.Buyer;
import com.vikas.invoice.entity.Invoice;
import com.vikas.invoice.entity.InvoiceItem;
import com.vikas.invoice.entity.Item;
import com.vikas.invoice.entity.Seller;
import com.vikas.invoice.entity.enums.InvoiceStatus;
import com.vikas.invoice.service.BuyerService;
import com.vikas.invoice.service.GeneratedInvoiceNumberService;
import com.vikas.invoice.service.InvoiceItemService;
import com.vikas.invoice.service.InvoiceService;
import com.vikas.invoice.service.ItemService;
import com.vikas.invoice.service.SellerService;

@Controller
public class AssetInvoiceEntryController {

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
	GeneratedInvoiceNumberService generatedInvoiceNumberService;

	@GetMapping("/assetInvoiceEntry")
	public String loadForm() {
		return null;
	}

	// @formatter:off
	@PostMapping("/saveAssetInvoiceEntry")
	public void saveForm(
			@RequestParam("type") int serviceType, 
			@RequestParam("buyerId") int buyerId, 
			@RequestParam("sellerId") int sellerId,
			@RequestParam("itemId") Integer[] itemIds, 
			@RequestParam("quantity") Integer[] quantities) {
		// @formatter:on

		Buyer buyer = buyerService.getBuyerById(buyerId);
		Seller seller = sellerService.getSellerById(sellerId);

		Map<Integer, Integer> itemQuantityMap = new HashMap<>();
		for (int i = 0; i < itemIds.length; i++) {
			if (quantities[i] > 0)
				itemQuantityMap.put(itemIds[i], quantities[i]);
		}

		// create invoiceItem Object
		List<InvoiceItem> invoiceItems = new ArrayList<>();
		for (Entry<Integer, Integer> entry : itemQuantityMap.entrySet()) {

			Integer key = entry.getKey();
			Item item = itemService.getItemById(key);
			Integer quantity = entry.getValue();

			InvoiceItem invoiceItem = new InvoiceItem();
			invoiceItem.setItem(item);
			invoiceItem.setQuantity(quantity);
			double totalPrice = item.getPrice() * quantity;
			invoiceItem.setTotalPrice(totalPrice);

			invoiceItems.add(invoiceItem);
		}

		// create invoice Object
		Invoice invoice = new Invoice();
		invoice.setBuyer(buyer);
		invoice.setSeller(seller);
		invoice.setType(serviceType);
		invoice.setCreationDate(new Date());
		invoice.setStatusCode(InvoiceStatus.ACTIVE);

		int totalQuantity = invoiceItems.stream().map(i -> i.getQuantity()).reduce(0, Integer::sum);
		invoice.setTotalQuantity(totalQuantity);

		double invoiceTotalPrice = invoiceItems.stream().map(i -> i.getTotalPrice()).reduce(0d, Double::sum);
		invoice.setTotalPrice(invoiceTotalPrice);

		// Generate InvoiceNumber
		String invoiceNumberStr = generatedInvoiceNumberService.generateInvoiceNumber(invoice);
		invoice.setInvoiceNumber(invoiceNumberStr);

		// saving invoice to database
		invoiceService.save(invoice);

		// updating invoiceId in each invoiceItem
		for (InvoiceItem invoiceItem : invoiceItems) {
			invoiceItem.setInvoice(invoice);
		}

	}

}
