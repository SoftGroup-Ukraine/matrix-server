package ua.softgroup.matrix.server.supervisor

import java.io.{FileInputStream, IOException, InputStream}
import java.nio.file.{Files, Paths}
import java.security.interfaces.RSAPublicKey
import java.security.spec.{InvalidKeySpecException, PKCS8EncodedKeySpec, X509EncodedKeySpec}
import java.security.{KeyFactory, NoSuchAlgorithmException, PrivateKey, PublicKey}
import java.text.ParseException
import java.util.Date
import javax.annotation.PostConstruct

import com.google.common.io.ByteStreams
import com.nimbusds.jose.{JOSEException, JWSAlgorithm, JWSHeader}
import com.nimbusds.jose.crypto.{RSASSASigner, RSASSAVerifier}
import com.nimbusds.jwt.{JWTClaimsSet, SignedJWT}
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import ua.softgroup.matrix.server.supervisor.producer.exception.JwtException

/**
  * @author Oleksandr Tyshkovets <sg.olexander@gmail.com>
  */
@Component
class TokenHelper {

  private val KEY_ALGORITHM = "RSA"
  private val ALGORITHM = "RS512"

  private val PUBLIC_KEY_FILE = "public_key.der"
  private val PRIVATE_KEY_FILE = "private_key.der"

  private var publicKey: PublicKey = _
  private var privateKey: PrivateKey = _

  @PostConstruct
  def init(): Unit = {
    publicKey = generatePublic(securityKey2Bytes(PUBLIC_KEY_FILE))
    privateKey = generatePrivate(securityKey2Bytes(PRIVATE_KEY_FILE))
  }

  def validateToken(token: String): Boolean =
    try
      SignedJWT
        .parse(token)
        .verify(new RSASSAVerifier(publicKey.asInstanceOf[RSAPublicKey]))
    catch {
      case e@(_: JOSEException | _: ParseException) => throw new JwtException(e)
    }

  def generateToken(subject: String, issuer: String, expirationTime: Date): String = {
    val signedJWT = new SignedJWT(
      new JWSHeader(JWSAlgorithm.parse(ALGORITHM)),
      new JWTClaimsSet.Builder()
        .subject(subject)
        .issuer(issuer)
        .expirationTime(expirationTime)
        .build)
    signedJWT.sign(new RSASSASigner(privateKey))

    signedJWT.serialize
  }

  def extractSubjectFromToken(token: String): String =
    try
      SignedJWT.parse(token).getJWTClaimsSet.getSubject
    catch {
      case e: ParseException => throw new JwtException(e)
    }

  @throws[NoSuchAlgorithmException]
  @throws[InvalidKeySpecException]
  private def generatePublic(keyBytes: Array[Byte]) =
    KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(keyBytes))

  @throws[NoSuchAlgorithmException]
  @throws[InvalidKeySpecException]
  private def generatePrivate(keyBytes: Array[Byte]) =
    KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(keyBytes))

  private def securityKey2Bytes(filePath: String) = ByteStreams.toByteArray(Files.newInputStream(Paths.get(filePath)))

}
