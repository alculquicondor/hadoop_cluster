hadoop fs -put /root/data/ml-100k/u.data u.data
hadoop fs -rm -r output 
hadoop fs -rm -r temp

mahout recommenditembased -s SIMILARITY_COOCCURRENCE --input u.data --output output
