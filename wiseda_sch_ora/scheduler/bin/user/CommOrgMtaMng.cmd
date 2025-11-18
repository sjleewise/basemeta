@ECHO OFF

SETLOCAL

@REM Initialize the common environment.

call C:/ide/mois/wiseda_sch/scheduler/bin/setenv.cmd

%JAVA_HOME%/bin/java -Xms128m -Xmx512m -cp %SCHEDULER_LIB%  kr.wise.commons.user.CommOrgMtaMng %1

ENDLOCAL
