package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CatagoryDto;
import com.example.demo.dto.CatagoryRespo;
import com.example.demo.service.CatagoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/catagory")
public class CatagoryController {
	
	@Autowired
	private CatagoryService catagoryService;
	
	@PostMapping("/save-catagory")
	public ResponseEntity<?> saveCatagory(@RequestBody CatagoryDto catagoryDto)
	{
		boolean saveCatagory = catagoryService.saveCatagory(catagoryDto);
		if (saveCatagory) {
			return new ResponseEntity<>("saved successfully",HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllCatagory()
	{
		List<CatagoryDto> allCatagory = catagoryService.getAllCatagory();
		
		if (CollectionUtils.isEmpty(allCatagory)) {
			return ResponseEntity.noContent().build();
		}else {
			return new ResponseEntity<>(allCatagory,HttpStatus.OK); 
		}
		
	}
	
	@GetMapping("/active")
	public ResponseEntity<?> getActiveCatagory()
	{
		List<CatagoryRespo> allCatagory = catagoryService.getActiveCatagory ();
		
		if (CollectionUtils.isEmpty(allCatagory)) {
			return ResponseEntity.noContent().build();
		}else {
			return new ResponseEntity<>(allCatagory,HttpStatus.OK); 
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCatagoryDetailsById(@PathVariable Integer id)
	{
		CatagoryDto catagoryDto = catagoryService.getCatagoryById(id);
		if(ObjectUtils.isEmpty(catagoryDto))
		{
			return new ResponseEntity<>("Catagory not found with id="+id,HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(catagoryDto,HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCatagoryDetailsById(@PathVariable Integer id)
	{
		Boolean deleted = catagoryService.deleteCatagoryById(id);
		if(deleted)
		{
			return new ResponseEntity<>("Catagory deleted success",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Catagory not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
