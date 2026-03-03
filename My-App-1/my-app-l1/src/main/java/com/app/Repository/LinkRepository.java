package com.app.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Model.Link;

public interface LinkRepository extends JpaRepository<Link, UUID> {

	 List<Link> findByIsActiveTrueOrderByCreatedAt();
	 
}
