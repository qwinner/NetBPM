#!/bin/sh

. ./addtoclasspath.sh
echo $CLASSPATH

java -cp $CLASSPATH org.jbpm.gpd.ProcessDesigner
