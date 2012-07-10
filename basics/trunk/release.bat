rem update versions
mvn clean install -DperformRelease -Psamples -Ptests -Pdist
mvn scm:tag -Dtag=%1
mvn -Psamples -Ptests -Pdist -DperformRelease -Psonatype-oss-release clean deploy
rem update versions