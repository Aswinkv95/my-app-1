package com.app.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="VisaCbs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisaCbs {
	
	@Id
	private String RECON_ID;
	private String TRANSACTION_TYPE;
	private LocalDate TRANSACTION_DATE;
	@Column(name = "TRANSACTION_AMOUNT", precision = 15, scale = 2)
	private BigDecimal TRASSACTION_AMOUNT;
	private String DB_CR_FLAG;
	private String STATUS;
	private String FILENAME;

}
