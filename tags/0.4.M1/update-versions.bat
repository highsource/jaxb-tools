@echo off
for /R %1 %%x in (pom.xml) do if exist %%x call update-version %%x %2 %3
for /R %1 %%x in (.classpath) do if exist %%x call update-version %%x %2 %3
for /R %1 %%x in (.tomcatplugin) do if exist %%x call update-version %%x %2 %3
for /R %1 %%x in (project-pom.xml) do if exist %%x call update-version %%x %2 %3
for /R %1 %%x in (project-build.xml) do if exist %%x call update-version %%x %2 %3
