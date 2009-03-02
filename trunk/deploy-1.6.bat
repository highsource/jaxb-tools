setlocal
set JAVA_HOME=%JAVA6_HOME%
call mvn -X -DcontinuousIntegrationDeploy clean install >std 2>err
endlocal