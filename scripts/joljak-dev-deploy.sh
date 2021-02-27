#!/bin/bash

REPOSITORY=/home/ubuntu/dev/backend/build
PROJECT_NAME=joljak-dev-server

echo "> Build 파일 복사"

cp $REPOSITORY/*.jar $REPOSITORY/zip

sudo kill $(sudo lsof -t -i:8080)

echo "> 새애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/zip/*.jar | tail -n 1)

echo "> JAR Name:$JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

sudo nohup java -jar -Dspring.config.location=/home/ubuntu/dev/backend/config/application-dev.properties $JAR_NAME > $REPOSITORY/zip/nohup.out 2>&1 &

echo "> 새애플리케이션 배포 완료"