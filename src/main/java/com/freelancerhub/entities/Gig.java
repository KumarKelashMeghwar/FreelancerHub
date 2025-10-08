package com.freelancerhub.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "gigs")
@EntityListeners(AuditingEntityListener.class)
public class Gig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(length = 5000)
	private String description;

	@Column(nullable = false)
	private BigDecimal price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@ManyToMany
	@JoinTable(name = "gig_tags", joinColumns = @JoinColumn(name = "gig_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();

	@OneToMany(mappedBy = "gig", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GigImage> images = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User owner;
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private Timestamp createdAt;
	
	@UpdateTimestamp
	@Column(nullable = false)
	private Timestamp updatedAt;
	

	public void addImage(GigImage img) {
		images.add(img);
		img.setGig(this);
	}

	public void removeImage(GigImage img) {
		images.remove(img);
		img.setGig(null);
	}

	public Gig() {

	}

	public Gig(Long id, String title, String description, BigDecimal price, Category category, Set<Tag> tags,
			List<GigImage> images, User owner) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.category = category;
		this.tags = tags;
		this.images = images;
		this.owner = owner;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public List<GigImage> getImages() {
		return images;
	}

	public void setImages(List<GigImage> images) {
		this.images = images;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	

}
