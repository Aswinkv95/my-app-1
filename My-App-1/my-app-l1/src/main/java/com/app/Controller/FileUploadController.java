package com.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.Log.LogWritter;
import com.app.Service.FileUploadService;

@RestController
@RequestMapping("/BANK")
public class FileUploadController {
	
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private LogWritter log;
	
	@PostMapping(value="/file-upload")
	 public ResponseEntity<String> doUploadFile(@RequestParam("network") String network,
			                                    @RequestParam("file") MultipartFile file){
		 String status="";
		 try {
			
			 status= fileUploadService.fileUploading(network,file);
			 log.logInfo("File Uploaded "+status);
		 }catch (Exception e) {
			 log.logError("File Uploaded "+status,e);
			  return new ResponseEntity<>("Upload failed", HttpStatus.BAD_REQUEST);
		}
		 
		 return new ResponseEntity<String>(status,HttpStatus.OK);
	 }

}
