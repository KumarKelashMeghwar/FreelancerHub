package com.freelancerhub.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.freelancerhub.entities.Category;
import com.freelancerhub.entities.Gig;
import com.freelancerhub.entities.GigImage;
import com.freelancerhub.entities.Tag;
import com.freelancerhub.entities.User;
import com.freelancerhub.repositories.CategoryRepository;
import com.freelancerhub.repositories.GigRepository;
import com.freelancerhub.repositories.TagRepository;

@Service
public class GigService {

	private GigRepository gigRepository = null;
	private TagRepository tagRepository = null;
	private CategoryRepository categoryRepository = null;
	private Path uploadRoot = null;

	public GigService(GigRepository gigRepository, TagRepository tagRepository, CategoryRepository categoryRepository,
			@Value("${file.upload-dir:uploads}") String uploadDir) {
		this.categoryRepository = categoryRepository;
		this.gigRepository = gigRepository;
		this.tagRepository = tagRepository;
		this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
	}

	public Gig createGig(String title, String description, BigDecimal price, Long categoryId, List<Long> tagIds,
			List<MultipartFile> images, User owner) throws IOException {

		if (images == null || images.size() < 1 || images.size() > 3) {
			throw new IllegalArgumentException("Images count must be between 1 and 3");
		}

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new NoSuchElementException("Category not found"));

		List<Tag> tagEntities = tagRepository.findAllById(tagIds);
		if (tagEntities.size() != tagIds.size()) {
			throw new NoSuchElementException("One or more tags not found");
		}

		// creating gig without images firsts
		Gig gig = new Gig();
		gig.setTitle(title);
		gig.setDescription(description);
		gig.setPrice(price);
		gig.setCategory(category);
		gig.setTags(new HashSet<>(tagEntities));
		gig.setOwner(owner);

		gig = gigRepository.save(gig);

		Path gigDir = uploadRoot.resolve("gigs").resolve(String.valueOf(gig.getId()));

		Files.createDirectories(gigDir);

		for (MultipartFile file : images) {
			String contentType = Optional.ofNullable(file.getContentType()).orElse("");

			if (!contentType.startsWith("image/")) {
				throw new IllegalArgumentException("Only image files are allowed");
			}

			String original = file.getOriginalFilename();

			String ext = "";
			int i = original != null ? original.lastIndexOf(".") : -1;
			if (i > 0)
				ext = original.substring(i);

			String filename = UUID.randomUUID().toString() + ext;
			Path target = gigDir.resolve(filename);

			Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

			GigImage img = new GigImage();

			img.setFilename(filename);
			img.setUrl("/uploads/gigs/" + gig.getId() + "/" + filename);
			gig.addImage(img);

		}

		return gigRepository.save(gig);

	}

	public Gig updateGig(Long gigId, String title, String description, BigDecimal price, Long categoryId,
			List<Long> tagIds, List<String> existingImages, List<MultipartFile> newImages, User owner)
			throws IOException {

		Gig gig = gigRepository.findById(gigId).orElseThrow(() -> new NoSuchElementException("Gig not found"));

		if (!gig.getOwner().getId().equals(owner.getId())) {
			throw new SecurityException("Not allowed to update this gig");
		}

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new NoSuchElementException("Category not found"));

		List<Tag> tagEntities = tagRepository.findAllById(tagIds);
		if (tagEntities.size() != tagIds.size()) {
			throw new NoSuchElementException("One or more tags not found");
		}

		System.out.println(newImages);
		
		// update basic fields
		gig.setTitle(title);
		gig.setDescription(description);
		gig.setPrice(price);
		gig.setCategory(category);
		gig.setTags(new HashSet<>(tagEntities));

		// ---- Handle Images ----
		// 1. Keep only existing images that front end sent
		Set<String> keepSet = existingImages != null ? new HashSet<>(existingImages) : new HashSet<>();
		
		gig.getImages().removeIf(img -> !keepSet.contains(img.getUrl()));

		// 2. Add new uploaded images
		if (newImages != null && !newImages.isEmpty()) {
			Path gigDir = uploadRoot.resolve("gigs").resolve(String.valueOf(gig.getId()));
			Files.createDirectories(gigDir);

			for (MultipartFile file : newImages) {
				
				System.out.println("new imageXX : " +  file.getOriginalFilename());
				String contentType = Optional.ofNullable(file.getContentType()).orElse("");
				if (!contentType.startsWith("image/")) {
					throw new IllegalArgumentException("Only image files are allowed");
				}

				String original = file.getOriginalFilename();
				String ext = "";
				int i = original != null ? original.lastIndexOf(".") : -1;
				if (i > 0)
					ext = original.substring(i);

				String filename = UUID.randomUUID().toString() + ext;
				Path target = gigDir.resolve(filename);
				Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

				GigImage img = new GigImage();
				img.setFilename(filename);
				img.setUrl("/uploads/gigs/" + gig.getId() + "/" + filename);
				gig.addImage(img);
			}
		}

		// Ensure total images are between 1 and 3
		System.out.println("Size of images : " + gig.getImages().size());
		if (gig.getImages().size() < 1 || gig.getImages().size() > 3) {
			throw new IllegalArgumentException("Images count must be between 1 and 3");
		}

		return gigRepository.save(gig);
	}

	public Optional<Gig> getById(Long id) {
		return gigRepository.findById(id);
	}

	public List<Gig> getAllByOwnerId(Long ownerId) {
		return gigRepository.findByOwnerId(ownerId);
	}

	public boolean deleteById(Long id) {
		if (gigRepository.existsById(id)) {
			gigRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

}