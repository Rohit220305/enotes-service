package com.example.demo.service.imp;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.demo.dto.CatagoryDto;
import com.example.demo.dto.CatagoryRespo;
import com.example.demo.entity.Catagory;
import com.example.demo.repository.CatagoryRepository;	
import com.example.demo.service.CatagoryService;


@Service
public class CatagoryServiceImpl implements CatagoryService{
	 
	@Autowired
	private CatagoryRepository catagoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public boolean saveCatagory(CatagoryDto catagoryDto) {
		
//		Catagory catagory = new Catagory();
//		catagory.setName(catagoryDto.getName());
//		catagory.setDescription(catagoryDto.getDescription());
//		catagory.setActive(catagoryDto.isActive());

		Catagory catagory = mapper.map(catagoryDto, Catagory.class);
		
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
	public List<CatagoryDto> getAllCatagory() {
		List<Catagory> catagories = catagoryRepo.findAll();
		
		List<CatagoryDto> catagoryDtoList = catagories.stream().map(cat -> mapper.map(cat, CatagoryDto.class)).toList();
		
		return catagoryDtoList;
	}

	
	@Override
	public List<CatagoryRespo> getActiveCatagory() {
		
		List<Catagory> catagories = catagoryRepo.findByIsActiveTrue();
		List<CatagoryRespo> catagoryList = catagories.stream().map(cat->mapper.map(cat, CatagoryRespo.class)).toList();
		
		return catagoryList;
	}
}
