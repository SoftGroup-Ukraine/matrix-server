package ua.softgroup.matrix.server.service

import java.util.Optional

import ua.softgroup.matrix.server.persistent.entity.{WorkDay, WorkTimePeriod}

/**
  * @author Oleksandr Tyshkovets <sg.olexander@gmail.com>
  */
trait WorkTimePeriodService extends GenericEntityService[WorkTimePeriod] {

  def getLatestPeriodOf(workDay: WorkDay): Optional[WorkTimePeriod]

}
