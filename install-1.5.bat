setlocal
set JAVA_HOME=%JAVA5_HOME%
call mvn -X -Dsamples,templates,tests clean install >std 2>err
endlocal