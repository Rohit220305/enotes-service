package com.example.demo.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.example.demo.dto.CatagoryDto;
import com.example.demo.exception.ValidationEcxception;

@Component
public class Validation {

	public void catagoryValidation(CatagoryDto catagoryDto)
	{
		Map<String, Object> error = new LinkedHashMap<>();
		
		if (ObjectUtils.isEmpty(catagoryDto)) {
			throw new IllegalArgumentException("Catagory object/JSON shoulden't be null or empty");
		}
		else {
			
			// validation name field
			if (ObjectUtils.isEmpty(catagoryDto.getName())) {
				error.put("name:", "name feild is empty or null");
			}else {
				if(catagoryDto.getName().length()<10)
				{
					error.put("name:", "name lenght min 10");
				}
				if(catagoryDto.getName().length()>100)
				{
					error.put("name:", "name lenght max 100");
				}
			}
			
			// validation description
			if (ObjectUtils.isEmpty(catagoryDto.getDescription())) {
				error.put("description:", "Description feild is empty or null");
			}
			
			// validation isActive
			if (ObjectUtils.isEmpty(catagoryDto.getIsActive())) {
				error.put("isActive:", "isActive feild is empty or null");
			}else {
				if( catagoryDto.getIsActive() != Boolean.TRUE.booleanValue() && catagoryDto.getIsActive() != Boolean.FALSE.booleanValue() )
				{
					error.put("isActive", "invalid value isActive field");
				}
			}
			
		}
		
		if (!error.isEmpty()) {
			throw new ValidationEcxception(error) ;
		}
		
	}
	
}
