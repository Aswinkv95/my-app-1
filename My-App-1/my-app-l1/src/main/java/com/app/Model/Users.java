package com.app.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*; 
import java.time.LocalDateTime; 

@Entity 
@Table(name = "users") 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users { 
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; 
	@Column(nullable = false, unique = true) 
	private String email; 
	@Column(nullable = false, unique = true) 
	private String username; 
	@Column(length = 15) 
	private String phone; 
	@Column(name = "password_hash", nullable = false) 
	private String passwordHash; 
	@Column(nullable = false) 
	private String role; 
	@Column(name = "is_active", nullable = false) 
	private Boolean isActive; 
	@Column(name = "is_verified", nullable = false) 
	private Boolean isVerified; 
	@Column(name = "failed_login_attempts") 
	private Integer failedLoginAttempts; 
	@Column(name = "last_failed_login_at") 
	private LocalDateTime lastFailedLoginAt; 
	@Column(name = "locked_until") 
	private LocalDateTime lockedUntil; 
	@Column(name = "last_login_at") 
	private LocalDateTime lastLoginAt; 
	@Column(name = "last_login_ip") 
	private String lastLoginIp; 
	@Column(name = "password_changed_at") 
	private LocalDateTime passwordChangedAt; 
	@Column(name = "created_at", updatable = false) 
	private LocalDateTime createdAt; 
	@Column(name = "updated_at") 
	private LocalDateTime updatedAt; 
	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

}
