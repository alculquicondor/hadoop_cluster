#!/bin/bash

export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar

cd /root/data/invertedIndex

hadoop com.sun.tools.javac.Main WordcountDriver.java
rm ii.jar
jar cf ii.jar WordcountDriver*.class
#rm WordcountDriver*.class
