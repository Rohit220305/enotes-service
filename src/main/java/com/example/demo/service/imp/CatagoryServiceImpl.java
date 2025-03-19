package com.example.demo.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.demo.entity.Catagory;
import com.example.demo.repository.CatagoryRepository;	
import com.example.demo.service.CatagoryService;


@Service
public class CatagoryServiceImpl implements CatagoryService{
	 
	@Autowired
	private CatagoryRepository catagoryRepo;
	
	@Override
	public boolean saveCatagory(Catagory catagory) {
		catagory.setDeleted(false);
		catagory.setCreatedBy(1);
		catagory.setCreatedOn(new Date());
				
		Catagory saveCatagory = catagoryRepo.save(catagory);
		
		if (ObjectUtils.isEmpty(saveCatagory)) {
			return false;
		}
		return true;
	}

	@Override
	public List<Catagory> getAllCatagory() {
		List<Catagory> catagories = catagoryRepo.findAll();
		
		return catagories;
	}

}
