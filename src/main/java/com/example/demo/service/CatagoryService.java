package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Catagory;

public interface CatagoryService {

	public boolean saveCatagory(Catagory catagory);
	
	public List<Catagory> getAllCatagory();
	
}
