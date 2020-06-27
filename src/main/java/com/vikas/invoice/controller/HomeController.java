package com.vikas.invoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vikas.invoice.entity.InvoiceItem;
import com.vikas.invoice.service.AddressService;
import com.vikas.invoice.service.BuyerService;
import com.vikas.invoice.service.CategoryService;
import com.vikas.invoice.service.CountryService;
import com.vikas.invoice.service.GeneratedInvoiceNumberService;
import com.vikas.invoice.service.InvoiceItemService;
import com.vikas.invoice.service.InvoiceService;
import com.vikas.invoice.service.ItemService;
import com.vikas.invoice.service.SellerService;
import com.vikas.invoice.service.StateService;

@Controller
public class HomeController {

	@Autowired
	CountryService countryService;

	@Autowired
	StateService stateService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ItemService itemService;

	@Autowired
	AddressService addressService;

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

	@RequestMapping("/")
	public String home() {
		tempMethod();
		return "home";
	}

	private void tempMethod() {
		countryService.getAllCountries().forEach(i -> System.out.println(i));

		System.out.println("===========================================================");
		stateService.getAllStates().forEach(i -> System.out.println(i));

		System.out.println("===========================================================");
		categoryService.getAllCategories().forEach(i -> System.out.println(i));

		System.out.println("===========================================================");
		itemService.getAllItems().forEach(i -> System.out.println(i));

		System.out.println("===========================================================");
		addressService.getAllAddresses().forEach(i -> System.out.println(i));

		System.out.println("===========================================================");
		sellerService.getAllSellers().forEach(i -> System.out.println(i));

		System.out.println("===========================================================");
		buyerService.getAllBuyers().forEach(i -> System.out.println(i));

		System.out.println("===========================================================");
		invoiceService.getAllInvoices().forEach(i -> System.out.println(i));

		System.out.println("===========================================================");
		invoiceItemService.getAllInvoiceItems().forEach(i -> System.out.println(i));

		System.out.println("===========================================================");
		String invoiceNumber = generatedInvoiceNumberService.generateInvoiceNumber(invoiceService.getInvoiceById(3));
		System.out.println("InvoiceNumber: " + invoiceNumber);
		
		System.out.println("===========================================================");
		List<InvoiceItem> invoiceItems = invoiceService.getInvoiceById(13).getInvoiceItems();
		System.out.println(invoiceItems);
	}

}
