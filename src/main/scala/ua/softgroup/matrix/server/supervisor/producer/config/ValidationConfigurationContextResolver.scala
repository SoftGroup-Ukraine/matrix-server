package ua.softgroup.matrix.server.supervisor.producer.config

import java.lang.reflect.{Constructor, Method}
import java.util
import javax.validation.{ParameterNameProvider, Validation}
import javax.ws.rs.{FormParam, HeaderParam, PathParam, QueryParam}
import javax.ws.rs.container.ResourceContext
import javax.ws.rs.core.Context
import javax.ws.rs.ext.ContextResolver

import org.glassfish.jersey.server.validation.ValidationConfig
import org.glassfish.jersey.server.validation.internal.InjectingConstraintValidatorFactory

/**
  * @author Oleksandr Tyshkovets <sg.olexander@gmail.com> 
  */
class ValidationConfigurationContextResolver extends ContextResolver[ValidationConfig] {

  @Context
  private val resourceContext: ResourceContext = _

  override def getContext(`type`: Class[_]): ValidationConfig =
    new ValidationConfig()
      .constraintValidatorFactory(resourceContext.getResource(classOf[InjectingConstraintValidatorFactory]))
      .parameterNameProvider(new ParamBasedParameterNameProvider)


  private class ParamBasedParameterNameProvider extends ParameterNameProvider {

    private var nameProvider: ParameterNameProvider = _

    {
      nameProvider = Validation.byDefaultProvider.configure.getDefaultParameterNameProvider
    }

    override def getParameterNames(constructor: Constructor[_]): util.List[String] =
      nameProvider.getParameterNames(constructor)

    override def getParameterNames(method: Method): util.List[String] = {
      val names = nameProvider.getParameterNames(method)

      val parameterAnnotations = method.getParameterAnnotations
      var i = 0
      while ( {
        i < parameterAnnotations.length
      }) {
        for (annotation <- parameterAnnotations(i)) {
          if (annotation.annotationType eq classOf[HeaderParam]) names.set(i, annotation.asInstanceOf[HeaderParam].value + " header")
          else if (annotation.annotationType eq classOf[PathParam]) names.set(i, annotation.asInstanceOf[PathParam].value + " path parameter")
          else if (annotation.annotationType eq classOf[QueryParam]) names.set(i, annotation.asInstanceOf[QueryParam].value + " query parameter")
          else if (annotation.annotationType eq classOf[FormParam]) names.set(i, annotation.asInstanceOf[FormParam].value + " form parameter")
        }

        {
          i += 1; i - 1
        }
      }

      names
    }
  }

}
