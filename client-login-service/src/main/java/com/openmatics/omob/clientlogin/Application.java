
package com.openmatics.omob.clientlogin;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;




/**
 * SpringBootServletInitializer launch of spring boot application.
 *
 */
@SpringBootApplication
@ComponentScan("com.openmatics")
@EnableCaching
public class Application extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(Application.class);
  }

  /**
   * main method launch point of application.
   * @param args input params
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
  /**
   * Model mapper for DTO from/to Entity conversion.
   *@return Model mapper Object
   */
  @Bean
  public ModelMapper modelMapper() {
      return new ModelMapper();
  }
  
  @Bean
  public CacheManager cacheManager() {
      return new EhCacheCacheManager(cacheMangerFactory().getObject());
  }

  @Bean
  public EhCacheManagerFactoryBean cacheMangerFactory() {
      EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
      bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
      bean.setShared(true);
      return bean;
  }
  
}
