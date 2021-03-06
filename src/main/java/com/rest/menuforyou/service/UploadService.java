package com.rest.menuforyou.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rest.menuforyou.domain.Dish;
import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.error.SaveException;
import com.rest.menuforyou.repository.DishRepository;
import com.rest.menuforyou.repository.RestaurantRepository;
import com.rest.menuforyou.util.Utils;

@Service
public class UploadService {

	@Autowired
	private RestaurantRepository restaurantRepo;

	@Autowired
	private DishRepository dishRepository;

	@Transactional(readOnly = false)
	public String saveImage(MultipartFile file, long id, String entity) {
		String imageDirectoryPath = "/Users/mauriziopinzi/Documents/";
		String filename = UUID.randomUUID().toString() + ".PNG";
		String imageUrl = imageDirectoryPath + filename;
		try {
			FileOutputStream fos = new FileOutputStream(imageUrl);
			fos.write(file.getBytes());
			fos.close();

		} catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException : " + ex);
		} catch (IOException ioe) {
			System.out.println("IOException : " + ioe);
		}

		try {
			if (entity.equals("dish")) {
				Dish dish = dishRepository.findOne(Long.valueOf(id));
				Utils.checkPermission(dish.getMenu());
				dish.setImageUrl(imageUrl);
				dishRepository.save(dish);
			} else if (entity.equals("restaurant")) {
				Restaurant restaurant = restaurantRepo.findOne(Long.valueOf(id));
				Utils.checkPermission(restaurant);
				restaurant.setImageUrl(imageUrl);
				restaurantRepo.save(restaurant);
			} else {
			}
		} catch (ConstraintViolationException e) {
			throw new SaveException("Error Image save", e);
		}
		return imageUrl;
	}
}
