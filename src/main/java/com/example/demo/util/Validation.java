package com.example.demo.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.example.demo.dto.CatagoryDto;
import com.example.demo.dto.TodoDto;
import com.example.demo.dto.TodoDto.StatusDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.enums.TodoStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationEcxception;
import com.example.demo.repository.RoleRepository;

@Component
public class Validation {
	
	@Autowired
	private RoleRepository roleRepo;
	
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
				if(catagoryDto.getName().length()<3)
				{
					error.put("name:", "name lenght min 3");
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
	
	public void todoValidation(TodoDto todo) throws Exception
	{
		StatusDto reqStatus = todo.getStatus();
		
		Boolean statusFound = false;
				
		for(TodoStatus st:TodoStatus.values())
		{
			if (st.getId().equals(reqStatus.getId())) {
				statusFound  = true;
			}
			
		}
		
		if(!statusFound) {
			throw new ResourceNotFoundException("Invalid Status");
		}
	}
	
	public void userValidation(UserDto userDto)
	{
		
		if(!StringUtils.hasText(userDto.getFirstName()))
		{
			throw new IllegalArgumentException("First name is Invalid !!");
		}
		
		if(!StringUtils.hasText(userDto.getLastName()))
		{
			throw new IllegalArgumentException("Last name is Invalid !!");
		}
		
		if(!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX))
		{
			throw new IllegalArgumentException("Email is Invalid !!");
		}
		
		if(!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constants.MOB_REGEX))
		{
			throw new IllegalArgumentException("Mobile No is Invalid !!");
		}
		
		
		if(CollectionUtils.isEmpty(userDto.getRoles()))
		{
			throw new IllegalArgumentException("role is Invalid !!");

		}else {
			List<Integer> roleIds = roleRepo.findAll().stream().map(r->r.getId()).toList();
			
			List<Integer> invalidRequestRoleIds = userDto.getRoles().stream().map(r->r.getId())
					.filter(roleId->!roleIds.contains(roleId)).toList();
			
			if (!CollectionUtils.isEmpty(invalidRequestRoleIds)) {
				throw new IllegalArgumentException("role is Invalid !!"+ invalidRequestRoleIds );

			}
		}
	}
	
}
