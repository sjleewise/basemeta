#!/usr/bin/sh

. /usr/ssw/wmeta/dqschedule/scheduler/bin/setenv.sh

$JAVA_HOME/bin/java -Xms128m -Xmx512m -cp $SCHEDULER_LIB  kr.wise.meta.code.CodeManager %1
