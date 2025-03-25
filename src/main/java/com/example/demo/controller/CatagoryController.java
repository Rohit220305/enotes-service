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
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.CatagoryService;
import com.example.demo.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@RequestMapping("/api/v1/catagory")
public class CatagoryController {
	
	@Autowired
	private CatagoryService catagoryService;
	private Object log;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveCatagory(@RequestBody CatagoryDto catagoryDto)
	{
		boolean saveCatagory = catagoryService.saveCatagory(catagoryDto);
		if (saveCatagory) {
			return CommonUtil.createBuildResponseMessage("saved success", HttpStatus.CREATED);
//			return new ResponseEntity<>("saved successfully",HttpStatus.CREATED);
		}else {
			return CommonUtil.createErroeResponseMessage("category not saved", HttpStatus.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllCatagory()
	{

		List<CatagoryDto> allCatagory = catagoryService.getAllCatagory();
		
		if (CollectionUtils.isEmpty(allCatagory)) {
			return ResponseEntity.noContent().build();
		}else {
			return CommonUtil.createBuildResponse(allCatagory,HttpStatus.OK );
//			return new ResponseEntity<>(allCatagory,HttpStatus.OK); 
		}
		
	}
	
	@GetMapping("/active")
	public ResponseEntity<?> getActiveCatagory()
	{
		List<CatagoryRespo> allCatagory = catagoryService.getActiveCatagory ();
		
		if (CollectionUtils.isEmpty(allCatagory)) {
			return ResponseEntity.noContent().build();
		}else {
//			return new ResponseEntity<>(allCatagory,HttpStatus.OK); 
			return CommonUtil.createBuildResponse(allCatagory,HttpStatus.OK );
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCatagoryDetailsById(@PathVariable Integer id) throws Exception
	{
		
		
		CatagoryDto catagoryDto = catagoryService.getCatagoryById(id);
		if(ObjectUtils.isEmpty(catagoryDto))
		{
//			return CommonUtil.createErroeResponseMessage("Internal server error",HttpStatus.NOT_FOUND);
			
			return CommonUtil.createErroeResponseMessage("Catagory not found",HttpStatus.NOT_FOUND);
			
//			return new ResponseEntity<>("Catagory not found",HttpStatus.NOT_FOUND);
		}
//		return new ResponseEntity<>(catagoryDto,HttpStatus.OK);
		return CommonUtil.createBuildResponse(catagoryDto,HttpStatus.OK );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCatagoryDetailsById(@PathVariable Integer id)
	{
		Boolean deleted = catagoryService.deleteCatagoryById(id);
		if(deleted)
		{
			return CommonUtil.createBuildResponse("Catagory deleted successfully",HttpStatus.OK );
//			return new ResponseEntity<>("Catagory deleted success",HttpStatus.OK);
		}
		else {
			return CommonUtil.createErroeResponseMessage("Catagory not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>("Catagory not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
