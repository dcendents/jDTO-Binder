/**
 * UNCLASSIFIED
 * Copyright © 2014 Defence R&D Canada - Valcartier. All rights reserved.
 */
package org.jdto;

/**
 * Defines a type resolver. The type resolver is used by the data binder to determine which type corresponding to a given source type should be instantiated to
 * be assigned as an instance of a target type.
 * 
 * @author Thales, under contract W7701-125076/001/QCL *
 */
public interface TypeResolver {

	/**
	 * Resolve the specified source type returning a type which is assignable to targetType. The following must be true in all cases:
	 * <p>
	 * <code>A.class.isAssignableFrom(resolveType(A.class, B.class))</code>
	 * 
	 * @param targetType
	 *            The target type.
	 * @param type
	 *            The type to resolve.
	 * @return The resolved type.
	 */
	Class<?> resolveType(Class<?> targetType, Class<?> type);
}
