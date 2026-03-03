package com.app.Controller;

import java.net.http.HttpClient;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Model.Link;
import com.app.Service.LinkService;

@RestController
@RequestMapping("/BANK")
public class LinkController {
	
	  @Autowired
	    private LinkService linkService;

	    @GetMapping("/link")
	    public List<Link> getAllLinks() {
	    	
	    	List<Link> menuList= linkService.getLinkTree();
	        return menuList;
	    }

}
