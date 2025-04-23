package com.example.demo.service.imp;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.Validation;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public boolean register(UserDto userDto) {
		
		validation.userValidation(userDto);
		
		User user = mapper.map(userDto, User.class);
		
		setRole(userDto,user);
		
		User saveUser = userRepo.save(user);
		if(!ObjectUtils.isEmpty(saveUser))
		{
			return true;
		}
		return false;
	}

	private void setRole(UserDto userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r->r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		
		user.setRoles(roles);
		
	}

}
