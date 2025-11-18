﻿@ECHO OFF
SETLOCAL

set NLS_LANG=AMERICAN_AMERICA.KO16KSC5601

call C:/ide/basemeta_postgresql/wiseda_sch_ora/scheduler/bin/setenv.cmd

set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/commons-dbcp-1.3.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/commons-pool-1.5.4.jar
 
@REM you'll need to set this to the absolute path to your quartz.jar file...
set RMI_CODEBASE=file:%SCHEDULER_HOME%/lib/quartz-all-1.8.4.jar


IF "%1%" == "" goto default
IF "%1%" == "START" goto start
IF "%1%" == "STOP" goto stop

:start
@echo "Quartz Server Start"
%JAVA_HOME%\bin\java -Xms1024m -Xmx1024m -DQuartz -cp %SCHEDULER_LIB% -Djava.rmi.server.codebase=%RMI_CODEBASE% -Djava.security.policy=scheduler.policy -Dorg.quartz.properties=scheduler.properties org.quartz.impl.QuartzServer
goto :finish

:stop
@echo "Quartz Server Stop"
%JAVA_HOME%\bin\java -DQuartz -cp %SCHEDULER_LIB% kr.wise.scheduler.DAScheduler SHUTDOWN
goto :finish

:default
@echo "[WARN] Parameter가 없습니다"
goto :finish

:finish

ENDLOCAL