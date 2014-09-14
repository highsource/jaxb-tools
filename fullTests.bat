setlocal
set JAVA_HOME=%JAVA5_HOME%
mvn -DcontinuousIntegrationInstall clean install >std 2>err
endlocal