package com.app.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.UserDTO;
import com.app.DTO.VisaCbsDTO;
import com.app.Log.LogWritter;
import com.app.Model.Users;
import com.app.Model.VisaCbs;
import com.app.Service.FileUploadService;
import com.app.Service.VisaService;

@RestController
@RequestMapping("/BANK")
public class VisaController {

	@Autowired
	private VisaService visaService;
	@Autowired
	private LogWritter log;

	@PostMapping("/visa-exception")
	public ResponseEntity<List<VisaCbsDTO>> getVisaException(@DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fromDate,
			@DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate toDate) {
		try {
			System.out.println(fromDate + "INSIDE __________________________________________" + toDate);
			List<VisaCbs> list = visaService.findexceptionWith(fromDate, toDate);
			List<VisaCbsDTO> dtoList = list.stream().map(entity -> {
				VisaCbsDTO visaCbsDTO = new VisaCbsDTO();
				visaCbsDTO.setTRANSACTION_DATE(entity.getTRANSACTION_DATE());
				visaCbsDTO.setTRASSACTION_AMOUNT(entity.getTRASSACTION_AMOUNT());
				visaCbsDTO.setTRANSACTION_TYPE(entity.getTRANSACTION_TYPE());
				visaCbsDTO.setSTATUS(entity.getSTATUS());
				visaCbsDTO.setDB_CR_FLAG(entity.getDB_CR_FLAG());
				visaCbsDTO.setFILENAME(entity.getFILENAME());

				return visaCbsDTO;
			}).toList();
			log.logInfo("Feteched Details Of Visa Cbs Exception");

			return new ResponseEntity<>(dtoList, HttpStatus.OK);
		} catch (Exception e) {
			log.logError("Error Came in Cbs Exception", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/visa-exception/export")
	public ResponseEntity<byte[]> exceptionDownload(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("records") List<String> records) {
		try {

			String excelFileName = "Visa-exception_" + fromDate.replace("-", "") + "_"
					+ toDate.replace("-", "")+".xlsx";
			
			
			
			String projectDir = System.getProperty("user.dir");
			String path = projectDir + "/FILES/REPORTS/";
			
			Path dirPath = Paths.get(path);
			if (!Files.exists(dirPath)) {
				Files.createDirectories(dirPath); // creates all missing parent dirs too
				
			}
			Path excelPath = Paths.get(path, excelFileName);
			
			Path zipPath = Paths.get(path, excelFileName.replace(".xlsx", ".zip"));


			Files.deleteIfExists(excelPath);
			Files.deleteIfExists(zipPath);
			byte[] zipBytes = visaService.generateZip(fromDate, toDate, records,excelPath,zipPath);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+zipPath+"\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(zipBytes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
