#!/bin/bash

hadoop fs -rm -r output
hadoop fs -put -f /root/data/patterns.txt patterns.txt

hadoop jar /root/data/wc.jar WordCount2 input output -skip patterns.txt

echo "To see ouput, run:"
echo "hadoop fs -cat output/part-r-00000"
