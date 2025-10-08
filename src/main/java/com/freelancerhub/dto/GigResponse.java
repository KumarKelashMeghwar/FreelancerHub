package com.freelancerhub.dto;

import java.math.BigDecimal;
import java.util.List;

public class GigResponse {
	
	private Long id;
	private String title;
	private String description;
	private BigDecimal price;
	private Long categoryId;
	private String categoryName;
	private List<String> tags;
	private List<String> images;
	
	public GigResponse() {
		
	}
	
	public GigResponse(Long id, String title, String description, BigDecimal price, Long categoryId,
			String categoryName, List<String> tags, List<String> images) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.tags = tags;
		this.images = images;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
	
	
	
	

}
