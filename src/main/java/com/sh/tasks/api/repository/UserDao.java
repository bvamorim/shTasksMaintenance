package com.sh.tasks.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sh.tasks.api.model.Role;
import com.sh.tasks.api.model.User;

/**
 * <h1>UserDao</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);
    User findByEmail(String email);
    List<User> findByRoleNot(Role role);

}