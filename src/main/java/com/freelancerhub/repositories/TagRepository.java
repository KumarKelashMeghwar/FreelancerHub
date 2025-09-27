package com.freelancerhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelancerhub.entities.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
	boolean existsByName(String name);
}
