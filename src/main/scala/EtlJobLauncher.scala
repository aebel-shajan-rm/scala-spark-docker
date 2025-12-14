package com.github.aebel
import org.apache.spark.sql.SparkSession

object EtlJobLauncher extends Serializable with EtlJob {
  def main(args: Array[String]): Unit = {
    try {
      val spark: SparkSession = SparkSession
        .builder
        .appName("Example etl job")
        .getOrCreate()


      logger.info(s"Starting Etl Job")
      val config = parseConfig(args)
      runJob(config, spark)

      logger.info(s"Finished Etl Job")
      spark.stop()
    } catch {
      case e: Exception =>
        logger.error(s"job failed with exception ${e.getMessage}")
        sys.exit(2)
    }
  }
}
