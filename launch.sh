#!/usr/bin/env bash
#/bin/bash

SCRIPT_DIR=$(cd `dirname $0`; pwd)
cd $SCRIPT_DIR

# JAVA ENV
export JAVA_HOME=/usr/java/vsc_jdk_jce_1.8u77_64bit
export PATH=$JAVA_HOME/bin:$PATH

# DN-Rider Env
PID_FILE=$SCRIPT_DIR/dn-rider.pid
LOG_FILE=$SCRIPT_DIR/dn-rider.log

# Kill previous execution , if any
if [ -e $PID_FILE ] ; then
        PID=$(cat $PID_FILE)
        echo "Killing older version (pid = $PID)..."
        kill -9 $PID
        rm -rf $PID_FILE
fi

# launch DN-Rider
JAR_FILE=$(ls -rt dn-rider*.*ar | head -1)
{
        echo "$(date) START"
        java -version
        echo "JAR = $JAR_FILE"
        ls -l $JAR_FILE
} | tee -a $LOG_FILE
java -jar $JAR_FILE >> $LOG_FILE &
PID=$!
echo $PID > $PID_FILE
echo "Launched as pid $PID"
echo "Log File: $LOG_FILE"
