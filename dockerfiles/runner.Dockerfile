FROM uhopper/hadoop-spark:2.0.2_2.7.2

RUN apt-get install -y openjdk-8-jdk

COPY dockerfiles/apache-mahout-distribution-0.11.0 /opt/apache-mahout-distribution-0.11.0
ENV MAHOUT_HOME=/opt/apache-mahout-distribution-0.11.0
ENV PATH=$PATH:$MAHOUT_HOME/bin
