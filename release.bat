setlocal
echo Setting JAVA_HOME to %JAVA6_HOME%.
set JAVA_HOME=%JAVA6_HOME%

echo Performing a short clean build.
pause
call mvn clean install
echo Short clean build completed.
pause

echo Performing a full clean build.
pause
call mvn clean install -Ptests,samples,tutorials,templates
pause
call mvn clean install -Ptests-0
pause
call mvn clean install -Ptests-1
pause
call mvn clean install -Ptests-2
echo Full clean build completed.
pause

echo Setting new version to %1.
pause
call mvn versions:set -Ptests,tests-0,tests-1,tests-2,samples,tutorials,templates,versions -DnewVersion=%1
echo Version was set to %1.
pause
call mvn versions:commit -Ptests,tests-0,tests-1,tests-2,samples,tutorials,templates,versions
echo Version %1 committed.
pause

echo Performing a short clean build.
pause
call mvn clean install
echo Short clean build completed.
pause

echo Performing a full clean build.
pause
call mvn clean install -Ptests,samples,tutorials,templates
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
call mvn -DperformRelease -Psonatype-oss-release clean deploy
echo Full clean deploy done.
pause

echo Setting new version to %2.
pause
call mvn versions:set -Ptests,tests-0,tests-1,tests-2,samples,tutorials,templates,versions -DnewVersion=%2
echo Version was set to %2.
pause
call mvn versions:commit -Ptests,tests-0,tests-1,tests-2,samples,tutorials,templates,versions
echo Version %2 was committed.
pause

echo Performing a short clean build.
pause
call mvn clean install
echo Short clean build completed.
pause

echo Performing a full clean build.
pause
call mvn clean install -Ptests,samples,tutorials,templates
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