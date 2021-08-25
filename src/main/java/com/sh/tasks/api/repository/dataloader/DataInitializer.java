package com.sh.tasks.api.repository.dataloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sh.tasks.api.model.Role;
import com.sh.tasks.api.model.User;
import com.sh.tasks.api.services.IRoleService;
import com.sh.tasks.api.services.IUserService;

/**
 * <h1>DataInitializer</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Component
public class DataInitializer {
	
    private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public void createRoles() {

		//Create Role Manager
		Role tempRole = new Role();
		tempRole.setId(new Long(1));
		tempRole.setRole("MANAGER");
        roleService.createRole(tempRole);

		//Create Role Technician
		tempRole.setId(new Long(2));
        tempRole.setRole("TECHNICIAN");
        roleService.createRole(tempRole);
        
		//Log all roles created
        roleService.findAll().stream().map(role -> "saved role: " + role.getRole()).forEach(logger::info);
        
	}	
	
	public void createUsers() {
		
		//Create user Manager
		User newManager = new User();
		newManager.setEmail("manager@sh.com");
		newManager.setUsername("manager");
		newManager.setPassword((bcryptEncoder.encode("manager2021")));
		
		newManager = userService.createUser(newManager);
		userService.changeRoleToManager(newManager);

		//Create user Technician		
		User newTech = new User();
		newTech.setEmail("technician@sh.com");
		newTech.setUsername("technician");
		newTech.setPassword((bcryptEncoder.encode("tech2021")));
		
		newTech = userService.createUser(newTech);
		userService.changeRoleToTechnician(newTech);
		
		//Create user Technician - John		
		User newTechJohn = new User();
		newTechJohn.setEmail("johntechnician@sh.com");
		newTechJohn.setUsername("john");
		newTechJohn.setPassword((bcryptEncoder.encode("john2021")));
		
		newTechJohn = userService.createUser(newTechJohn);
		userService.changeRoleToTechnician(newTechJohn);

		//Log all users created
        userService.findAll().stream().map(user -> user.getUsername()).forEach(logger::info);
	}
}