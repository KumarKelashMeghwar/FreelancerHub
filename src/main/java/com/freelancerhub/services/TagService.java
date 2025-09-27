package com.freelancerhub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.freelancerhub.entities.Tag;
import com.freelancerhub.repositories.TagRepository;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;
	
	public Tag addTag(Tag tag) {
		
		if(tagRepository.existsByName(tag.getName())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Tag already exists");
		}
		
		return tagRepository.save(tag);
	}
	
	
	public List<Tag> getAllTags(){
		
		if(tagRepository.findAll().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tags found");
		}
		
		return tagRepository.findAll();
	}
	
	
	
}
