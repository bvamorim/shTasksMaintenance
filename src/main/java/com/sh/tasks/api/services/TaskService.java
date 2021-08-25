package com.sh.tasks.api.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.tasks.api.model.Task;
import com.sh.tasks.api.model.User;
import com.sh.tasks.api.repository.TaskDao;

/**
 * <h1>TaskService</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Service
public class TaskService implements ITaskService {
    
	@Autowired
	private TaskDao taskDao;

    @Override
    public Task createTask(Task task) {
        return taskDao.save(task);
    }
    
    @Override
    public void assignTaskToUser(Task task, User user) {
        task.setOwner(user);
        taskDao.save(task);
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskDao.getOne(id);
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        return taskDao.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return taskDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByOwnerOrderByDateCreatedDesc(User user) {
        return taskDao.findByOwnerOrderByDateCreatedDesc(user);
    }

    @Override
    @Transactional
    public Task setTaskPerformed(Long id) {
        Task task = taskDao.getOne(id);
        task.setDatePerformed(LocalDate.now());
        task.setPerformed(true);
        asyncNotification(task);
		return taskDao.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskDao.findById(id).orElse(null);
    }
    
    @Async
    public void asyncNotification(Task task) {
        System.out.println("The tech "+task.getCreatorName()+" performed the task "+task.getId()+" on date "+task.getDatePerformed().toString());
    }

}
