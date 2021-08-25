package com.sh.tasks.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sh.tasks.api.exception.NotAuthorizedException;
import com.sh.tasks.api.exception.TaskNotExistException;
import com.sh.tasks.api.model.Task;
import com.sh.tasks.api.model.User;
import com.sh.tasks.api.services.ITaskService;
import com.sh.tasks.api.services.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <h1>TaskController</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-23
 */
@RestController
@RequestMapping("/task")
@Api(tags = { "Task API" })
public class TaskController {

	@Autowired
	private ITaskService iTaskService;
	
	@Autowired
	private IUserService iUserService;

	/**
	 * Method to save a task
	 * 
	 * @param task
	 * @return task
	 */
	@ApiOperation(value = "Add a new Task (Technician)")
	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	public Task createTask(@Valid @RequestBody Task task) {
				
		Task savedTask = null;
		
		if(!iUserService.getLoggedUser().isManager()){
			task.setCreatorName(iUserService.getLoggedUser().getUsername());
			savedTask = iTaskService.createTask(task);
			iTaskService.assignTaskToUser(savedTask, iUserService.getLoggedUser());
		} else 
			throw new NotAuthorizedException(iUserService.getLoggedUser().getUsername());
		
		return savedTask;
	}

	/**
	 * Method for update a task by id
	 * 
	 * @param Task
	 * @param id
	 * @return void
	 */
	@ApiOperation(value = "Update an existing Task found by ID (Technician)")
	@PatchMapping("/update/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Task updateTask(@RequestBody Task task, @PathVariable Long id) {		

		Task updatedTask = null;
		Task taskVerify = iTaskService.getTaskById(id);
		if(taskVerify == null) throw new TaskNotExistException(id.toString());
		
		boolean isOwner = taskVerify.getOwner().equals(iUserService.getLoggedUser());
		
		if(isOwner) {
			updatedTask = iTaskService.updateTask(id, task);
		} else 
			throw new NotAuthorizedException(iUserService.getLoggedUser().getUsername());
		
		return updatedTask;

	}
	
    
	/**
	 * Method for update a task status(performed) by id
	 * 
	 * @param id
	 * @return void
	 */
	@ApiOperation(value = "Update an existing Task found by ID to performed (Technician)")
	@PatchMapping("/setTaskPerformed/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Task setTaskPerformed(@PathVariable Long id) {

		Task taskUpdated = null; 
		Task taskVerify = iTaskService.getTaskById(id);
		if(taskVerify == null) throw new TaskNotExistException(id.toString());
		
		boolean isOwner = taskVerify.getOwner().equals(iUserService.getLoggedUser());
		
		if(isOwner) {
			taskUpdated = iTaskService.setTaskPerformed(id);
		} else 
			throw new NotAuthorizedException(iUserService.getLoggedUser().getUsername());
		
		return taskUpdated;
	}


	/**
	 * Method of listing all tasks by user
	 * 
	 * @return iUserService.findByUserRoleNot()
	 */
	@ApiOperation(value = "Returns a List of all existing tasks (Manager) ")
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public List<User> listAll() {
		
		List<User> list = null;

		if(iUserService.getLoggedUser().isManager()){
			
			list = iUserService.findByUserRoleNot(iUserService.getLoggedUser().getRole());
			if(list == null) throw new TaskNotExistException("listAll");
			
		} else 
			throw new NotAuthorizedException(iUserService.getLoggedUser().getUsername());
		
		return list;
	}
	
	/**
	 * Method of listing all logged user tasks
	 * 
	 * @return iTaskService.findByOwnerOrderByDateCreatedDesc()
	 */
	@ApiOperation(value = "Returns a List of logged user existing tasks (Technician)")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Task> list() {
		
		List<Task> list = null;

		if(!iUserService.getLoggedUser().isManager()){
			list = iTaskService.findByOwnerOrderByDateCreatedDesc(iUserService.getLoggedUser());
		} else 
			throw new NotAuthorizedException(iUserService.getLoggedUser().getUsername());
		
		return list;
	}
    
	/**
	 * Method for delete a task by id
	 * 
	 * @param id
	 */
	@ApiOperation(value = "Deletes an existing Task found by ID (Manager)")
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable Long id) {
		
		if(iUserService.getLoggedUser().isManager()){
			
			Task task = iTaskService.getTaskById(id);
			if(task == null) throw new TaskNotExistException(id.toString());
			
			iTaskService.deleteTask(id);
			
		} else 
			throw new NotAuthorizedException(iUserService.getLoggedUser().getUsername());

	}

	
	/**
	 * Method to search for a task with id
	 * 
	 * @param id
	 * @return task
	 * @throws Exception
	 */
	@ApiOperation(value = "Returns a Task found by ID (Technician or Manager)")
	@GetMapping("/find/{id}")
	public Task searchTaskById(@PathVariable Long id) {

		Task task = iTaskService.getTaskById(id);
		if(task == null) throw new TaskNotExistException(id.toString());
		
		boolean isOwner = task.getOwner().equals(iUserService.getLoggedUser());

		if(!iUserService.getLoggedUser().isManager() && !isOwner){
			throw new NotAuthorizedException(iUserService.getLoggedUser().getUsername());
		}

		return task;
	}

}
