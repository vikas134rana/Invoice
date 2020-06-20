package com.vikas.invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.invoice.entity.Category;
import com.vikas.invoice.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public void save(Category category) {
		categoryRepository.save(category);
	}

	public void deleteById(int id) {
		categoryRepository.deleteById(id);
	}

	public Category getCategoryById(int id) {
		Optional<Category> categoryOpt = categoryRepository.findById(id);
		return categoryOpt.isPresent() ? categoryOpt.get() : null;
	}

	public List<Category> getAllCategories() {
		return (List<Category>) categoryRepository.findAll();
	}

}
