@echo off

set JAVA="%JAVA_HOME%\bin\java"
set LIB_DIR=D:\projects\SoC\castor\castor-1.0-src\castor-1.0\lib

set cp=.\classes
for %%i in (%LIB_DIR%\*.jar) do  set cp=%cp%;%%i

set cp=%cp%;.\lib\castor-1.0.jar

%JAVA% -classpath %cp% org.castor.ddl.Main  %*
