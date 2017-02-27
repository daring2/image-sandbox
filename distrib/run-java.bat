@echo off

set JvmOpts=-Xmx256m
jre/bin/java.exe -cp "conf;lib/*" %JvmOpts% %*