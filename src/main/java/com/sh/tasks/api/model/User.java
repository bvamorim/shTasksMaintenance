package com.sh.tasks.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * <h1>User</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Entity
@Table(name = "user")
@Data
public class User implements Serializable {

	private static final long serialVersionUID = 5600480143011308925L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
	private Long id;

	@Column(unique = true)
	private String username;

    @Email(message = "User email is not valid")
    @NotEmpty(message = "User email cannot be empty")
    @Column(unique = true)
    private String email;
	
	@Column
	@JsonIgnore
	private String password;
	
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private List<Task> tasksOwned;
    
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;
    
	@JsonIgnore
    public List<Task> getTasksPerformed() {
        return tasksOwned.stream()
                .filter(Task::isPerformed)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Task> getTasksStillInProgress() {
        return tasksOwned.stream()
                .filter(task -> !task.isPerformed())
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public boolean isManager() {
        String roleName = "MANAGER";
        return role.getRole().equals(roleName);
    }

	
}