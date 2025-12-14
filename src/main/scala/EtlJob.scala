package com.github.aebel
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.slf4j.{Logger, LoggerFactory}


trait EtlJob {
  val logger: Logger = LoggerFactory.getLogger(getClass)

  def runJob(jobConfig: JobConfig, spark: SparkSession): Unit = {

    val schema = StructType(Array(
      StructField("id", IntegerType, nullable = false),
      StructField("name", StringType, nullable = false),
      StructField("department", StringType, nullable = false),
      StructField("salary", IntegerType, nullable = false)
    ))

    val rawDF = spark.read
      .option("header", "true") // Treat the first row as a header
      .schema(schema)           // Apply the defined schema
      .csv(jobConfig.inputLocation)

    rawDF.printSchema()
    rawDF.show()

    // --- T: TRANSFORM ---
    logger.info("--- Transforming data... ---")
    // Transformation 1: Filter out salaries below 60000
    val filteredDF = rawDF.filter(col("salary") >= 60000)

    // Transformation 2: Add a new column "Bonus" (20% of salary)
    val transformedDF = filteredDF
      .withColumn("Bonus", col("salary") * 0.20)
      .withColumn("TotalComp", col("salary") + col("Bonus"))
      .orderBy(desc("TotalComp"))

    transformedDF.printSchema()
    transformedDF.show()

    // --- L: LOAD ---
    logger.info("--- Loading data... ---")

    // Delete old output directory if it exists (for local testing)
    // NOTE: In a real cluster, you'd be more careful with file deletion.

    transformedDF.write
      .mode("overwrite")     // Overwrite if the directory exists
      .option("header", "true") // Include column headers in the output file
      .csv(jobConfig.outputLocation)        // Write the DataFrame as CSV files to the directory

    logger.info(s"--- ETL Complete! Data written to: ${jobConfig.outputLocation} ---")

    // Stop the Spark Session
    spark.stop()
  }


  def parseConfig(args: Array[String]): JobConfig = {
    if (args.length != 2) {
      throw new IllegalArgumentException(
        "Expecting only two arguments, the path to a config file and a partitionTs")
    }

    val configFilePath = args(0)
    val config = ConfigFactory.load(ConfigFactory.parseFile(new java.io.File(configFilePath)))


    JobConfig(
      config.getString("config.outputLocation"),
      config.getString("config.inputLocation"),
      "20251201000000"
    )
  }
}

case class JobConfig(
  outputLocation: String,
  inputLocation: String,
  parTs: String)
