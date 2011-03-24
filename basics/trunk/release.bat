setlocal
set JAVA_HOME=%JAVA5_HOME%
call mvn -DperformRelease=true clean deploy
endlocal
