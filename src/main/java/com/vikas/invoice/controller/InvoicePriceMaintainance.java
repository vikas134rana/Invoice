package com.vikas.invoice.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vikas.invoice.entity.Category;
import com.vikas.invoice.entity.Item;
import com.vikas.invoice.entity.ItemPrice;
import com.vikas.invoice.entity.Seller;
import com.vikas.invoice.entity.TaxRate;
import com.vikas.invoice.service.CategoryService;
import com.vikas.invoice.service.ItemPriceService;
import com.vikas.invoice.service.ItemService;
import com.vikas.invoice.service.SellerService;
import com.vikas.invoice.service.TaxRateService;
import com.vikas.invoice.util.Util;

@Controller
public class InvoicePriceMaintainance {

	@Autowired
	SellerService sellerService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ItemService itemService;

	@Autowired
	ItemPriceService itemPriceService;

	@Autowired
	TaxRateService taxRateService;

	// @formatter:off
	@RequestMapping("/invoicePriceMaintainance")
	public String invoicePriceMaintainance(Model model, 
			@RequestParam(value = "sellerId", required = false) Integer sellerId,
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			@RequestParam(value = "categorySearch", required = false) String categorySearch,
			@RequestParam(value = "itemId", required = false) Integer itemId,
			@RequestParam(value = "itemSearch", required = false) String itemSearch) {
		// @formatter:on

		List<Seller> sellers = sellerService.getAllSellers();
		model.addAttribute("sellers", sellers);

		// when sellerId is Selected
		if (sellerId != null && sellerId != 0) {
			Seller selectedSeller = sellerService.getSellerById(sellerId);
			model.addAttribute("selectedSeller", selectedSeller);

			List<Category> categories = categoryService.getAllCategories();

			if (categorySearch != null) {
				categories = categories.stream().filter(c -> c.getName().toLowerCase().contains(categorySearch.toLowerCase())).collect(Collectors.toList());
				model.addAttribute("categorySearch", categorySearch);
			}

			model.addAttribute("categories", categories);

			// when categoryId is Selected (sellerId is already selected)
			if (categoryId != null && categoryId != 0) {
				Category selectedCategory = categoryService.getCategoryById(categoryId);
				model.addAttribute("selectedCategory", selectedCategory);

				TaxRate selectedTaxRate = taxRateService.getTaxRate(selectedCategory);
				model.addAttribute("selectedTaxRate", selectedTaxRate);

				List<Item> items = selectedCategory.getItems();

				if (itemSearch != null) {
					items = items.stream().filter(i -> i.getName().toLowerCase().contains(itemSearch.toLowerCase())).collect(Collectors.toList());
					model.addAttribute("itemSearch", itemSearch);
				}

				model.addAttribute("items", items);
				model.addAttribute("units", Util.getItemUnits());
				
				// when itemId is Selected (categoryId and sellerId is already selected)
				if (itemId != null && itemId != 0) {
					Item selectedItem = itemService.getItemById(itemId);
					model.addAttribute("selectedItem", selectedItem);

					ItemPrice selectedItemPrice = itemPriceService.getItemPrice(selectedItem, selectedSeller);
					model.addAttribute("selectedItemPrice", selectedItemPrice);
				}
			}
		}

		return "invoice_price_maintainance";
	}

	// @formatter:off
	// Create or Update Category
	@RequestMapping("/invoicePriceMaintainance/saveCategory")
	public String createCategory(Model model, 
			@RequestParam(value = "sellerId") int sellerId,
			@RequestParam(value = "categoryId") Integer categoryId,
			@RequestParam(value = "categoryName") String categoryName,
			@RequestParam(value = "hsn") String hsn,
			@RequestParam(value = "cgstRate") double cgstRate,
			@RequestParam(value = "sgstRate") double sgstRate) {
		// @formatter:on

		System.out.println("sellerId: " + sellerId + ", " + "categoryId: " + categoryId + ", " + "categoryName: " + categoryName + ", " + "hsn: " + hsn + ", "
				+ "cgstRate: " + cgstRate + ", " + "sgstRate: " + sgstRate);

		Seller seller = sellerService.getSellerById(sellerId);

		Category category;

		// Update
		if (categoryId != null) {

			System.out.println("=========== Updating Category ===========");

			category = categoryService.getCategoryById(categoryId);
			category.setHsnCode(hsn);

			TaxRate taxRate = taxRateService.getTaxRate(category);
			taxRate.setCgstRate(cgstRate);
			taxRate.setSgstRate(sgstRate);
			taxRate.setStartDate(LocalDateTime.now());

			ArrayList<TaxRate> taxRates = new ArrayList<>(Arrays.asList(taxRate));
			category.setTaxRates(taxRates);
		}
		// Create
		else {

			System.out.println("=========== Creating Category ===========");

			category = new Category();
			category.setName(categoryName);
			category.setHsnCode(hsn);

			TaxRate taxRate = new TaxRate();
			taxRate.setCategory(category);
			taxRate.setSeller(seller);
			taxRate.setCgstRate(cgstRate);
			taxRate.setSgstRate(sgstRate);
			taxRate.setStartDate(LocalDateTime.now());

			category.setTaxRates(Arrays.asList(taxRate));
		}

		categoryService.save(category); // update/create category

		return "redirect:/invoicePriceMaintainance";
	}

	// @formatter:off
	// Create or Update Item
	@RequestMapping("/invoicePriceMaintainance/saveItem")
	public String createItem(Model model, 
		@RequestParam(value = "sellerId") int sellerId,
		@RequestParam(value = "categoryId") int categoryId,
		@RequestParam(value = "itemId") Integer itemId,
		@RequestParam(value = "itemName") String itemName,
		@RequestParam(value = "unit") String unit,		
		@RequestParam(value = "price") double price) {
		// @formatter:on

		Seller seller = sellerService.getSellerById(sellerId);

		Item item;

		// Update
		if (itemId != null) {

			System.out.println("=========== Updating Item ===========");

			item = itemService.getItemById(itemId);
			item.setUnit(unit);
			
			ItemPrice itemPrice = itemPriceService.getItemPrice(item, seller);
			itemPrice.setPrice(price);
			itemPrice.setStart_date(LocalDateTime.now());

			ArrayList<ItemPrice> itemPrices = new ArrayList<>(Arrays.asList(itemPrice));
			item.setItemPrices(itemPrices);
		}
		// Create
		else {

			System.out.println("=========== Creating Item ===========");

			item = new Item();
			item.setName(itemName);
			item.setUnit(unit);
			
			Category category = categoryService.getCategoryById(categoryId);
			item.setCategory(category);

			ItemPrice itemPrice = new ItemPrice();
			itemPrice.setItem(item);
			itemPrice.setPrice(price);
			itemPrice.setSeller(seller);
			itemPrice.setStart_date(LocalDateTime.now());

			item.setItemPrices(Arrays.asList(itemPrice));
		}

		itemService.save(item); // update/create item

		return "redirect:/invoicePriceMaintainance";
	}

}
