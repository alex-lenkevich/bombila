package bombila

import java.time.ZonedDateTime

case class PriceEntity(departDate: ZonedDateTime,
                       returnDate: ZonedDateTime,
                       from: String,
                       to: String,
                       outboundDuration: Long,
                       returnDuration: Long,
                       outboundFlights: List[Flight],
                       returnFlights: List[Flight],
                       price: Long)

case class Flight(company: String,
                  departTime: ZonedDateTime,
                  departAirport: String,
                  arrivalTime: ZonedDateTime,
                  arrivalAirport: String)

