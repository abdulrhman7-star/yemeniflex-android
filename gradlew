#!/bin/sh
export CLASSPATH="gradle/wrapper/gradle-wrapper.jar"
exec java $JAVA_OPTS $GRADLE_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
