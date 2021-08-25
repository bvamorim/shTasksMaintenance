package com.sh.tasks.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h1>NotAuthorizedException</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-24
 */
@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="User not autorized")
public class NotAuthorizedException extends IllegalArgumentException  {

	private static final long serialVersionUID = -8612834030341785417L;

	public NotAuthorizedException(String msg) {
        super(msg);
    }

}
