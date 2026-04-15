package com.app.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.DTO.VisaCbsDTO;
import com.app.Model.VisaCbs;
import com.app.Repository.VisaFileUploadRepository;

@Service
public class FileUploadService {
	
	@Autowired
	private VisaFileUploadRepository fileUploadRepository;
	/*
	@Transactional
	public String fileUploading(String network, MultipartFile file) {
		
		VisaCbs visaCbs = new VisaCbs();
		int rowCount=0;
		System.out.println("Inside Service");
		try(InputStream in = file.getInputStream();
		    Workbook workbook = new XSSFWorkbook(in) ) {
			
			 Sheet sheet = workbook.getSheetAt(0);
			 for(Row row : sheet) {
				    if(rowCount>0) {
				// for(int j =0 ;j<row.getLastCellNum();j++) {
					VisaCbs list = new VisaCbs();
					list.setRECON_ID(row.getCell(0).toString());
					list.setTRANSACTION_TYPE(row.getCell(1).toString());
					list.setTRANSACTION_DATE(LocalDate.parse(row.getCell(2).toString()));
					list.setDB_CR_FLAG(row.getCell(3).toString());
					list.setSTATUS(row.getCell(5).toString());
					list.setFILENAME(file.getOriginalFilename());
					list.setTRASSACTION_AMOUNT( new BigDecimal( row.getCell(4).toString()));
					fileUploadRepository.save(list);
					System.out.println(row.getCell(3).toString());
				    }
				    rowCount++;
				 }
			 }catch (Exception e) {
				 e.printStackTrace();
			return "FAILED";
		}
		return "SUCCESS";
	}
	*/
	@Transactional
	public String fileUploading(String network, MultipartFile file) {

	    int rowCount = 0;
	    System.out.println("Inside Service");
	    try (InputStream in = file.getInputStream();
	         Workbook workbook = new XSSFWorkbook(in)) {
	        Sheet sheet = workbook.getSheetAt(0);
	        for (Row row : sheet) {
	            if (rowCount == 0) { // skip header
	                rowCount++;
	                continue;
	            }
	            VisaCbs list = new VisaCbs();
	            // SAFE cell reading
	            list.setRECON_ID(getCellString(row, 0));
	            list.setTRANSACTION_TYPE(getCellString(row, 1));
	            list.setDB_CR_FLAG(getCellString(row, 3));
	            list.setSTATUS(getCellString(row, 4));
	            list.setFILENAME(file.getOriginalFilename());
	            // DATE handling
	            list.setTRANSACTION_DATE(getCellDate(row, 2));
	            // AMOUNT handling
	            list.setTRASSACTION_AMOUNT(getCellDecimal(row, 5));
	            fileUploadRepository.save(list);
	            rowCount++;
	        }

	    } catch (Exception e) {
	        e.printStackTrace(); // 🔥 always log
	        return "FAILED";
	    }

	    return "SUCCESS";
	}
	private String getCellString(Row row, int index) {
	    Cell cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	    return cell.toString().trim();
	}
	private LocalDate getCellDate(Row row, int index) {
	    Cell cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

	    if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
	        return cell.getLocalDateTimeCellValue().toLocalDate();
	    }

	    return null; // or throw error
	}
	
	private BigDecimal getCellDecimal(Row row, int index) {
	    Cell cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

	    if (cell.getCellType() == CellType.NUMERIC) {
	        return BigDecimal.valueOf(cell.getNumericCellValue());
	    }

	    try {
	        return new BigDecimal(cell.toString());
	    } catch (Exception e) {
	        return new BigDecimal(cell.toString());
	    }
	}

}
