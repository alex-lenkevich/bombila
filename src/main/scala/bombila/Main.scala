package bombila

import java.time.{LocalDate, LocalDateTime, ZoneOffset, ZonedDateTime}
import java.util.Date

import org.mongodb.scala.bson.BsonDocument

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {

  private val outboundDate: ZonedDateTime = ZonedDateTime.now()
  private val backDate: ZonedDateTime = ZonedDateTime.now().plusMonths(1)

  private val repository = new PriceRepository()

  repository.collection.deleteMany(BsonDocument())

  def `LocalDate to date`(l: LocalDate) = new Date(l.toEpochDay * 24 * 60 * 60 * 1000)
  def `LocalDate to date`(l: LocalDateTime) = new Date(l.toEpochSecond(ZoneOffset.UTC) * 1000)

  private val entity = PriceEntity(ZonedDateTime.now(), ZonedDateTime.now().plusMonths(1), "hamb", "hrk", 300, 280,
    List(
      Flight("LOT", outboundDate, "HAM", outboundDate.plusHours(1), "WAW"),
      Flight("LOT", outboundDate.plusHours(2), "WAW", outboundDate.plusHours(3), "HRK")
    ),
    List(
      Flight("LOT", backDate, "HRK", backDate, "WAW"),
      Flight("LOT", backDate.plusHours(2), "WAW", backDate.plusHours(3), "HAM")
    ),
    300L
  )
  val result = for {
    _ <- repository.save(entity)
    loaded <- repository.collection.find(BsonDocument()).head()
  } yield {
    assert(loaded == loaded, s"$loaded not equal to $loaded")
  }

  Await.result(result, 5 seconds)


}
