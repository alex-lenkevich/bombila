package bombila

import ch.rasc.bsoncodec.time.ZonedDateTimeStringCodec
import org.bson.codecs.configuration.CodecRegistries.{fromCodecs, fromProviders, fromRegistries}
import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

class PriceRepository {

  val codecRegistry = fromRegistries(
    fromCodecs(new ZonedDateTimeStringCodec()),
    fromProviders(classOf[Flight]),
    fromProviders(classOf[PriceEntity]),
    DEFAULT_CODEC_REGISTRY)

  val mongoClient: MongoClient = MongoClient()
  val db: MongoDatabase = mongoClient.getDatabase("bombila").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[PriceEntity] = db.getCollection("bombila")

  def save(price: PriceEntity) = {
    collection.insertOne(price).head()
  }

}
