package com.app.Service;


import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.Model.VisaCbs;
import com.app.Repository.VisaRepository;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


@Service
public class VisaService {

	@Autowired
	private VisaRepository visaRepository;
	public List<VisaCbs> findexceptionWith(LocalDate fromDate, LocalDate toDate) {
		System.out.println(fromDate+"---------------"+toDate);
		return visaRepository.findByExceptionDateBetween(fromDate, toDate);
	}
	public byte[] generateZip(String fromDate, String toDate, List<String> list, Path excelPath, Path zipPath) {
		
		try {
		 // Define file paths
			
        int rowCount=1;
        // 1️⃣ Create Excel file
        ObjectMapper mapper = new ObjectMapper();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Visa Exceptions");

            // Headers
            rowCount++;
            Row header= sheet.createRow(rowCount);
            Map<String, Object> firstRecord = mapper.readValue(list.get(0),
       	                                      new TypeReference<Map<String, Object>>() {});
            List<String> headers = new ArrayList<>(firstRecord.keySet());
            
            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 10));
            header.createCell(0).setCellValue("VISA EXCEPTION FROM "+fromDate+" TO "+toDate);
            
            rowCount++;
            header = sheet.createRow(rowCount);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers.get(i));
            }
           
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> record = mapper.readValue(list.get(i), 
                		                         new TypeReference<Map<String, Object>>() {});
                rowCount++;
                header= sheet.createRow(rowCount);

                for (int j = 0; j < headers.size(); j++) {
                    Object value = record.get(headers.get(j));
                    header.createCell(j).setCellValue(value != null ? value.toString() : "");
                }
            }
           
           
            // Write Excel to temp file
            try (FileOutputStream fos = new FileOutputStream(excelPath.toFile())) {
                workbook.write(fos);
            }
        }

        // 2️⃣ Create ZIP containing Excel
        try (FileOutputStream fos = new FileOutputStream(zipPath.toFile());
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            ZipEntry zipEntry = new ZipEntry(excelPath.getFileName().toString());
            zos.putNextEntry(zipEntry);
            byte[] bytes = Files.readAllBytes(excelPath);
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
        }

        // 3️⃣ Read ZIP and return
        byte[] zipBytes = Files.readAllBytes(zipPath);

        return zipBytes;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
    
	
	}
	
	

}
