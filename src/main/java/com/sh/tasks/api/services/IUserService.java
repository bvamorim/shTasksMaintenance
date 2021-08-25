package com.sh.tasks.api.services;

import java.util.List;

import com.sh.tasks.api.model.Role;
import com.sh.tasks.api.model.User;

/**
 * <h1>IUserService</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
public interface IUserService {

	public User getLoggedUser();
	
    User createUser(User user);

    User changeRoleToManager(User user);
    
    User changeRoleToTechnician(User user);

    List<User> findAll();

    User getUserByEmail(String email);

    boolean isUserEmailPresent(String email);

    User getUserById(Long userId);

    void deleteUser(Long id);

	List<User> findByUserRoleNot(Role role);

}
