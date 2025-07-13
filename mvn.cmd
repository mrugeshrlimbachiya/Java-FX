@echo off
set "JAVA_HOME=C:\Program Files\Java\jdk-17"
set "PATH=%JAVA_HOME%\bin;C:\Windows\System32"
call "E:\Softwares\apache-maven-3.9.10\bin\mvn.cmd" %*
