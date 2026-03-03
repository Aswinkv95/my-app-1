package com.app.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.UserDTO;
import com.app.DTO.VisaCbsDTO;
import com.app.Model.Users;
import com.app.Model.VisaCbs;
import com.app.Service.VisaService;

@RestController
@RequestMapping("/BANK")
public class VisaController {
	
	@Autowired
	private VisaService visaService;
	
	 @PostMapping("/visa-exception")
	 public ResponseEntity<List<VisaCbsDTO>> login(
			 @RequestParam("fromDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fromDate,
			 @RequestParam("toDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate toDate  ) { 
		 
		List<VisaCbs> list = visaService.findexceptionWith(fromDate,toDate); 
		List<VisaCbsDTO> dtoList= list.stream().map(entity ->{
			VisaCbsDTO visaCbsDTO = new VisaCbsDTO();
			visaCbsDTO.setTRANSACTION_DATE(entity.getTRANSACTION_DATE());
			visaCbsDTO.setTRASSACTION_AMOUNT(entity.getTRASSACTION_AMOUNT());
			visaCbsDTO.setTRANSACTION_TYPE(entity.getTRANSACTION_TYPE());
			visaCbsDTO.setSTATUS(entity.getSTATUS());
			visaCbsDTO.setDB_CR_FLAG(entity.getDB_CR_FLAG());
			visaCbsDTO.setFILENAME(entity.getFILENAME());
			
			return visaCbsDTO;
		}).toList();
		
		
	     return  new ResponseEntity<>(dtoList, HttpStatus.OK);
	 }

}
