/**
 *
 * Copyright Notice: Openmatics s.r.o -- Confidential and Proprietary
 *
 * All rights reserved.
 * This software is the confidential and proprietary information of Openmatics s.r.o
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Openmatics.
 */
package com.openmatics.omob.clientlogin.rest;

import javax.annotation.PostConstruct;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.openmatics.omob.clientlogin.common.CommonExceptionMapper;


/**
 * This class registers valid Spring beans as Restful Service.
 * @author Kather Basha
 *
 */
@Component
public class JerseyConfig extends ResourceConfig implements BeanPostProcessor {

  /**
   * Providing data-binding functionality for Jackson.
   */
  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Exception Utility changes.
   */
  @Autowired
  private CommonExceptionMapper commonExceptionMapper;

  /**
   * Registering JacksonJaxbJsonProvider and required Exception Handler Class.
   */
  @PostConstruct
  public void initConfig() {
    //register error mapping
    register(commonExceptionMapper);

    JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
    provider.setMapper(objectMapper);
    register(provider);
    register(AccessDeniedMapper.class);
  }

  /**
   * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
   */
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Object targetObject = getTargetObject(bean);

    Path annotationPath = AnnotationUtils.findAnnotation(targetObject.getClass(), Path.class);
    if (annotationPath != null && !isRegistered(targetObject)) {
      register(targetObject);
    }

    Provider annotationProvider = AnnotationUtils.findAnnotation(targetObject.getClass(), Provider.class);
    if (annotationProvider != null && !isRegistered(targetObject)) {
      register(targetObject);
    }

    return bean;
  }

  /**
   * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
   */
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }

  /**
   * Reading Target Object through proxy.
   * @param proxy object is input
   * @param <T> parameter
   * @return <T> T return type object
   */
  protected <T> T getTargetObject(Object proxy) {
    if (AopUtils.isJdkDynamicProxy(proxy)) {
      try {
        return (T) ((Advised) proxy).getTargetSource().getTarget();
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    } else {
      return (T) proxy;
    }
  }

}

