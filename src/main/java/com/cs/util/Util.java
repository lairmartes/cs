//if it's util only for controller, it should be moved to there
package com.cs.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

	public Util() {
		super();
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
