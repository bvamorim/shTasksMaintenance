package com.sh.tasks.api.model.datatransferobject;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.sh.tasks.api.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>TaskDto</h1>
 *
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Getter
@Setter
@AllArgsConstructor
public class TaskDto {

	private @NotNull String name;
	private @NotNull String description;
	private @NotNull LocalDate dateCreated;
	private LocalDate datePerformed;
    private boolean isPerformed;
    private User owner;

}
