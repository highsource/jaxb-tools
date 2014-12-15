setlocal
echo Setting JAVA_HOME to %JAVA6_HOME%.
set JAVA_HOME=%JAVA6_HOME%
call mvn clean install -Pall -DperformRelease
endlocal