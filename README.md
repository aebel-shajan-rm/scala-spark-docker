# Example scala spark project

Made this project to see the end to end of a typical scala spark project.

## Setup 
### Using commands
00. Install required dependencies (probs using sdkman):
    * spark 3.5.3
    * scala 2.12.21
    * jdk 17
    * sbt 1.11.7
    
01. clone repo

```shell
git clone <git repo goes here>
```

1. Create fat jar
```shell
sbt assembly
```

2. Run job with spark-submit in shell script
```shell
sh ./scripts/run.sh
```
### Using docker
0. Run compose
```shell
docker compose up
```



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
sbt and setting up project:
* https://www.scala-sbt.org/1.x/docs/Running.html
* https://medium.com/@suffyan.asad1/spark-essentials-a-guide-to-setting-up-and-running-spark-projects-with-scala-and-sbt-80e2680d3528

spark docs:

* https://spark.apache.org/docs/latest/submitting-applications.html
* https://spark.apache.org/docs/3.5.7/cluster-overview.html
* https://spark.apache.org/docs/latest/quick-start.html

logs:
* https://stackoverflow.com/questions/27248997/how-to-suppress-spark-logging-in-unit-tests