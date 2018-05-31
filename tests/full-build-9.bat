setlocal
echo Setting JAVA_HOME to %JAVA9_HOME%.
set JAVA_HOME=%JAVA9_HOME%
call mvn clean install -Pall,sonatype-oss-release -DperformRelease --fail-at-end
endlocal