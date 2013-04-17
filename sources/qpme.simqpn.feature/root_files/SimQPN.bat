@echo OFF

:: tries to use the default JAVA, but in addition tries to identify JAVA by
:: qualifying JAVA_HOME
set JAVA=java

IF EXIST %JAVA_HOME% (
	set JAVA=%JAVA_HOME%\bin\java.exe
)

IF NOT EXIST "%JAVA%" (
	echo Please check your Java installation or set JAVA_HOME properly
	GOTO end
)

:: some JAVA_OPTS, which can be extended by setting JAVA_OPTS in the environment
set JAVA_OPTS=-server

:: set path to eclipse folder. If the same folder as this script, use the default; otherwise, use /path/to/eclipse/
set ECLIPSEHOME=.
 
:: get path to equinox jar inside ECLIPSEHOME folder
for /f "delims= tokens=1" %%c in ('dir /B /S /OD %ECLIPSEHOME%\plugins\org.eclipse.equinox.launcher_*.jar') do set EQUINOXJAR=%%c

set ECLIPSE_ARGS=-consoleLog -application qpme.simqpn.kernel.Simulator
 
"%JAVA%" %JAVA_OPTS% -jar %EQUINOXJAR% %ECLIPSE_ARGS% %*

:end
