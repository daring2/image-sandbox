@echo off

set JvmOpts=-Xmx256m
"jre/bin/java.exe" -cp "config;lib/*" %JvmOpts% %*