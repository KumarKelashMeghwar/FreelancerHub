package com.freelancerhub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelancerhub.entities.Gig;

public interface GigRepository extends JpaRepository<Gig, Long>{
	
	
	List<Gig> findByOwnerId(Long ownerId);
	
	
	
}
