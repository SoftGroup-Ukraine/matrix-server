package ua.softgroup.matrix.server.persistent.entity

import java.time.LocalDateTime
import javax.persistence._

import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.jpa.domain.AbstractPersistable

import scala.beans.BeanProperty

/**
  * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
  */
@Entity
@SerialVersionUID(7093407748878141348L)
class TimeAudit extends AbstractPersistable[java.lang.Long] {

  @Column
  @CreationTimestamp
  @BeanProperty
  var creationDate: LocalDateTime = _

  @Column
  @BeanProperty
  var timeSeconds: Int = _

  @Column(columnDefinition = "TEXT")
  @BeanProperty
  var reason: String = _

  @Column
  @BeanProperty
  var principalId: Long = _

  @ManyToOne(fetch = FetchType.LAZY)
  @BeanProperty
  var workDay: WorkDay = _

  def this(timeSeconds: Int, reason: String, principalId: Long, workDay: WorkDay) {
    this()
    this.timeSeconds = timeSeconds
    this.reason = reason
    this.principalId = principalId
    this.workDay = workDay
  }

  override def toString: String = {
    "TimeAudit(%s, creationDate=%s, timeSeconds=%d, reason=%s, principalId=%d, workDay=%s)"
      .format(super.getId, creationDate, timeSeconds, reason, principalId, workDay)
  }

}
