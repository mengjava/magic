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

[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/usr/java
[ ! -e "$JAVA_HOME/bin/java" ] && unset JAVA_HOME

if [ -z "$JAVA_HOME" ]; then
  if $darwin; then

    if [ -x '/usr/libexec/java_home' ] ; then
      export JAVA_HOME=`/usr/libexec/java_home`

    elif [ -d "/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home" ]; then
      export JAVA_HOME="/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home"
    fi
  else
    JAVA_PATH=`dirname $(readlink -f $(which javac))`
    if [ "x$JAVA_PATH" != "x" ]; then
      export JAVA_HOME=`dirname $JAVA_PATH 2>/dev/null`
    fi
  fi
  if [ -z "$JAVA_HOME" ]; then
        error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better!"
  fi
fi

ACTIVE_PROFILES=
SERVER_ADDR=

Usage (){
    echo "用法 [-h <输出帮助信息>] [-a <多环境配置>] [-u <注册中心地址>]"
    exit -1
}

while getopts ":a:u:" opt
do
    case $opt in
        a)
            ACTIVE_PROFILES=$OPTARG;;
        u)
            SERVER_ADDR=$OPTARG;;
        ?)
        Usage;;
    esac
done

export JAVA_HOME
export JAVA="$JAVA_HOME/bin/java"
export BASE_DIR=`cd $(dirname $0)/..; pwd`
export SERVER_ADDR
export ACTIVE_PROFILES
if [[ "$ACTIVE_PROFILES" == ""||"$SERVER_ADDR" == "" ]]; then
    error_exit "Please set the ACTIVE_PROFILES and SERVER_ADDR variable in your environment."
fi

appName=$0
#该方式是从右开始最大化匹配到字符"."，然后截掉右边内容（包括字符"."）,返回余下左侧部分
ps -ef|grep "${appName%%.*}" |grep -v "$0" |grep -v "grep" |awk '{print $2}'|xargs kill -9

echo "${JAVA_HOME}"

if [ ! -d "${BASE_DIR}/logs" ]; then
  mkdir ${BASE_DIR}/logs
fi

JAVA_OPT="${JAVA_OPT} -server -Xms2g -Xmx2g -Xmn1g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${BASE_DIR}/logs/java_heapdump.hprof"
JAVA_OPT="${JAVA_OPT} -XX:-UseLargePages"

JAVA_OPT="${JAVA_OPT} -jar ${BASE_DIR}/"${appName%%.*}"/target/"${appName%%.*}".jar"

echo "$JAVA $JAVA_OPT"

nohup $JAVA ${JAVA_OPT} >${BASE_DIR}/logs/"${appName%%.*}"_out.txt &