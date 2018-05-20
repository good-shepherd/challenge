package com.midasit.challenge.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class ImageService {

	 @Value("${app.jwtSecret}")
	 private String imagePath;
	
	public void store(MultipartFile file) {
		// TODO Auto-generated method stub
		
	}

}
