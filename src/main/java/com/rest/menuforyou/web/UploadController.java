package com.rest.menuforyou.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rest.menuforyou.response.JsonOk;
import com.rest.menuforyou.service.UploadService;

@Controller
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@RequestMapping(value = "/uploadRestaurantImage", method = RequestMethod.POST)
	public @ResponseBody JsonOk uploadRestaurantImage(@RequestParam MultipartFile file, @RequestParam long id) {
		String imageUrl = uploadService.saveImage(file, id, "restaurant");
		return new JsonOk(imageUrl);
	}

	@RequestMapping(value = "/uploadDishImage", method = RequestMethod.POST)
	public @ResponseBody JsonOk uploadDishImage(@RequestParam MultipartFile file, @RequestParam long id) {
		String imageUrl = uploadService.saveImage(file, id, "dish");
		return new JsonOk(imageUrl);
	}

}
