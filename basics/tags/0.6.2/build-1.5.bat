setlocal
set JAVA_HOME=%JAVA5_HOME%
call mvn clean install >std 2>err
endlocal