@echo off
setlocal

::REQUIRED parameter: Main class

SET VERSION=0.0.1-SNAPSHOT

java -cp target\jdbc-derby-%VERSION%.jar com.lini.jdbc.%1%

endlocal
