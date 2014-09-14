rem update versions
rem call mvn clean install
rem call mvn clean install -Psamples
rem call mvn clean install -Ptests
rem call mvn clean install -Ptemplates
rem call mvn clean install -Ptutorials
rem call mvn clean install -Ptests
rem call mvn clean install -Psamples
rem call mvn clean install -Ptemplates
rem call mvn clean install -Ptutorials
rem mvn scm:tag -Dtag=%1
rem mvn -Psamples -Ptests -DperformRelease -Psonatype-oss-release clean deploy
rem update versions
call mvn -DperformRelease -Psonatype-oss-release clean deploy
call mvn -Ptests -DperformRelease -Psonatype-oss-release clean deploy
call mvn -Psamples -DperformRelease -Psonatype-oss-release clean deploy
call mvn -Ptemplates -DperformRelease -Psonatype-oss-release clean deploy
call mvn -Ptutorials -DperformRelease -Psonatype-oss-release clean deploy
