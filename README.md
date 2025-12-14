# Example scala spark project


## Things to note
| snippet                            | description                                                                                                                                                                    |
|------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| command `sbt package`              | Creates jar (without dependencies included) inside `target` folder                                                                                                             | 
| command `sbt assembly`             | Requires additional plugin `addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.2.0")` in `project/plugin.sbt`. <br> <br> Creates fat jar (includes dependencies)                
| syntax `% provided` in `build.sbt` | This results in the dependency not being included in the fat jar created by `sbt assembly`                                                                                     |
| command `sbt run`                  | Runs project by finding main entrypoint. <br> <br> NOTE: For spark scala projects use `sbt assembly` then `spark-submit` to run spark scala code. `sbt run` will cause errors. |
| syntax `.master("local[*]")` when initialising spark session. | Run locally using all cores.                                                                                                                                                   |
| command `spark-submit` | Used to run scala spark jobs using assembly jar |


## sources
* https://www.scala-sbt.org/1.x/docs/Running.html
* https://spark.apache.org/docs/3.5.7/cluster-overview.html
* https://medium.com/@suffyan.asad1/spark-essentials-a-guide-to-setting-up-and-running-spark-projects-with-scala-and-sbt-80e2680d3528
* https://spark.apache.org/docs/latest/submitting-applications.html
* https://stackoverflow.com/questions/27248997/how-to-suppress-spark-logging-in-unit-tests
* https://spark.apache.org/docs/latest/quick-start.html