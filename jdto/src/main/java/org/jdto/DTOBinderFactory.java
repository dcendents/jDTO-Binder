/*
 *    Copyright 2012 Juan Alberto López Cavallotti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jdto;

import java.io.InputStream;
import java.util.HashMap;
import org.jdto.impl.*;

/**
 * Factory class for getting a DTOBinder instance. Please do not use this approach
 * on dependency injection enabled environments, the framework already provides
 * integration with the Spring Framework and CDI. <br />
 * 
 * Use this class to obtain instances of the DTO Binder bean.
 * @author Juan Alberto Lopez Cavallotti
 * @since 1.0
 */
public abstract class DTOBinderFactory {

    /**
     * Create a new instance of a DTO binder with default settings. <br />
     * The resulting bean will read its configurations out of annotations on the
     * target DTOs
     * @return a new binder instance configured with the default bean modifier 
     * and empty bean metadata.
     */
    public static DTOBinder buildBinder() {
        DTOBinderBean bean = new DTOBinderBean();
        injectDependencies(bean);
        bean.setMetadata(new HashMap<Class, BeanMetadata>());
        
        return bean;
    }

    /**
     * Create a new instance of a DTO binder which will read the binding configuration
     * out of the given XML file passed as a parameter.
     * @param xmlFile
     * @return a new binder instance configured with the default bean modifier 
     * and metadata read from an XML configruation file.
     * @throws IllegalArgumentException if the classpath resource is not found.
     */
    public static DTOBinder buildBinder(InputStream xmlFile) {
        DTOBinderBean bean = new DTOBinderBean(xmlFile, true);
        injectDependencies(bean);
        
        return bean;
    }

    /**
     * Create a new instance of a DTO binder which will read the binding configuration
     * out of the given XML file passed as a parameter.
     * @param xmlFile
     * @return a new binder instance configured with the default bean modifier 
     * and metadata read from an XML configruation file.
     * @throws IllegalArgumentException if the classpath resource is not found.
     */
    public static DTOBinder buildBinder(InputStream xmlFile, TypeResolver typeResolver) {
        DTOBinderBean bean = new DTOBinderBean(xmlFile, true);
        injectDependencies(bean);
        bean.setTypeResolver(typeResolver);
        
        return bean;
    }

    /**
     * Create a new instance of a DTO binder which will read the binding configuration
     * out of the given XML file wich should be present in the classpath.
     * @param classpathResource a String pointing to a classpath resource.
     * @return a new binder instance configured with the default bean modifier
     * and metadata read from an XML configuration file.
     * @throws IllegalArgumentException if the classpath resource is not found.
     * @since 1.4
     */
    public static DTOBinder buildBinder(String classpathResource) {
        
        InputStream is = DTOBinderFactory.class.getResourceAsStream(classpathResource);
        return buildBinder(is); 
    }
    
    /**
     * Get a singleton instance of a DTO binder. This will cache the object
     * in the instance pool and will retrieve it on further usage. <br />
     * 
     * It is recommended to use this method if you want the instance being
     * handled or remembered for you. <br />
     * 
     * The returned DTO binder is annotation-style configured, which is also
     * recommended.
     * @return a singleton instance of the DTO binder which can be quicky 
     * forgotten by the user.
     */
    public static DTOBinder getBinder() {
        DTOBinderBean bean = InstancePool.getOrCreate(DTOBinderBean.class);
        injectDependencies(bean);
        
        if (bean.getMetadata() == null) {
            bean.setMetadata(new HashMap<Class, BeanMetadata>());
        }
        return bean;
    }

    private static BeanModifier getBeanModifier() {
        return InstancePool.getOrCreate(CoreBeanModifier.class);
    }

    private static PropertyValueMergerInstanceManager getMergerManager(BeanModifier beanModifier) {
        BaseMergerInstanceManager baseMergerManger = InstancePool.getOrCreate(BaseMergerInstanceManager.class);
        baseMergerManger.setModifier(beanModifier);
        return baseMergerManger;
    }
    
    private static void injectDependencies(DTOBinderBean bean) {
        bean.setBeanModifier(getBeanModifier());
        bean.setMergerManager(getMergerManager(bean.getBeanModifier()));
    }
}
