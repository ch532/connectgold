#!/bin/sh
##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Locate Java
if [ -z "$JAVA_HOME" ]; then
  JAVA_EXE=$(which java)
else
  JAVA_EXE="$JAVA_HOME/bin/java"
fi

if [ ! -x "$JAVA_EXE" ]; then
  echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH."
  exit 1
fi

# Determine the directory of this script
DIR="$( cd "$( dirname "$0" )" && pwd )"

# Execute Gradle wrapper JAR
exec "$JAVA_EXE" -cp "$DIR/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
