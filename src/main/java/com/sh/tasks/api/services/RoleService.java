package com.sh.tasks.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.tasks.api.model.Role;
import com.sh.tasks.api.repository.RoleDao;


/**
 * <h1>RoleService</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Service
public class RoleService implements IRoleService {
	
	@Autowired
    private RoleDao roleDao;

    @Override
    public Role createRole(Role role) {
        return roleDao.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleDao.findAll();
    }
    
}

