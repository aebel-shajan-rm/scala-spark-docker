#!/bin/bash

export AWS_ACCESS_KEY_ID=000000000000 AWS_SECRET_ACCESS_KEY=000000000000
awslocal s3 mb s3://input-bucket
awslocal s3 cp /tmp/data/input.csv s3://input-bucket/
