package com.vikas.invoice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.invoice.entity.Category;
import com.vikas.invoice.entity.Item;
import com.vikas.invoice.entity.ItemPrice;
import com.vikas.invoice.entity.Seller;
import com.vikas.invoice.repository.ItemPriceRepository;

@Service
public class ItemPriceService {

	@Autowired
	private ItemPriceRepository itemPriceRepository;

	public void save(ItemPrice itemPrice) {
		itemPriceRepository.save(itemPrice);
	}

	public void deleteById(int id) {
		itemPriceRepository.deleteById(id);
	}

	public ItemPrice getItemPriceById(int id) {
		Optional<ItemPrice> itemPriceOpt = itemPriceRepository.findById(id);
		return itemPriceOpt.isPresent() ? itemPriceOpt.get() : null;
	}

	public List<ItemPrice> getItemPricesById(List<Integer> ids) {
		return (List<ItemPrice>) itemPriceRepository.findAllById(ids);
	}

	public List<ItemPrice> getAllItemPrices() {
		return (List<ItemPrice>) itemPriceRepository.findAll();
	}

	public ItemPrice getItemPrice(Item item, Seller seller) {
		ItemPrice itemPrice = item.getItemPrices().stream().filter(ip -> ip.getSeller().getId() == seller.getId()).findFirst().orElse(null);
		return itemPrice;
	}

	public List<ItemPrice> getItemPrices(Category category, Seller seller) {
		List<ItemPrice> itemPrices = seller.getItemPrices().stream().filter(s -> s.getItem().getCategory().getId() == category.getId())
				.collect(Collectors.toList());
		return itemPrices;
	}
}
