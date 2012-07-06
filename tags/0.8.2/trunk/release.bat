rem update versions
rem mvn scm:tag -Dtag=%new_version%
rem mvn -Psamples -Ptests -DperformRelease -Psonatype-oss-release clean deploy