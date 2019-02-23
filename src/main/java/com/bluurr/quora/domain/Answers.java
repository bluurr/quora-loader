package com.bluurr.quora.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Bluurr
 *
 */
@AllArgsConstructor(staticName = "limit")
@NoArgsConstructor
@Data
public class Answers {
	private int limit;
}
