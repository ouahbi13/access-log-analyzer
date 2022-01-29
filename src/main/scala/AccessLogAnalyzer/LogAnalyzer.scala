package AccessLogAnalyzer

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.log4j._
import org.elasticsearch.hadoop.cfg.ConfigurationOptions

object LogAnalyzer {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .config(ConfigurationOptions.ES_NET_HTTP_AUTH_USER, "elastic")
      .config(ConfigurationOptions.ES_NET_HTTP_AUTH_PASS, "changeme")
      .config(ConfigurationOptions.ES_NODES, "localhost")
      .config(ConfigurationOptions.ES_PORT, "9200")
      .master("local[*]")
      .appName("first").getOrCreate()

    Logger.getLogger("org").setLevel(Level.ERROR)

    val schema = StructType(List(
      StructField("HTTP", StringType, true),
      StructField("Status", StringType, true),
      StructField("Website", StringType, true),
      StructField("Asset", StringType, true),
      StructField("Ip", StringType, true)))

    val StreamDF = spark.readStream.option("delimiter", " ").schema(schema)
      .csv("/home/dba/IdeaProjects/AccessLogAnalyzer/data")

    import spark.implicits._

    StreamDF.writeStream
      .option("checkpointLocation", "/tmp/es_checkpoint")
      .format("es")
      .outputMode("append")
      .start("spark/logs")
      .awaitTermination()
    }
}

