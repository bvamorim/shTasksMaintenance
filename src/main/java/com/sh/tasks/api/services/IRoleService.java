package com.sh.tasks.api.services;

import java.util.List;

import com.sh.tasks.api.model.Role;

/**
 * <h1>IRoleService</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
public interface IRoleService {
	
    Role createRole(Role role);

    List<Role> findAll();
}
