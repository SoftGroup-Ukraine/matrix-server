package ua.softgroup.matrix.server.service.impl

import java.util.Optional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.softgroup.matrix.server.persistent.entity.{WorkDay, WorkTimePeriod}
import ua.softgroup.matrix.server.persistent.repository.WorkTimePeriodRepository
import ua.softgroup.matrix.server.service.WorkTimePeriodService

/**
  * @author Oleksandr Tyshkovets <sg.olexander@gmail.com> 
  */
@Service
class WorkTimePeriodServiceImpl @Autowired() (repository: WorkTimePeriodRepository) extends WorkTimePeriodService {

  override def getLatestPeriodOf(workDay: WorkDay): Optional[WorkTimePeriod] =
    Optional.ofNullable(repository.findTopByWorkDayOrderByStartDesc(workDay))

  override def getById(id: Long): Optional[WorkTimePeriod] = Optional.ofNullable(repository.findOne(id))

  override def save(entity: WorkTimePeriod): WorkTimePeriod = repository.save(entity)

  override def isExist(id: Long): Boolean = repository.exists(id)

}
