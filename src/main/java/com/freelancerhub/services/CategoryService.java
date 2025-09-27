package com.freelancerhub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.freelancerhub.entities.Category;
import com.freelancerhub.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category addCategory(Category category) {
		
		if(categoryRepository.existsByName(category.getName()))
		{
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
		}
		
		return categoryRepository.save(category);
		
	}
	
	public List<Category> getCategories(){
		
		return categoryRepository.findAll();
		
	}

}
