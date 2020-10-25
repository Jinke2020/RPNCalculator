@ECHO OFF
SETLOCAL
mvn clean package && xcopy /s .\target\RPNCalculator-1.0-RELEASE.jar .\bin\ /Y
ENDLOCAL