FROM uhopper/hadoop-spark:2.0.2_2.7.2

RUN apt-get install -y openjdk-8-jdk

COPY dockerfiles/apache-mahout-distribution-0.13.0 /opt/apache-mahout
ENV MAHOUT_HOME=/opt/apache-mahout
ENV PATH=$PATH:$MAHOUT_HOME/bin
