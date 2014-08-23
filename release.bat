call mvn versions:set -DnewVersion=%1
pause
call mvn versions:commit
pause
call mvn clean install -DperformRelease
pause
rem  -Psamples -Ptests -Pdist
git commit -a -m "Version %1"
pause
rem call mvn scm:tag -Dtag=%1
git tag -a %1 -m "Version %1"
pause
git push origin master
git push --tags origin master
pause
call mvn -DperformRelease -Psonatype-oss-release clean deploy
pause
call mvn versions:set -DnewVersion=%2
call mvn versions:commit
call mvn clean install -DperformRelease
git commit -a -m="Version %2"
pause