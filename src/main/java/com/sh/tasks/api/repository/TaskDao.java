package com.sh.tasks.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sh.tasks.api.model.Task;
import com.sh.tasks.api.model.User;

/**
 * <h1>TaskDao</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Repository
public interface TaskDao extends JpaRepository<Task, Long> {
	
    List<Task> findByOwnerOrderByDateCreatedDesc(User owner);

}