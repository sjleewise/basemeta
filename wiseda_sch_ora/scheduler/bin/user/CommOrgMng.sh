#!/usr/bin/sh

. /GCLOUD/WebApp/deploy/datamgmt_sch/scheduler/bin/setenv.sh

$JAVA_HOME/bin/java -Xms128m -Xmx512m -cp $SCHEDULER_LIB  kr.wise.commons.user.CommOrgMng %1
