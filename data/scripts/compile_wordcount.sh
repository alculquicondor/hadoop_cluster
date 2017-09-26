#!/bin/bash

export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar

cd /root/data

hadoop com.sun.tools.javac.Main WordCount2.java
rm wc.jar
jar cf wc.jar WordCount2*.class
rm WordCount2*.class
