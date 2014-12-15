/**
 * UNCLASSIFIED
 * Copyright © 2014 Defence R&D Canada - Valcartier. All rights reserved.
 */
package org.jdto.mergers;

import org.jdto.AdvancedSinglePropertyValueMerger;
import org.jdto.BeanModifier;
import org.jdto.BeanModifierAware;
import org.jdto.impl.BeanMetadata;
import org.jdto.impl.FieldMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Merger object for jDTO to keep the target object's default value when the source property is null.
 * 
 * @author Thales, under contract W7701-125076/001/QCL *
 */
public class DefaultValueKeeperMerger implements AdvancedSinglePropertyValueMerger<Object, Object>, BeanModifierAware {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(DefaultValueKeeperMerger.class);

	private BeanModifier modifier;

	@Override
	public boolean isRestoreSupported(String[] params) {
		return false;
	}

	@Override
	public Object mergeObjects(Object value, String[] extraParam, Class<?> dtoClass, String targetProperty, BeanMetadata beanMetadata,
			FieldMetadata fieldMetadata) {
		if (value != null) {
			return value;
		}

		try {
			Object obj = dtoClass.newInstance();
			return modifier.readPropertyValue(targetProperty, obj);
		} catch (IllegalAccessException ex) {
			log.error(String.format("Could not retrieve default value for [%s][%s]", extraParam[0], extraParam[1]), ex);
		} catch (InstantiationException ex) {
			log.error(String.format("Could not retrieve default value for [%s][%s]", extraParam[0], extraParam[1]), ex);
		} catch (NullPointerException ex) {
			log.error(String.format("Could not retrieve default value for [%s][%s]", extraParam[0], extraParam[1]), ex);
		}

		return null;
	}

	@Override
	public Object restoreObject(Object object, String[] params, Class<?> dtoClass, String targetProperty, BeanMetadata beanMetadata, FieldMetadata fieldMetadata) {
		return null;
	}

	@Override
	public void setBeanModifier(BeanModifier modifier) {
		this.modifier = modifier;
	}

	@Override
	public Object mergeObjects(Object value, String[] extraParam) {
		return value;
	}

	@Override
	public Object restoreObject(Object object, String[] params) {
		return object;
	}
}
