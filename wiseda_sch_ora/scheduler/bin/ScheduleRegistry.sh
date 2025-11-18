#!/usr/bin/sh

. /home/dbmeta/app/apache-tomcat-9.0.72/webapps/wiseda_sch_ora/scheduler/bin/setenv.sh

$JAVA_HOME/bin/java -Xms128m -Xmx512m -cp $SCHEDULER_LIB  kr.wise.scheduler.ScheduleRegistry $1 $2 $3 $4 $5 $6
