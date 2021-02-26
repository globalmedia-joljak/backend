#!/bin/bash

REPOSITORY=/home/ubuntu/dev/backend/build
PROJECT_NAME=joljak-dev-server

echo "> Build 파일 복사"

cp $REPOSITORY/*.jar $REPOSITORY/zip

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f *.jar | awk '{print  $1}')
echo "현재 구동중인 애플리케이션 pid:" $CURRENT_PID

if [  -z "$CURRENT_PID" ]; then
        echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> 현재 구동 중인 애플리케이션 삭제."
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> 새애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/zip/*.jar | tail -n 1)

echo "> JAR Name:$JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

sudo nohup java -jar -Dspring.config.location=/home/ubuntu/dev/backend/config/application-dev.properties $JAR_NAME > $REPOSITORY/zip/nohup.out 2>&1 &

echo "> $JAR_NAME 실행 완료"