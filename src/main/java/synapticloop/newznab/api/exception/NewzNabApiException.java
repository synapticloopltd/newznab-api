package synapticloop.newznab.api.exception;

/*
 * Copyright (c) 2016 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

public class NewzNabApiException extends Exception {
	private static final long serialVersionUID = 4378654574290599119L;

	/**
	 * Instantiate a new NewzNab API Exception
	 */
	public NewzNabApiException() {
		super();
	}

	/**
	 * Instantiate a new NewzNab API Exception
	 * 
	 * @param message the exception message
	 */
	public NewzNabApiException(String message) {
		super(message);
	}

	/**
	 * Instantiate a new NewzNab API Exception
	 * 
	 * @param cause the root cause
	 */
	public NewzNabApiException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiate a new NewzNab API Exception
	 * 
	 * @param message the exception message
	 * @param cause the root cause
	 */
	public NewzNabApiException(String message, Throwable cause) {
		super(message, cause);
	}
}
