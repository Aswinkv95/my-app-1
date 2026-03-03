package com.app.DTO;

import com.app.Model.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private boolean success; 
	private String message;
	private Users user;
}
