package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Catagory;
import com.example.demo.service.CatagoryService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/catagory")
public class CatagoryController {
	
	@Autowired
	private CatagoryService catagoryService;
	
	@PostMapping("/save-catagory")
	public ResponseEntity<?> saveCatagory(@RequestBody Catagory catagory)
	{
		boolean saveCatagory = catagoryService.saveCatagory(catagory);
		if (saveCatagory) {
			return new ResponseEntity<>("saved successfully",HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/catagory")
	public ResponseEntity<?> getAllCatagory()
	{
		List<Catagory> allCatagory = catagoryService.getAllCatagory();
		
		if (CollectionUtils.isEmpty(allCatagory)) {
			return ResponseEntity.noContent().build();
		}else {
			return new ResponseEntity<>(allCatagory,HttpStatus.OK); 
		}
		
	}
}
