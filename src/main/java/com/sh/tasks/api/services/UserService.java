package com.sh.tasks.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.tasks.api.model.Role;
import com.sh.tasks.api.model.User;
import com.sh.tasks.api.repository.RoleDao;
import com.sh.tasks.api.repository.TaskDao;
import com.sh.tasks.api.repository.UserDao;

/**
 * <h1>UserService</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Service
public class UserService implements IUserService {
	
    private static final String MANAGER="MANAGER";
    private static final String TECHNICIAN="TECHNICIAN";
    
    private UserDao userDao;
    private TaskDao taskDao;
    private RoleDao roleDao;

	@Autowired
    public UserService(UserDao userDao,
    				   TaskDao taskDao,
    				   RoleDao roleDao) {
		this.userDao = userDao;
		this.taskDao = taskDao;
		this.roleDao = roleDao;
	}

	@Override
	@Transactional(readOnly = true)
	public User getLoggedUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		User user = userDao.findByUsername(username);
		return user;
	}
	
    @Override
    public User createUser(User user) {
        user.setPassword(user.getPassword());
        Role userRole = roleDao.findByRole(TECHNICIAN);
        user.setRole(userRole);
        return userDao.save(user);
    }

    @Override
    public User changeRoleToManager(User user) {
        Role adminRole = roleDao.findByRole(MANAGER);
        user.setRole(adminRole);
        return userDao.save(user);
    }
    
    @Override
    public User changeRoleToTechnician(User user) {
        Role adminRole = roleDao.findByRole(TECHNICIAN);
        user.setRole(adminRole);
        return userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserEmailPresent(String email) {
        return userDao.findByEmail(email) != null;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userDao.getOne(id);
        user.getTasksOwned().forEach(task -> taskDao.delete(task));
        userDao.delete(user);
    }
    

    @Override
    @Transactional(readOnly = true)
    public List<User> findByUserRoleNot(Role role) {
        return userDao.findByRoleNot(role);
    }

}
