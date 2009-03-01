setlocal
set JAVA_HOME=%JAVA5_HOME%
call mvn -X -DcontinuousIntegrationInstall clean install >std 2>err
endlocal