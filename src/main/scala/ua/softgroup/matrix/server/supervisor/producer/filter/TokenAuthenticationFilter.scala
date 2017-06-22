package ua.softgroup.matrix.server.supervisor.producer.filter

import java.util.Optional
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.NotAuthorizedException
import javax.ws.rs.container.{ContainerRequestContext, ContainerRequestFilter}
import javax.ws.rs.core.Response.Status.FORBIDDEN
import javax.ws.rs.core.{Context, Response}

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ua.softgroup.matrix.server.supervisor.producer.json.v2.ErrorJson
import ua.softgroup.matrix.server.supervisor.producer.token.TokenHelper

/**
  * @author Oleksandr Tyshkovets <sg.olexander@gmail.com> 
  */
@Component
class TokenAuthenticationFilter @Autowired() (tokenHelper: TokenHelper) extends ContainerRequestFilter {

  private val logger = LoggerFactory.getLogger(this.getClass)

  private val SWAGGER_JSON = "swagger.json"
  private val TOKEN = "TOKEN"

  private var context: ServletContext = _
  private var request: HttpServletRequest = _

  override def filter(requestContext: ContainerRequestContext): Unit = {
    if (SWAGGER_JSON == requestContext.getUriInfo.getPath) return

    logIpAddress()

    val token = Optional.ofNullable(requestContext.getHeaderString(TOKEN))
                        .orElseThrow(() => new NotAuthorizedException(new ErrorJson("Authorization header must be provided")))

    if (!tokenHelper.validateToken(token))
      requestContext.abortWith(
        Response
          .status(FORBIDDEN)
          .entity(new ErrorJson("Token is not valid"))
          .build
      )
  }

  private def logIpAddress() = {
    val remoteHost = request.getRemoteHost
    val remoteAddr = request.getRemoteAddr
    val remotePort = request.getRemotePort
    logger.info(s"$remoteHost $remoteAddr:$remotePort")
  }

  @Context
  def setContext(context: ServletContext): Unit = {
    this.context = context
  }

  @Context
  def setRequest(request: HttpServletRequest): Unit = {
    this.request = request
  }

}
