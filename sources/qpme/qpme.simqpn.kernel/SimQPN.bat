@echo OFF

set ECLIPSEHOME=.
 
:: get path to equinox jar inside ECLIPSEHOME folder
for /f "delims= tokens=1" %%c in ('dir /B /S /OD %ECLIPSEHOME%\plugins\org.eclipse.equinox.launcher_*.jar') do set EQUINOXJAR=%%c
 

if (%1)==(-l) GOTO list
if (%1)==(-r) GOTO run
GOTO syntaxerror

:list
if (%2)==() GOTO syntaxerror
if (%2)==(-r) GOTO listrun
if NOT exist %~f2 GOTO filenotfound 
java -server -jar %EQUINOXJAR% -consoleLog -application qpme.simqpn.kernel.Simulator -l %~f2

GOTO end

:run
if (%2)==() GOTO syntaxerror
if (%3)==() GOTO syntaxerror
if NOT exist %~f3 GOTO filenotfound
java -server -jar %EQUINOXJAR% -consoleLog -application qpme.simqpn.kernel.Simulator -r %2 %~f3
GOTO end

:listrun
if (%3)==() GOTO syntaxerror
if (%4)==() GOTO syntaxerror
if NOT exist %~f4 GOTO filenotfound
java -server -jar %EQUINOXJAR% -consoleLog -application qpme.simqpn.kernel.Simulator -l -r %3 %~f4
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
