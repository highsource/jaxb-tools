setlocal
set JAVA_HOME=%JAVA5_HOME%
call mvn -X -DperformRelease -DcontinuousIntegrationDeploy clean deploy >std 2>err
endlocal