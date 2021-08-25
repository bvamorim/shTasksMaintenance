package com.sh.tasks.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * <h1>Role</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Entity
@Table(name = "role")
@Data
public class Role implements Serializable {

	private static final long serialVersionUID = 7454993339462019797L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    
    @Column(name = "role")
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<User> users;


}
