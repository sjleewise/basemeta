
set JAVA_HOME=C:/ide/Java/jdk1.8.0_112

set SCHEDULER_HOME=C:/ide/postgresql/wiseda_sch_ora/scheduler

set SCHEDULER_LIB=.
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/quartz-all-1.8.4.jar

set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jta-1_1-classes.zip
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jackson-core-2.1.0.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jackson-databind-2.1.0.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jackson-annotations-2.1.0.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/json-simple-1.1.1.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/opencsv-3.8.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jsch-0.1.55.jar

rem logging library
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/slf4j-api-1.7.32.jar

rem logback binding part
rem set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jcl-over-slf4j-1.7.32.jar
rem set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/logback-classic-1.2.9.jar
rem set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/logback-core-1.2.9.jar

rem log4j1 binding part
rem set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/log4j-1.2.14.jar
rem set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/slf4j-api-1.6.0.jar
rem set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/slf4j-log4j12-1.6.0.jar

rem log4j2 binding part
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/log4j-1.2-api-2.17.0.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/log4j-api-2.17.0.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/log4j-core-2.17.0.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/log4j-slf4j-impl-2.17.0.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/log4j-web-2.17.0.jar

rem set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/ojdbc14.jar
rem set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/ojdbc6.jar
rem set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/ojdbc5.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/ojdbc6.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/Altibase.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/jconn3.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/jt400.jar
rem set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/sqljdbc.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/sqljdbc4.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/mariadb-java-client-3.0.6.jar

set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/msbase.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/mssqlserver.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/msutil.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/mysql-connector-java-5.1.14-bin.jar

set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/tera/terajdbc4.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/tera/tdgssconfig.jar

set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/db2jcc4.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/tibero5-jdbc.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/ifxjdbc.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/AS400JDBCDriver-1.0.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/mongo-java-driver-3.2.2.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/tibero6-jdbc.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/postgresql-42.2.25.jar
set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/lib/jdbc/ngdbc-2.3.48.jar

set SCHEDULER_LIB=%SCHEDULER_LIB%;%SCHEDULER_HOME%/classes
