@echo off
setlocal
if not defined JAVA_HOME (
    if exist "%USERPROFILE%\.jdk\jdk-17.0.20.1+1" (
        set "JAVA_HOME=%USERPROFILE%\.jdk\jdk-17.0.20.1+1"
    )
)
if defined JAVA_HOME (
    set "PATH=%JAVA_HOME%\bin;%PATH%"
)
if defined M2_HOME (
    if exist "%M2_HOME%\bin\mvn.cmd" (
        "%M2_HOME%\bin\mvn.cmd" %*
        exit /b %ERRORLEVEL%
    )
)
if exist "%USERPROFILE%\.maven\apache-maven-3.9.9\bin\mvn.cmd" (
    "%USERPROFILE%\.maven\apache-maven-3.9.9\bin\mvn.cmd" %*
    exit /b %ERRORLEVEL%
)
mvn %*
exit /b %ERRORLEVEL%
