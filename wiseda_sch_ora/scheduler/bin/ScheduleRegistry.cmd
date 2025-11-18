@ECHO OFF

SETLOCAL

@REM Initialize the common environment.

call C:/ide/basemeta_postgresql/wiseda_sch_ora/scheduler/bin/setenv.cmd

%JAVA_HOME%/bin/java -Xms128m -Xmx512m -cp %SCHEDULER_LIB%  kr.wise.scheduler.ScheduleRegistry %1 %2 %3 %4 %5 %6

ENDLOCAL
