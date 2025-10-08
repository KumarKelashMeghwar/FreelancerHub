package com.freelancerhub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelancerhub.entities.Category;
import com.freelancerhub.entities.Tag;
import com.freelancerhub.services.CategoryService;
import com.freelancerhub.services.TagService;

@RestController
@RequestMapping("/api")
public class AdminController {
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TagService tagService;
	
	
	// Categories
	
	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getCategories(){
		return ResponseEntity.ok(categoryService.getCategories());
	}
	
	
	@PostMapping("/admin/add-category")
	public ResponseEntity<?> addCategory(@RequestBody Category category){
		
		try {
			Category savedCategory = categoryService.addCategory(category);
			return ResponseEntity.ok(savedCategory);
			
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	// Tags
	
	@PostMapping("/admin/add-tag")
	public ResponseEntity<Tag> addTag(@RequestBody Tag tag){
		
		try {
			Tag savedTag = tagService.addTag(tag);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedTag);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Tag((long) 500, e.getMessage()));
		}
		
	}
	
	@GetMapping("/tags")
	public ResponseEntity<List<Tag>> getTags(){
		try {
			return ResponseEntity.ok(tagService.getAllTags());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(new Tag()));
		}
	}
	
	

}
