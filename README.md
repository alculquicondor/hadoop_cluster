# Hadoop cluster

An Apache Haddop cluster repository running over Docker. Used for distributed storage and processing of datasets. Tested with two algorithms : Word Count and Inverted Index.


## Building

### Requirements
 - [Docker 1.12.0 or later](https://docs.docker.com/engine/installation/), to create a swarm, deploy application services to a swarm, and manage swarm behavior. 
 - [Docker Compose](https://docs.docker.com/compose/install/), for defining and running multi-container Docker applications.

### Set up Docker Swarm
At least two hosts are required to create the network for the cluster using docker swarm, one for master and others for the slaves.

Initialize the swarm on master, it will generate a random token and open a listening port:
    
    docker swarm init --advertise-addr <MASTER-IP>
Join to the swarm on slaves : 

    docker swarm join --token <TOKEN> <MASTER-IP>:<PORT>
Type to list all the current slaves joined to the swarm :
    
    docker node ls

### Set up Docker 
Edit deploys placements nodes at `docker-compose.yml` to setup the hostname of the master and the slave.  Edit **node.hostname** value for every node in the swarm. 
Use the following tags for the master node *yarnresourcemanager*, *hdfsnamenode*, *runner*. 
For slaves, use tags like *yarnresourcemanager{#}*, *hdfsnamenode{#}*.
Pull the docker images with

    docker-compose pull
## Run
## Start Docker
On master, run the following command:

    docker-compose up

This will create docker containers in the nodes connected to the swarm.
To see webui of every namenode visit [http://<IP>:50070]() in your web browser.
To see webui of every datanode visit [http://<IP>:8020]() in your web browser.
## Test algorithms
To run a Word Count, enter to **hadoop_runner** docker container :

    docker exec -ti <CONTAINER ID> bash
Move to data directory and run the scripts for :
    
    cd /root/data
    ./scripts/initial_setup.sh
    ./scripts/upload_input.sh 
    ./scripts/compile_wordcount.sh 
    ./scripts/run_wordcount.sh 
    
## Authors
This Hadoop cluster application was made at San Pablo Catholic University (Peru) for the Cloud Computing class by:
- [Aldo Culquicondor](https://github.com/alculquicondor)
- [Brenda Solari](https://github.com/brenda151295)
- [Charles](https://github.com/dkred)
- [Daniel Lozano](https://github.com/djdnl13)