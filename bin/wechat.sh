#!/bin/sh
PROJECT="wechat"
APP="wechat"
JAR_PATH="/root/$PROJECT/bin/$APP.jar"
JAVA_OPTS="-ms512m -mx512m -Xmn256m -XX:MaxPermSize=256m"
DEBUG_PORT="84841"
DEBUG_ARGS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=$DEBUG_PORT"

#初始化psid变量（全局）
psid=0

checkpid() {
   javaps=`jps -l | grep $APP`
   if [ -n "$javaps" ]; then
      psid=`echo $javaps | awk '{print $1}'`
   else
      psid=0
   fi
}

start() {
   checkpid

   if [ $psid -ne 0 ]; then
      echo "================================"
      echo "warn: $APP already started! (pid=$psid)"
      echo "================================"
   else
      echo "Starting $APP ..."
      nohup java $JAVA_OPTS $DEBUG_ARGS -jar $JAR_PATH > /dev/null 2>&1 &
      checkpid
      if [ $psid -ne 0 ]; then
         echo "(pid=$psid) [OK]"
      else
         echo "[Failed]"
      fi
   fi
}

stop() {
   checkpid
   if [ $psid -eq 0 ]; then
      echo "================================"
      echo "warn: $APP is not running"
      echo "================================"
      exit 1
   else
      count=0
      while [ $psid -ne 0 ];
      do
        let count=$count+1
        echo "Stopping $APP $count times...(pid=$psid) "
        if [ $count -gt 5 ]; then
          echo "kill -9 $psid"
          kill -9 $psid
        else
          kill -15 $psid
        fi
        sleep 2;
        checkpid
      done
      checkpid
      if [ $psid -eq 0 ]; then
        echo "[OK]"
      else
        echo "[Failed]"
      fi
   fi
}

status() {
   checkpid

   if [ $psid -ne 0 ];  then
      echo "$APP is running! (pid=$psid)"
   else
      echo "$APP is not running"
   fi
}

info() {
   echo "Information:"
   echo "****************************"
   echo "Project Name = $PROJECT"
   echo "Application Name = $APP"
   echo "Application Debug Port = $DEBUG_PORT"
   echo "****************************"
}


###################################
#读取脚本的第一个参数($1)，进行判断
#参数取值范围：{start|stop|restart|status|info}
#如参数不在指定范围之内，则打印帮助信息
###################################
case "$1" in
   'start')
      start
      ;;
   'stop')
     stop
     ;;
   'restart')
     stop
     start
     ;;
   'status')
     status
     ;;
   'info')
     info
     ;;
  *)
     echo "Usage: $0 {start|stop|restart|status|info}"
     exit 1
esac