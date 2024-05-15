# Apache-Kafka-Demo
Learn and play with Apache Kafka

Implemented based on LinkedIn Learning course: [Complete Guide to Apache Kafka for Beginners](https://www.linkedin.com/learning/complete-guide-to-apache-kafka-for-beginners)

## Theory

- **Kafka Cluster** - entire service within microservice app.
- **Topic** - logical part of Kafka Cluster for Event processing.
  Event is some sort of data, e.g. Temperature changing over the time collected by City sensors. 
- **Partition** - physical and logical part of Topic. 
  When Events come to Topic they are distributed across several Partitions. 
  Different Partitions may be located on the same or different physical machines.
- **Broker** - physical part of Kafka Cluster. 
  Only one Broker can be on the same physical machine. 
  May contain different Partitions from different Topics.

## Preparation

First, we have to install Kafka and ZooKeeper servers on our machine.

Follow this instruction: [How to Install Apache Kafka on Windows?](https://www.conduktor.io/kafka/how-to-install-apache-kafka-on-windows/) 

Used `kafka_2.13-3.7.0`

## Start services

Start local services for Zookeeper and Kafka:

In first Ubuntu window run `zookeeper-server-start.sh ~/kafka_2.13-3.7.0/config/zookeeper.properties`

In second Ubuntu window run `kafka-server-start.sh ~/kafka_2.13-3.7.0/config/server.properties`

## Kafka CLI: Topics

Having Zookeeper and Kafka running, we can create new Topic.

In third Ubuntu window run `kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 1` 

As we are using localhost, `replication-factor` can't be more than 1 (number of server machines). 

Then check Topics, run `kafka-topics.sh --bootstrap-server localhost:9092 --list`

Then describe Topic, run `kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --describe`