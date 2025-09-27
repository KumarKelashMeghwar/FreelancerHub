package com.freelancerhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelancerhub.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	boolean existsByName(String name);
}
