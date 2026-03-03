package com.app.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.UserDTO;
import com.app.Model.Users;

@RestController
@RequestMapping("/BANK")
public class UserController {
	
	 @PostMapping("/login")
	 public ResponseEntity<UserDTO> login(@RequestBody Users user) { 
		 System.out.println(user.getUsername()+"-------"+user.getPasswordHash());
		  UserDTO userDto = new UserDTO(true, "Login successful", user); 
		  
	     return ResponseEntity.ok(userDto);   
	 }
	 

	
}
