package com.sh.tasks.api.services;


import java.util.List;

import com.sh.tasks.api.model.Task;
import com.sh.tasks.api.model.User;

/**
 * <h1>ITaskService</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
public interface ITaskService {

	Task createTask(Task task);
	
    void assignTaskToUser(Task task, User user);

    Task updateTask(Long id, Task task);

    void deleteTask(Long id);

    List<Task> findAll();

    List<Task> findByOwnerOrderByDateCreatedDesc(User user);

    Task setTaskPerformed(Long id);

    Task getTaskById(Long taskId);

}
