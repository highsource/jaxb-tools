setlocal
echo Setting JAVA_HOME to %JAVA10_HOME%.
set JAVA_HOME=%JAVA10_HOME%
call mvn clean install -Pall,sonatype-oss-release -DperformRelease
endlocal