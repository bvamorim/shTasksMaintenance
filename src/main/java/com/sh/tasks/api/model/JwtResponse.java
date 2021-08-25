package com.sh.tasks.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h1>JwtResponse</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@NoArgsConstructor
@Getter
@Setter
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091624046844L;
	private String jwttoken = "";

	public JwtResponse(String token) {
		this.jwttoken = token;
	}
	
}