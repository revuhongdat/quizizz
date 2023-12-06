package com.example.quizizz.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResourceNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1649400644426728338L;

	private String status;
	private String message;
	private Object data;



}
