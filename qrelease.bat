setlocal
echo Setting JAVA_HOME to %JAVA6_HOME%.
set JAVA_HOME=%JAVA6_HOME%

echo Performing a short clean build.
rem pause
call mvn clean install
echo Short clean build completed.
rem pause

echo Performing a full clean build.
rem pause
call mvn clean install -DperformRelease -Ptests,samples
echo Full clean build completed.
rem pause

echo Setting new version to %1.
rem pause
call mvn versions:set -Ptests,samples -DnewVersion=%1
echo Version was set to %1.
rem pause
call mvn versions:commit -Ptests,samples
echo Version %1 committed.
rem pause

echo Performing a short clean build.
rem pause
call mvn clean install
echo Short clean build completed.
rem pause

echo Performing a full clean build.
rem pause
call mvn clean install -Ptests,samples -DperformRelease
echo Full clean build completed.
rem pause

echo Checking in version %1.
rem pause
git commit -a -m "Version %1"
echo Version %1 was checked in.
rem pause

echo Tagging version %1.
rem pause
git tag -a %1 -m "Version %1"
echo Version %1 was tagged.
rem pause

echo Pushing version %1.
rem pause
git push origin master
git push --tags origin master
echo Version %1 was pushed.
rem pause

echo Performing full clean deploy.
rem pause
call mvn -DperformRelease -Psonatype-oss-release,tests,samples clean deploy
echo Full clean deploy done.
rem pause

echo Setting new version to %2.
rem pause
call mvn versions:set -Ptests,samples -DnewVersion=%2
echo Version was set to %2.
rem pause
call mvn versions:commit -Ptests,samples
echo Version %2 was committed.
rem pause

echo Performing a short clean build.
rem pause
call mvn clean install
echo Short clean build completed.
rem pause

echo Performing a full clean build.
rem pause
call mvn clean install -DperformRelease -Ptests,samples
echo Full clean build completed.
rem pause


echo Checking in version %2.
rem pause
git commit -a -m "Version %2"
echo Version %2 was checked in.
rem pause

echo Pushing version %2.
rem pause
git push origin master
git push --tags origin master
echo Version %2 was pushed.
rem pause

endlocal