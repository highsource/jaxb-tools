@REM
@REM Copyright © 2005-2015, Alexey Valikov
@REM All rights reserved.
@REM
@REM Redistribution and use in source and binary forms, with or without
@REM modification, are permitted provided that the following conditions are met:
@REM
@REM 1. Redistributions of source code must retain the above copyright notice, this
@REM    list of conditions and the following disclaimer.
@REM 2. Redistributions in binary form must reproduce the above copyright notice,
@REM    this list of conditions and the following disclaimer in the documentation
@REM    and/or other materials provided with the distribution.
@REM
@REM THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
@REM ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
@REM WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
@REM DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
@REM ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
@REM (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
@REM LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
@REM ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
@REM (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
@REM SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
@REM
@REM The views and conclusions contained in the software and documentation are those
@REM of the authors and should not be interpreted as representing official policies,
@REM either expressed or implied, of the FreeBSD Project.
@REM

setlocal
echo Setting JAVA_HOME to %JAVA6_HOME%.
set JAVA_HOME=%JAVA6_HOME%

echo Performing a full clean build.
rem pause
call mvn clean install -DperformRelease -Pall
echo Full clean build completed.
rem pause

echo Setting new version to %1.
rem pause
call mvn versions:set -Pall -DnewVersion=%1
echo Version was set to %1.
rem pause
call mvn versions:commit -Pall
echo Version %1 committed.
rem pause

echo Performing a full clean build.
rem pause
call mvn clean install -Pall -DperformRelease
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
call mvn -DperformRelease -Psonatype-oss-release,all clean deploy
echo Full clean deploy done.
rem pause

echo Setting new version to %2.
rem pause
call mvn versions:set -Pall -DnewVersion=%2
echo Version was set to %2.
rem pause
call mvn versions:commit -Pall
echo Version %2 was committed.
rem pause

echo Performing a full clean build.
rem pause
call mvn clean install -DperformRelease -Pall
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