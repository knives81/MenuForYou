package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rest.menuforyou.service.UploadService;

@RestController
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@RequestMapping(value = "/uploadRestaurantImage", method = RequestMethod.POST)
	public String uploadRestaurantImage(@RequestParam MultipartFile file, @RequestParam long id) {
		return uploadService.saveImage(file, id, "restaurant");
	}

	@RequestMapping(value = "/uploadDishImage", method = RequestMethod.POST)
	public String uploadDishImage(@RequestParam MultipartFile file, @RequestParam long id) {
		return uploadService.saveImage(file, id, "dish");
	}

}
