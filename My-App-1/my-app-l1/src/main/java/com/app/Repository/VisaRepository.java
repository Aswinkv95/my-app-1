package com.app.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yaml.snakeyaml.events.Event.ID;


import com.app.Model.VisaCbs;

public interface VisaRepository extends JpaRepository<VisaCbs, String> {

	@Query(
	        value = "SELECT TRANSACTION_TYPE,TRANSACTION_DATE,TRANSACTION_AMOUNT, "+
	                " DB_CR_FLAG,STATUS,FILENAME,RECON_ID FROM VISA_CBS WHERE "+
	        		    " TRANSACTION_DATE between  :fdate and   :tdate ",
	        nativeQuery = true
	    )
	List<VisaCbs> findByExceptionDateBetween(@Param("fdate")
	      LocalDate fromDate,@Param("tdate") LocalDate toDate);

	
}
