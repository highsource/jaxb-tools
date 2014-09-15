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
call mvn clean install -DperformRelease -Ptests,samples,tutorials,templates
echo Full clean build completed.
pause

echo Setting new version to %1.
pause
call mvn versions:set -Ptests,samples,tutorials,templates -DnewVersion=%1
echo Version was set to %1.
pause
call mvn versions:commit -Ptests,samples,tutorials,templates
echo Version %1 committed.
pause

echo Performing a short clean build.
pause
call mvn clean install -DperformRelease
echo Short clean build completed.
pause

echo Performing a full clean build.
pause
call mvn clean install -Ptests,samples,tutorials,templates -DperformRelease
echo Full clean build completed.
pause

echo Checking in version %1.
pause
git commit -a -m "Version %1"
echo Version %1 was checked in.
pause

echo Tagging version %1.
pause
git tag -a %1 -m "Version %1"
echo Version %1 was tagged.
pause

echo Pushing version %1.
pause
git push origin master
git push --tags origin master
echo Version %1 was pushed.
pause

echo Performing full clean deploy.
pause
call mvn -DperformRelease -Psonatype-oss-release,tests,samples,tutorials,templates clean deploy
echo Full clean deploy done.
pause

echo Setting new version to %2.
pause
call mvn versions:set -Ptests,samples,tutorials,templates -DnewVersion=%2
echo Version was set to %2.
pause
call mvn versions:commit -Ptests,samples,tutorials,templates
echo Version %2 was committed.
pause

echo Performing a short clean build.
pause
call mvn clean install -DperformRelease
echo Short clean build completed.
pause

echo Performing a full clean build.
pause
call mvn clean install -DperformRelease -Ptests,samples,tutorials,templates
echo Full clean build completed.
pause


echo Checking in version %2.
pause
git commit -a -m "Version %2"
echo Version %2 was checked in.
pause

echo Pushing version %2.
pause
git push origin master
git push --tags origin master
echo Version %2 was pushed.
pause

endlocal