package com.sh.tasks.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h1>JwtRequest</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@NoArgsConstructor
@Getter
@Setter
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583905150707L;

	private String username;
	private String password;

	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

}