#!/bin/sh

. ./setenv.sh

SCHEDULER_LIB=$SCHEDULER_LIB:$SCHEDULER_HOME/lib/commons-dbcp-1.3.jar
SCHEDULER_LIB=$SCHEDULER_LIB:$SCHEDULER_HOME/lib/commons-pool-1.5.4.jar

#REM you'll need to set this to the absolute path to your quartz.jar file...
RMI_CODEBASE=file:$SCHEDULER_HOME/lib/quartz-all-1.8.4.jar

case "$1" in
	START)
		echo "START"
		$JAVA_HOME/bin/java -Xms512m -Xmx1024m -DQuartz -cp $SCHEDULER_LIB -Djava.rmi.server.codebase=${RMI_CODEBASE} -Djava.security.policy=scheduler.policy -Dorg.quartz.properties=scheduler.properties org.quartz.impl.QuartzServer
		;;
	STOP)
		echo "STOP"
		$JAVA_HOME/bin/java -DQuartz -cp $SCHEDULER_LIB kr.wise.scheduler.DAScheduler SHUTDOWN
		;;
	*)
		echo "[WARN] Syntax error"
		;;
esac
