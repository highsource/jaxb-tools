setlocal
echo Setting JAVA_HOME to %JAVA7_HOME%.
set JAVA_HOME=%JAVA7_HOME%
call mvn clean install -Pall -DperformRelease --fail-at-end
endlocal