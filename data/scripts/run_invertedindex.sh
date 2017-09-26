#!/bin/bash

hadoop fs -rm -r output

hadoop jar /root/data/invertedIndex/ii.jar WordcountDriver input output

echo "To see ouput, run:"
echo "hadoop fs -cat output/part-r-00000"
