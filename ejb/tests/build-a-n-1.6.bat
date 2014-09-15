setlocal
set JAVA_HOME=%JAVA6_HOME%
call mvn -X -Ptests-a-n clean install >std 2>err
endlocal