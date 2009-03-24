setlocal
set JAVA_HOME=%JAVA5_HOME%
call mvn -X -P samples,templates clean install >std 2>err
endlocal