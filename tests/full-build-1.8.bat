setlocal
echo Setting JAVA_HOME to %JAVA8_HOME%.
set JAVA_HOME=%JAVA8_HOME%
call mvn clean install -Pall -DperformRelease --fail-at-end
endlocal