package com.app.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import com.app.Model.Users;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisaCbsDTO {
	
	private String TRANSACTION_TYPE;
	private LocalDate TRANSACTION_DATE;
	@Column(name = "TRANSACTION_AMOUNT", precision = 15, scale = 2)
	private BigDecimal TRASSACTION_AMOUNT;
	private String DB_CR_FLAG;
	private String STATUS;
	private String FILENAME;

}
