# RPNCalculator

## Description
### This is command-line based RPN calculator
### Supported operators: +, -, *, /, sqrt, undo, clear


## Environment
### It needs JAVA and Maven(used when packaging) installed in your machine


## How to build
### Run script: ```build.cmd```, or type command: ```mvn clean package && cp .\target\RPNCalculator-1.0-RELEASE.jar .\bin\```


## How to run
### Run script: ```run.cmd```, or type command: ```java -jar -Xmx512m .\bin\RPNCalculator-1.0-RELEASE.jar```

## How to use
### After lanunch the binary, input numbers and operators with whitespace separated, it will process each command line, and type ```exit``` to quit
### Example Input ```1 2 +```
### Example Output ```stack: 3.0000000000 ```
