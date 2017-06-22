package ua.softgroup.matrix.server.service.impl

import java.io.IOException

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import retrofit2.Response
import ua.softgroup.matrix.server.service.TrackerSettingsService
import ua.softgroup.matrix.server.supervisor.consumer.endpoint.SupervisorEndpoint
import ua.softgroup.matrix.server.supervisor.consumer.json.SettingsJson

import scala.collection.{JavaConverters, immutable}

/**
  * @author Oleksandr Tyshkovets <sg.olexander@gmail.com>
  */
@Service
@PropertySource(Array("classpath:desktop.properties"))
class TrackerSettingsServiceImpl @Autowired() (supervisorEndpoint: SupervisorEndpoint, env: Environment)
  extends TrackerSettingsService {

  private val logger = LoggerFactory.getLogger(this.getClass)

  private val CHECKPOINT_FREQUENTLY_SECONDS_PROP = "checkpoint.frequently.seconds"
  private val SCREENSHOT_FREQUENTLY_PERIOD_PROP = "screenshot.frequently.period"
  private val IDLE_START_SECONDS_PROP = "idle.start.seconds"

  private lazy val checkpointFrequentlySeconds = env.getRequiredProperty(CHECKPOINT_FREQUENTLY_SECONDS_PROP).toInt
  private lazy val screenshotFrequentlyPeriod = env.getRequiredProperty(SCREENSHOT_FREQUENTLY_PERIOD_PROP).toInt
  private lazy val idleStartSeconds = env.getRequiredProperty(IDLE_START_SECONDS_PROP).toInt

  override def getTrackerSettings (token: String): TrackerSettings = {
    var settingsMap: Map[String, Int] = Map()
    try
      settingsMap = querySettings(token)
    catch {
      case e: IOException =>
        logger.error("Error when querying settings from supervisor API", e)
    }

    TrackerSettings(
      settingsMap.getOrElse(CHECKPOINT_FREQUENTLY_SECONDS_PROP, checkpointFrequentlySeconds),
      settingsMap.getOrElse(SCREENSHOT_FREQUENTLY_PERIOD_PROP, screenshotFrequentlyPeriod),
      settingsMap.getOrElse(IDLE_START_SECONDS_PROP, idleStartSeconds)
    )
  }

  @throws[IOException]
  private def querySettings (token: String): Map[String, Int] = {
    val response: Response[SettingsJson] = supervisorEndpoint.getTrackerSettings(token).execute

    if (!response.isSuccessful) {
      throw new IOException(response.errorBody.string)
    }

    response.body.getList
      .map(e => (e.key, e.value))
      .toMap
  }

}
