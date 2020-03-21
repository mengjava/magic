#!/usr/bin/env bash

cygwin=false
darwin=false
os400=false
case "`uname`" in
CYGWIN*) cygwin=true;;
Darwin*) darwin=true;;
OS400*) os400=true;;
esac
error_exit ()
{
    echo "ERROR: $1 !!"
    exit 1
}

ACTIVE_PROFILES=
SERVER_ADDR=
APP_NAME=
Usage (){
    echo "用法 [-a <多环境配置>] [-n <应用名>] [-u <注册中心地址>]"
    exit -1
}

while getopts ":a:n:u:" opt
do
    case $opt in
        a)
            ACTIVE_PROFILES=$OPTARG;;
        u)
            SERVER_ADDR=$OPTARG;;
        n)
            APP_NAME=$OPTARG;;
        ?)
        Usage;;
    esac
done

export JAVA_HOME
export JAVA="$JAVA_HOME/bin/java"
export BASE_DIR=`cd $(dirname $0); pwd`
export APP_NAME
export SERVER_ADDR
export ACTIVE_PROFILES
if [[ "$ACTIVE_PROFILES" == ""||"$SERVER_ADDR" == "" ||"$APP_NAME" == "" ]]; then
    error_exit "Please set the ACTIVE_PROFILES and SERVER_ADDR and APP_NAME variable in your environment."
fi

#该方式是从右开始最大化匹配到字符"."，然后截掉右边内容（包括字符"."）,返回余下左侧部分
#ps -ef|grep ${APP_NAME} |grep -v "$0" |grep -v "grep" |awk '{print $2}'|xargs kill -9

pkill -f ${APP_NAME}.jar

echo "${JAVA_HOME}"

if [ ! -d "${BASE_DIR}/logs" ]; then
  mkdir ${BASE_DIR}/logs
fi

JAVA_OPT="${JAVA_OPT} -server -Xms2g -Xmx2g -Xmn1g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${BASE_DIR}/logs/java_heapdump.hprof"
JAVA_OPT="${JAVA_OPT} -XX:-UseLargePages"

JAVA_OPT="${JAVA_OPT} -jar ${BASE_DIR}/target/${APP_NAME}.jar"

echo "$JAVA $JAVA_OPT"

$JAVA ${JAVA_OPT} >${BASE_DIR}/logs/${APP_NAME}_out.txt &