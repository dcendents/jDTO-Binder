/**
 * UNCLASSIFIED
 * Copyright © 2014 Defence R&D Canada - Valcartier. All rights reserved.
 */
package org.jdto.cdi;

import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.jdto.TypeResolver;

/**
 * Implementation of TypeResolver backed by a BidiMap. This implementation resolves types by giving priority to mapped types. The implementation verify in the
 * default map and its inverse map.
 * 
 * @author Thales, under contract W7701-125076/001/QCL *
 */
public class BidiMapTypeResolver implements TypeResolver {
	private BidiMap bidiMap;

	/**
	 * Construct a BidiMapTypeResolver with an empty map.
	 */
	public BidiMapTypeResolver() {
		this.bidiMap = new DualHashBidiMap();
	}

	/**
	 * Construct a BidiMapTypeResolver. The resolver is initialize by using the entries specified in the provided map. The provided map should not contain
	 * inverse entries.
	 * 
	 * @param map
	 *            The types map.
	 */
	@SuppressWarnings("unchecked")
	public BidiMapTypeResolver(Map<Class<?>, Class<?>> map) {
		this.bidiMap = new DualHashBidiMap();
		if (map != null) {
			this.bidiMap.putAll(map);
		}
	}

	@Override
	public Class<?> resolveType(Class<?> targetType, Class<?> type) {
		if (type == null) {
			return targetType;
		}

		Class<?> resultType = (Class<?>) bidiMap.get(type);
		if (resultType == null) {
			resultType = (Class<?>) bidiMap.inverseBidiMap().get(type);
		}

		if (resultType != null) {
			return resultType;
		}

		return targetType;
	}
}
