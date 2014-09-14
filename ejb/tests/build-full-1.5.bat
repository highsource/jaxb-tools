setlocal
set JAVA_HOME=%JAVA5_HOME%
call mvn -X -Ptests-a-n,tests-o-z clean install >std 2>err
endlocal