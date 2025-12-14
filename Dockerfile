# STAGE 1: building fat jar
FROM sbtscala/scala-sbt:eclipse-temurin-17.0.15_6_1.11.7_2.12.21 AS builder
WORKDIR /app
COPY project /app/project
COPY build.sbt /app/
# cache dependencies
RUN sbt update
COPY src /app/src
# build fat jar
RUN sbt assembly

# STAGE 2: Running spark job with spark-submit
# Using apche spark official image. (not emr image as its bigg +3GB)
FROM apache/spark:3.5.7-scala2.12-java17-python3-ubuntu
# Setup env vars to use spark-submit command
ENV SPARK_HOME=/opt/spark
ENV PATH=$SPARK_HOME/bin:$PATH
WORKDIR /opt/spark/work-dir
COPY --from=builder /app/target/scala-2.12/*.jar app.jar
COPY config config
COPY data data

# Define the entrypoint to run your job with spark-submit
ENTRYPOINT ["spark-submit", \
    "--class", "com.github.aebel.EtlJobLauncher", \
    "app.jar", \
    "config/jobConfig.conf", \
    "20251201000000"]
