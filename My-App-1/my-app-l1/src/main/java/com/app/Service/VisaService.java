package com.app.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Model.VisaCbs;
import com.app.Repository.VisaRepository;

@Service
public class VisaService {

	@Autowired
	private VisaRepository visaRepository;
	public List<VisaCbs> findexceptionWith(LocalDate fromDate, LocalDate toDate) {
		
		return visaRepository.findByExceptionDateBetween(fromDate, toDate);
	}
	
	

}
