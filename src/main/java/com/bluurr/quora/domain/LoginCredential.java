package com.bluurr.quora.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Holds login credential information for logging into the Quora platform.
 * 
 * @author Bluurr
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginCredential {
	private String username;
	private String password;
}