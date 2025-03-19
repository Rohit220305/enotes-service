package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CatagoryDto;
import com.example.demo.dto.CatagoryRespo;
import com.example.demo.entity.Catagory;

public interface CatagoryService {

	public boolean saveCatagory(CatagoryDto catagoryDto);
	
	public List<CatagoryDto> getAllCatagory();

	public List<CatagoryRespo> getActiveCatagory();
	
}
