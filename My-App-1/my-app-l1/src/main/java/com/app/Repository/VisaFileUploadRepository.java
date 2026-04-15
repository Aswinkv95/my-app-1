package com.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.Model.VisaCbs;

@Repository
public interface VisaFileUploadRepository extends JpaRepository<VisaCbs, String> {

	

	
}
