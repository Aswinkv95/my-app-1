package com.app.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Model.Link;
import com.app.Repository.LinkRepository;

@Service
public class LinkService {
	
	 @Autowired
	    private LinkRepository linkRepository;

	    public List<Link> getLinkTree() {
	        List<Link> allLinks = linkRepository.findByIsActiveTrueOrderByCreatedAt();

	        // Map by ID for quick lookup
	        Map<UUID, Link> linkMap = allLinks.stream()
	                .collect(Collectors.toMap(Link::getId, Function.identity()));

	        List<Link> roots = new ArrayList<>();

	        for (Link link : allLinks) {
	            if (link.getParentId() == null) {
	                roots.add(link); // top-level link
	            } else {
	                Link parent = linkMap.get(link.getParentId());
	                if (parent != null) {
	                    parent.getChildren().add(link); // assign child
	                }
	            }
	        }

	        return roots;
	    }

}
