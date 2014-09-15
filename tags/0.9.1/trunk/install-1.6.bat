setlocal
set JAVA_HOME=%JAVA6_HOME%
call mvn -DperformRelease -Psonatype-oss-release,samples,tests clean install
endlocal