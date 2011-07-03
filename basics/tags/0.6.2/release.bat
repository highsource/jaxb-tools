setlocal
set JAVA_HOME=%JAVA5_HOME%
call mvn -Psonatype-oss-release -DperformRelease clean deploy
endlocal
