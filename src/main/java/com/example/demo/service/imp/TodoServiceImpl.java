package com.example.demo.service.imp;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.demo.dto.TodoDto;
import com.example.demo.dto.TodoDto.StatusDto;
import com.example.demo.entity.Todo;
import com.example.demo.enums.TodoStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TodoRepository;
import com.example.demo.service.TodoService;
import com.example.demo.util.Validation;

@Service
public class TodoServiceImpl implements TodoService{


	
	@Autowired
	private TodoRepository todoRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private Validation validation;
	 
	public Boolean saveTodo(TodoDto todoDto) throws Exception {
		// validate todo staus
		validation.todoValidation(todoDto);
		
		Todo todo = mapper.map(todoDto, Todo.class);
		todo.setStatusId(todoDto.getStatus().getId());
		Todo saveTodo = todoRepo.save(todo);
		if(!ObjectUtils.isEmpty(saveTodo)) {
			return true;
		}
		return false;
	}

	@Override
	public TodoDto getTodoById(Integer id) throws Exception {
		
		Todo todo = todoRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found ! Id invalid"));
		
		TodoDto todoDto = mapper.map(todo, TodoDto.class);
		setStatus(todoDto,todo);
		return todoDto;
	}

	private void setStatus(TodoDto todoDto, Todo todo) {
		
		for(TodoStatus st : TodoStatus.values()) {
			if (st.getId().equals(todo.getStatusId())) {
				StatusDto statusDto = StatusDto.builder()
						.id(st.getId())
						.name(st.getName())
						.build();
				todoDto.setStatus(statusDto);
			}
		}
		
	}

	@Override
	public List<TodoDto> getTodoByUser() {
		
		Integer userid = 2;
		
		List<Todo> todos = todoRepo.findByCreatedBy(userid);
		return todos.stream().map(td -> mapper.map(td, TodoDto.class)).toList();
		
	}

}
