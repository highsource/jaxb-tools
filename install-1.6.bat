setlocal
set JAVA_HOME=%JAVA6_HOME%
call mvn clean install
call mvn clean install -Ptests
call mvn clean install -Psamples,tutorials,templates
call mvn clean install -Ptests-0,tests-1,tests-2
endlocal
pause