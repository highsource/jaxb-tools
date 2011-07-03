rem update versions
mvn clean install -Psamples -Ptests
mvn scm:tag -Dtag=%1
mvn -Psamples -Ptests -DperformRelease -Psonatype-oss-release clean deploy
rem update versions