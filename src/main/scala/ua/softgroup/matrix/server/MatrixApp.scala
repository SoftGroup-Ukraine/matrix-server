package ua.softgroup.matrix.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.ehcache.{EhCacheCacheManager, EhCacheManagerFactoryBean}
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

/**
  * @author Oleksandr Tyshkovets <sg.olexander@gmail.com> 
  */
@SpringBootApplication
@EnableCaching
class MatrixApp {

  @Bean def validator = new LocalValidatorFactoryBean

  @Bean def cacheManager = new EhCacheCacheManager(ehCacheCacheManager.getObject)

  @Bean def ehCacheCacheManager: EhCacheManagerFactoryBean = {
    val cmfb = new EhCacheManagerFactoryBean
    cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"))
    cmfb.setShared(true)
    cmfb
  }
}

object MatrixApp extends App {
  SpringApplication.run(classOf[MatrixApp])
}
