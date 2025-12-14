#!/bin/bash

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="${SCRIPT_DIR}/.."

spark-submit \
  --class com.github.aebel.EtlJobLauncher \
  ${PROJECT_DIR}/target/scala-2.12/scala-spark-demo-assembly-0.1.0.jar \
  ${PROJECT_DIR}/config/jobConfig.conf \
  20251201000000
