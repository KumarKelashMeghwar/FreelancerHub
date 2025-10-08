package com.freelancerhub.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.freelancerhub.dto.GigResponse;
import com.freelancerhub.entities.Gig;
import com.freelancerhub.entities.User;
import com.freelancerhub.services.GigService;

@RestController
@RequestMapping("/api/gigs")
public class GigController {

	@Autowired
	private GigService gigService;
	
	
	
	@PostMapping(value="/create", consumes = {"multipart/form-data"})
	public ResponseEntity<?> createGig(@RequestParam String title, @RequestParam String description,
			@RequestParam BigDecimal price, @RequestParam Long categoryId, @RequestParam List<Long> tagIds,
			@RequestParam("images") List<MultipartFile> images

	) throws IOException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		Gig gig = gigService.createGig(title, description, price, categoryId, tagIds, images, user);
		
		GigResponse dto = mapToDto(gig);
			
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);

	}
	
	
	@PutMapping(value="/update/{id}", consumes = {"multipart/form-data"})
	public ResponseEntity<?> updateGig(
	        @PathVariable Long id,
	        @RequestParam String title,
	        @RequestParam String description,
	        @RequestParam BigDecimal price,
	        @RequestParam Long categoryId,
	        @RequestParam List<Long> tagIds,
	        @RequestParam(value = "images", required = false) List<MultipartFile> images,
	        @RequestParam(value = "existingImages", required = false) List<String> existingImages
	) throws IOException {

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User user = (User) authentication.getPrincipal();

	    Gig updated = gigService.updateGig(id, title, description, price, categoryId, tagIds, existingImages, images, user);

	    GigResponse dto = mapToDto(updated);
	    return ResponseEntity.ok(dto);
	}

	
	
	@GetMapping("/my-gigs")
	public ResponseEntity<?> fetchGigs(){
		
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			List<Gig> allByOwnerId = gigService.getAllByOwnerId(user.getId());
			
			List<GigResponse> responses = allByOwnerId.stream().map(this::mapToDto).toList();
			
			return ResponseEntity.status(HttpStatus.OK).body(responses);
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
	}
	
	@GetMapping("/my-gigs/{gigId}")
	public ResponseEntity<?> fetchGigById(@PathVariable Long gigId){
		
		try {
			
			Optional<Gig> gig = gigService.getById(gigId);
			
			if(gig.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			GigResponse mapToDto = mapToDto(gig.get());
			
			return ResponseEntity.ok(mapToDto);
			
		}catch(Exception e) {
			return ResponseEntity.internalServerError().body("Server Error finding the gig details");
		}
		
	}
	
	
	
	@DeleteMapping("/delete/{gigId}")
	public ResponseEntity<?> deleteGig(@PathVariable Long gigId){
		
		try {
			
			boolean gigDeleted = gigService.deleteById(gigId);
			
			if(gigDeleted) {
				return ResponseEntity.ok("Gig with ID " + gigId + " has been deleted");
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gig with ID " + gigId + " not found");
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error while deleting the gig");
		}
		
	}
	
	
	private GigResponse mapToDto(Gig gig) {
		GigResponse dto = new GigResponse();
		dto.setId(gig.getId());
		dto.setTitle(gig.getTitle());
		dto.setDescription(gig.getDescription());
		dto.setPrice(gig.getPrice());
		dto.setCategoryId(gig.getCategory().getId());
		dto.setCategoryName(gig.getCategory().getName());
		dto.setTags(gig.getTags().stream().map(t -> t.getName()).collect(Collectors.toList()));
		dto.setImages(gig.getImages().stream().map(i -> i.getUrl()).collect(Collectors.toList()));
		
		return dto;
	}

}
