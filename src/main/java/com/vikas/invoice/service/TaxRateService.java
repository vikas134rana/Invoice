package com.vikas.invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.invoice.entity.Category;
import com.vikas.invoice.entity.TaxRate;
import com.vikas.invoice.repository.TaxRateRepository;

@Service
public class TaxRateService {

	@Autowired
	private TaxRateRepository taxRateRepository;

	public void save(TaxRate taxRate) {
		taxRateRepository.save(taxRate);
	}

	public void deleteById(int id) {
		taxRateRepository.deleteById(id);
	}

	public TaxRate getTaxRateById(int id) {
		Optional<TaxRate> taxRateOpt = taxRateRepository.findById(id);
		return taxRateOpt.isPresent() ? taxRateOpt.get() : null;
	}

	public List<TaxRate> getAllTaxRates() {
		return (List<TaxRate>) taxRateRepository.findAll();
	}

	public TaxRate getTaxRate(Category category) {
		TaxRate taxRate = category.getTaxRates().stream().findFirst().orElse(null);
		return taxRate;
	}
}
