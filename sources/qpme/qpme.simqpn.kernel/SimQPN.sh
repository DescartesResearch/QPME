#!/bin/sh

# Tries to use the default JAVA, but in addition tries to identify JAVA by
# qualifying JAVA_HOME
JAVA="$JAVA"

if ! [ -z "$JAVA_HOME" ]
then
		  JAVA="$JAVA_HOME/bin/java"
else
		  JAVA="`which java`"
fi

if ! [ -x "$JAVA" ]
then
		  echo "Please check your Java installation or set JAVA_HOME properly"
		  exit 1
fi

# Some JAVA_OPTS, which can be extended by setting JAVA_OPTS in the environment
JAVA_OPTS="$JAVA_OPTS -server -Djava.awt.headless=true"

# set path to eclipse folder. If the same folder as this script, use the default; otherwise, use /path/to/eclipse/
ECLIPSE_HOME=`dirname $BASH_SOURCE`;

# get path to equinox jar inside $ECLIPSE_HOME folder
CLASSPATH=$(find $ECLIPSE_HOME -name "org.eclipse.equinox.launcher_*.jar" | sort | tail -1);

# The main class to be executed
PROGRAM="org.eclipse.equinox.launcher.Main"
ECLIPSE_ARGS="-consoleLog -application qpme.simqpn.kernel.Simulator"

# Do the job
exec $JAVA $JAVA_OPTS -cp $CLASSPATH $PROGRAM ECLIPSE_ARGS $*