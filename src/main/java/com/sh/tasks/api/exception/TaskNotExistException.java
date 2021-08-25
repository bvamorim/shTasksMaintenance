package com.sh.tasks.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h1>TaskNotExistException</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-24
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Task not Found")
public class TaskNotExistException extends IllegalArgumentException  {

	private static final long serialVersionUID = -8612834030341712727L;

	public TaskNotExistException(String msg) {
        super(msg);
    }

}
