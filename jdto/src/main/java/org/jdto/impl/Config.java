/**
 * UNCLASSIFIED
 * Copyright © 2014 Defence R&D Canada - Valcartier. All rights reserved.
 */
package org.jdto.impl;

/**
 * @author Thales, under contract W7701-125076/001/QCL *
 *
 */
public final class Config {

	private static final String IGNORE_RUNTIME_EXCEPTION_PARAMETER = "jdto.ignore.runtime.exception";
	
	private static Boolean ignoreRuntimeException;
	
	public static boolean isIgnoreRuntimeException() {
		if( ignoreRuntimeException == null ) {
			ignoreRuntimeException = Boolean.valueOf(System.getProperty(IGNORE_RUNTIME_EXCEPTION_PARAMETER, Boolean.toString(Boolean.TRUE)));
		}
		
		return ignoreRuntimeException;
	}
}
