#!/bin/bash

BIN_DIR="./bin"
DB_DIR="./db"
LOG_DIR="./log"

case $1 in
    initdb)
        rm -rf $DB_DIR
        mkdir $DB_DIR
        touch $DB_DIR/wechat.db
        ;;
    removedb)
        rm -rf $DB_DIR
        ;;
    make)
        if [ ! -d $BIN_DIR ]; then
            mkdir $BIN_DIR
        fi
        mvn clean package
        cp ./target/wechat-1.0.0.0-SNAPSHOT.jar $BIN_DIR
        ;;
    clean)
        if [ -d $BIN_DIR ]; then
            rm -rf $BIN_DIR
        fi
        if [ -f ./wechat.pid ]; then
            rm wechat.pid
        fi
        mvn clean
        ;;
    start)
        nohup java -jar $BIN_DIR/wechat-1.0.0.0-SNAPSHOT.jar > /dev/null 2>&1 &
        echo $! > ./wechat.pid
        ;;
    stop)
        kill `cat ./wechat.pid`
        rm -rf $BIN_DIR
        rm ./wechat.pid
        ;;
    *)
        echo "usage: xyz {initdb|removedb|make|start|stop}" ;;
esac

