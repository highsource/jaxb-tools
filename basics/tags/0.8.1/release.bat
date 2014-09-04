setlocal
echo Setting JAVA_HOME to %JAVA6_HOME%.
set JAVA_HOME=%JAVA6_HOME%

echo Performing a short clean build.
pause
call mvn clean install -DperformRelease
echo Short clean build completed.
pause

echo Performing a full clean build.
pause
call mvn clean install -DperformRelease -Psamples,tests,dist
echo Full clean build completed.
pause

echo Setting new version to %1.
pause
call mvn versions:set -Psamples,tests,dist -DnewVersion=%1
echo Version was set to %1.
pause
call mvn versions:commit -Psamples,tests,dist
echo Version %1 committed.
pause

echo Performing a short clean build.
pause
call mvn clean install -DperformRelease
echo Short clean build completed.
pause

echo Performing a full clean build.
pause
call mvn clean install -Psamples,tests,dist -DperformRelease
echo Full clean build completed.
pause

echo Checking in version %1.
pause
call mvn scm:checkin -Dmessage="Version %1"
echo Version %1 was checked in.
pause

echo Tagging version %1.
pause
call mvn scm:tag -Dtag=%1
echo Version %1 was tagged.
pause

echo Performing full clean deploy.
pause
call mvn -DperformRelease -Psonatype-oss-release,samples,tests,dist clean deploy
echo Full clean deploy done.
pause

echo Setting new version to %2.
pause
call mvn versions:set -Psamples,tests,dist -DnewVersion=%2
echo Version was set to %2.
pause
call mvn versions:commit -Psamples,tests,dist
echo Version %2 was committed.
pause

echo Performing a short clean build.
pause
call mvn clean install -DperformRelease
pause

echo Checking in version %2.
pause
call mvn scm:checkin -Dmessage="Version %2"
echo Version %2 was checked in.
pause

endlocal