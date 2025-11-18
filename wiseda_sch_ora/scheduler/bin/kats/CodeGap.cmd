@ECHO OFF

SETLOCAL

@REM Initialize the common environment.

call C:/IDE/workspace-nhic/wiseda_scheduler/scheduler/bin/setenv.cmd

%JAVA_HOME%/bin/java -Xms128m -Xmx512m -cp %SCHEDULER_LIB% kr.wise.meta.code.CodeManager %1

ENDLOCAL
