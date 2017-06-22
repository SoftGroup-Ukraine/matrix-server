package ua.softgroup.matrix.server.supervisor

import java.lang.annotation.Annotation
import java.lang.reflect.{Constructor, Method}
import java.util
import javax.validation.{ParameterNameProvider, Validation}
import javax.ws.rs.{FormParam, HeaderParam, PathParam, QueryParam}
import javax.ws.rs.container.ResourceContext
import javax.ws.rs.core.Context
import javax.ws.rs.ext.{ContextResolver, Provider}

import org.glassfish.jersey.server.validation.ValidationConfig
import org.glassfish.jersey.server.validation.internal.InjectingConstraintValidatorFactory

import scala.annotation.tailrec

/**
  * @author Oleksandr Tyshkovets <sg.olexander@gmail.com>
  */
@Provider
class ValidationConfigurationContextResolver extends ContextResolver[ValidationConfig] {

  @Context
  private val resourceContext: ResourceContext = null

  override def getContext(aClass: Class[_]): ValidationConfig =
    new ValidationConfig()
      .constraintValidatorFactory(resourceContext.getResource(classOf[InjectingConstraintValidatorFactory]))
      .parameterNameProvider(new ParamBasedParameterNameProvider)


  private class ParamBasedParameterNameProvider extends ParameterNameProvider {

    private val nameProvider = Validation.byDefaultProvider.configure.getDefaultParameterNameProvider

    override def getParameterNames(constructor: Constructor[_]): util.List[String] =
      nameProvider.getParameterNames(constructor)

    override def getParameterNames(method: Method): util.List[String] = {
      val paramNames = nameProvider.getParameterNames(method)

      val parameterAnnotations = method.getParameterAnnotations

      parameterAnnotations.foreach(processArray(0, _, paramNames))

//      for (i <- parameterAnnotations.indices) {
//        parameterAnnotations(i).foreach(annotation => {
//          if (annotation.annotationType == classOf[HeaderParam])
//            paramNames.set(i, annotation.asInstanceOf[HeaderParam].value + " header")
//          else if (annotation.annotationType == classOf[PathParam])
//            paramNames.set(i, annotation.asInstanceOf[PathParam].value + " path parameter")
//          else if (annotation.annotationType == classOf[QueryParam])
//            paramNames.set(i, annotation.asInstanceOf[QueryParam].value + " query parameter")
//          else if (annotation.annotationType == classOf[FormParam])
//            paramNames.set(i, annotation.asInstanceOf[FormParam].value + " form parameter")
//        })
//      }

      paramNames
    }

    @tailrec private def processArray(i: Int, annotations: Array[Annotation], paramNames: util.List[String]) {
      if (i < annotations.length) {

        if (annotations(i).annotationType == classOf[HeaderParam])
          paramNames.set(i, annotations(i).asInstanceOf[HeaderParam].value + " header")
        else if (annotations(i).annotationType == classOf[PathParam])
          paramNames.set(i, annotations(i).asInstanceOf[PathParam].value + " path parameter")
        else if (annotations(i).annotationType == classOf[QueryParam])
          paramNames.set(i, annotations(i).asInstanceOf[QueryParam].value + " query parameter")
        else if (annotations(i).annotationType == classOf[FormParam])
          paramNames.set(i, annotations(i).asInstanceOf[FormParam].value + " form parameter")

        processArray(i + 1, annotations, paramNames)
      }
    }

  }

}
