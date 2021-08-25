package com.sh.tasks.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sh.tasks.api.model.Role;

/**
 * <h1>RoleDao</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

    Role findByRole(String user);

}