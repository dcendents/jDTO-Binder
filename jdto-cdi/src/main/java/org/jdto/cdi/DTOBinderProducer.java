/**
 * UNCLASSIFIED
 * Copyright © 2014 Defence R&D Canada - Valcartier. All rights reserved.
 */
package org.jdto.cdi;

import java.io.InputStream;
import java.text.MessageFormat;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jdto.DTOBinder;
import org.jdto.DTOBinderFactory;
import org.jdto.TypeResolver;
import org.jdto.impl.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Producer for a DTOBinder as the jdto-cdi module is broken (get NullPointerException).
 * 
 * @author Thales, under contract W7701-125076/001/QCL *
 */
public class DTOBinderProducer {

	private static final Logger log = LoggerFactory.getLogger(DTOBinderProducer.class);

	/**
	 * Method to create a DTOBinder to inject by loading the xml defined in the DTOMappingNameQualifier annotation
	 * 
	 * @return the DTOBinder
	 * @see DTOMappingNameQualifier
	 */
	@Produces
	@DTOMappingNameQualifier(value = "", loader = DTOBinderProducer.class)
	public DTOBinder getBinder(InjectionPoint ip) {

		DTOMappingNameQualifier annotation = ip.getAnnotated().getAnnotation(DTOMappingNameQualifier.class);
		String jdtoMapping = annotation.value();
		Class<?> resourceLoader = annotation.loader();

		InputStream in = resourceLoader.getResourceAsStream(jdtoMapping);
		if (in == null) {
			log.warn(MessageFormat.format("JDTO mapping resource '{0}' not found.", jdtoMapping));
		}

		TypeResolver typeResolver = null;

		String typeResolverConstantName = annotation.typeResolverConstantName();
		if (!"".equals(typeResolverConstantName)) {
			try {
				Class<?> typeResolverClass = annotation.typeResolverConstantClass();
				typeResolver = (TypeResolver) typeResolverClass.getDeclaredField(typeResolverConstantName).get(null);
			} catch (NoSuchFieldException ex) {
				log.error(ex.getMessage(), ex);
				if (!Config.isIgnoreRuntimeException()) {
					throw new RuntimeException(ex);
				}
			} catch (IllegalAccessException ex) {
				log.error(ex.getMessage(), ex);
				if (!Config.isIgnoreRuntimeException()) {
					throw new RuntimeException(ex);
				}
			}
		}

		return DTOBinderFactory.buildBinder(in, typeResolver);
	}
}
