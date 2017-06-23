package kr.or.connect.todo.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.service.TodoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/todo")
public class TodoController {

	private final TodoService service;
	private final Logger log = LoggerFactory.getLogger(TodoController.class);
	
	@Autowired
	public TodoController(TodoService service) {
		this.service = service;
	}
	
	@GetMapping
	Collection<Todo> readList() {
		return service.selectAll();
	}
	
	@GetMapping("/{id}")
	Todo read(@PathVariable Integer id) {
		return service.findById(id);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Todo create(@RequestBody Todo todo) {
		Todo newTodo = service.create(todo);
		log.info("todo create : {}", newTodo);
		return todo;
	}
	
		
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable Integer id){
		service.delete(id);
	}
	
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void update(@PathVariable Integer id, @RequestBody Todo todo) {
		todo.setId(id);
		service.update(todo);
	}
	
	@DeleteMapping("/completedDelete")
	public void completedDataDelete() {
		service.completedDelete();
	}
}

