setlocal
echo Setting JAVA_HOME to %JAVA8_HOME%.
set JAVA_HOME=%JAVA8_HOME%
call mvn clean install -Pall,sonatype-oss-release -DperformRelease --fail-at-end
endlocal