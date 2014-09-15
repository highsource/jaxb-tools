setlocal
set JAVA_HOME=%JAVA7_HOME%
call mvn -X clean install >std 2>err
endlocal