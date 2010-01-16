@echo OFF

set CLASSPATH="lib/colt.jar;lib/dom4j-1.6.1.jar;lib/jaxen-1.1-beta-6.jar;bin/classes/"

if (%1)==(-l) GOTO list
if (%1)==(-r) GOTO run
GOTO syntaxerror

:list
if (%2)==() GOTO syntaxerror
if (%2)==(-r) GOTO listrun
if NOT exist %~f2 GOTO filenotfound 
java -classpath %CLASSPATH% -server de.tud.cs.simqpn.console.SimQPN -l %~f2

GOTO end

:run
if (%2)==() GOTO syntaxerror
if (%3)==() GOTO syntaxerror
if NOT exist %~f3 GOTO filenotfound
java -classpath %CLASSPATH% -server de.tud.cs.simqpn.console.SimQPN -r %2 %~f3
GOTO end

:listrun
if (%3)==() GOTO syntaxerror
if (%4)==() GOTO syntaxerror
if NOT exist %~f4 GOTO filenotfound
java -classpath %CLASSPATH% -server de.tud.cs.simqpn.console.SimQPN -l -r %3 %~f4
GOTO end
 
:filenotfound
echo Syntax: SimQPN.bat [-l] [-r "configuration name"] qpe-file
echo Error: File not found!

GOTO end

:syntaxerror
echo Syntax: SimQPN.bat [-l] [-r "configuration name"] qpe-file
echo Error: Invalid parameters specified!
GOTO end


:end
