setlocal
set JAVA_HOME=%JAVA5_HOME%
call mvn -X -DprepareRelease -DcontinuousIntegrationDeploy clean deploy >std 2>err
endlocal