/**
 * UNCLASSIFIED
 * Copyright © 2014 Defence R&D Canada - Valcartier. All rights reserved.
 */
package org.jdto.cdi;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

import org.jdto.TypeResolver;

/**
 * Annotation interface for DTOBinderProducer
 * 
 * @author Thales, under contract W7701-125076/001/QCL *
 */
@Qualifier
@Retention(RUNTIME)
@Target({ TYPE, METHOD, FIELD, PARAMETER })
public @interface DTOMappingNameQualifier {

	/**
	 * The value to bind (ex: jdto-inferencerules-mappings.xml)
	 */
	@Nonbinding
	public String value();

	/**
	 * The class to use to load the resource file.
	 */
	@Nonbinding
	public Class<?> loader() default DTOMappingNameQualifier.class;

	/**
	 * The constant class to get the type resolver from.
	 */
	@Nonbinding
	public Class<?> typeResolverConstantClass() default TypeResolver.class;

	/**
	 * The name of the constant to read.
	 */
	@Nonbinding
	public String typeResolverConstantName() default "";
}
