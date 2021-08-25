package com.sh.tasks.api.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sh.tasks.api.model.component.AttributeEncryptor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * <h1>Task</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Entity
@Table(name = "task")
@NoArgsConstructor
@Data
public class Task implements Serializable {

	private static final long serialVersionUID = 176410632291188896L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
	private Long id;

	@NonNull
	@Column
    @NotEmpty(message = "Task name cannot be empty}")
	private String name;

    @NotEmpty(message = "Task description cannot be empty}")
    @Column(length = 2500)
    @Size(max = 2500, message = "Task description max size is 2500 characters")
    @Convert(converter = AttributeEncryptor.class)
    private String description;

    @NotNull(message = "Task date cannot be null}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@NonNull
	@Column(name = "dateCreated")
	private LocalDate dateCreated = LocalDate.now();
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "datePerformed")
	private LocalDate datePerformed;
    
    private boolean isPerformed;
	
    private String creatorName;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private User owner;

}
