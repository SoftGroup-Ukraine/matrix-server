package ua.softgroup.matrix.server.supervisor.producer.config

import javax.ws.rs.ApplicationPath

import io.swagger.jaxrs.config.BeanConfig
import io.swagger.jaxrs.listing.{ApiListingResource, SwaggerSerializers}
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.boot.context.embedded.{ConfigurableEmbeddedServletContainer, EmbeddedServletContainerCustomizer}
import org.springframework.boot.web.servlet.{ErrorPage, FilterRegistrationBean}
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.cors.{CorsConfiguration, UrlBasedCorsConfigurationSource}
import org.springframework.web.filter.CorsFilter
import ua.softgroup.matrix.server.supervisor.producer.filter.TokenAuthenticationFilter
import ua.softgroup.matrix.server.supervisor.producer.resources.{ReportResource, TimeResource, TrackingDataResource, WorkDaysResource}

/**
  * @author Oleksandr Tyshkovets <sg.olexander@gmail.com> 
  */
@Component
@ApplicationPath("/api/v2")
class JerseyConfig extends ResourceConfig {

  {
    register(classOf[ReportResource])
    register(classOf[WorkDaysResource])
    register(classOf[TimeResource])
    register(classOf[TrackingDataResource])

    register(classOf[GenericExceptionMapper])
    register(classOf[ValidationExceptionMapper])
    register(classOf[ValidationConfigurationContextResolver])
    register(classOf[TokenAuthenticationFilter])

    configureSwagger()
  }

  private def configureSwagger() = {
    register(classOf[ApiListingResource])
    register(classOf[SwaggerSerializers])

    val beanConfig = new BeanConfig
    beanConfig.setTitle("Matrix REST API")
    beanConfig.setDescription("Make Matrix Great Again")
    beanConfig.setVersion("2.0")
    beanConfig.setBasePath("/api/v2")
    beanConfig.setResourcePackage("ua.softgroup.matrix.server.supervisor.producer")
    beanConfig.setScan(true)
  }

  @Bean
  def corsFilter: FilterRegistrationBean = {
    val config = new CorsConfiguration
    config.setAllowCredentials(true)
    config.addAllowedOrigin("*")
    config.addAllowedHeader("*")
    config.addAllowedMethod("*")
    val source = new UrlBasedCorsConfigurationSource
    source.registerCorsConfiguration("/**", config)
    val bean = new FilterRegistrationBean(new CorsFilter(source))
    bean.setOrder(0)
    bean
  }

  /**
    * Route all errors towards a stub for hiding Tomcat error pages
    */
  @Bean
  def containerCustomizer: EmbeddedServletContainerCustomizer =
    (container: ConfigurableEmbeddedServletContainer) => container.addErrorPages(new ErrorPage("/"))

}
