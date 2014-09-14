setlocal
set JAVA_HOME=%JAVA5_HOME%
call mvn -X -DperformRelease -DcontinuousIntegrationInstall clean install >std 2>err
endlocal