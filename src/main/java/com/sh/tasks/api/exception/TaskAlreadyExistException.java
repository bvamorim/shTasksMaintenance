package com.sh.tasks.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h1>TaskAlreadyExistException</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-24
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Task ID already exist")
public class TaskAlreadyExistException extends IllegalArgumentException  {

	private static final long serialVersionUID = -8612814730341712727L;

	public TaskAlreadyExistException(String msg) {
        super(msg);
    }

}
