package com.vikas.invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vikas.invoice.entity.ItemPrice;
import com.vikas.invoice.entity.Seller;
import com.vikas.invoice.service.SellerService;

@Controller
public class HomeController {

	@Autowired
	SellerService sellerService;

	@RequestMapping("/")
	public String home() {

		Seller seller = sellerService.getSellerById(1);
		System.out.println(seller);

		for (ItemPrice itemPrice : seller.getItemPrices()) {
			System.out.println("\t"+itemPrice);
			System.out.println("\t\t"+itemPrice.getItem());
		}
		return "home";
	}

}
