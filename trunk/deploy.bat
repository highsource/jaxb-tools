setlocal
set JAVA_HOME=%JAVA5_HOME%
call mvn -X clean install >std 2>err
call mvn -X -DprepareRelease -DcontinuousIntegrationDeploy clean deploy &>std 2&>err
endlocal