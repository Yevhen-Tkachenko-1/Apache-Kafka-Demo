# Apache-Kafka-Demo

Learn and play with Apache Kafka

Implemented based on LinkedIn Learning
course: [Complete Guide to Apache Kafka for Beginners](https://www.linkedin.com/learning/complete-guide-to-apache-kafka-for-beginners)
with related GitHub repository: [conduktor/kafka-beginners-course](https://github.com/conduktor/kafka-beginners-course)

* [Tech Stack](#tech-stack)
* [Short Theory](#short-theory)
* [Software Preparation (Windows OS)](#software-preparation-windows-os)
* [Start Kafka](#start-kafka)
* [Module 1: Basics](#module-1-basics)
* [Module 2: Pet Project](#module-2-pet-project)

## Tech Stack

- Apache Kafka
- Apache ZooKeeper
- Apache Kafka CLI
- Apache Kafka SDK
- Java 17
- Gradle
- Windows 10
- Windows Subsystem for Linux (WSL)
- IntelliJ IDEA (Community edition)

## Short Theory

- **Kafka Cluster** - entire service within microservice app.
- **Topic** - logical part of Kafka Cluster for Event processing.
  Event is some sort of data, e.g. Temperature changing over the time collected by City sensors.
- **Partition** - physical and logical part of Topic.
  When Events come to Topic they are distributed across several Partitions.
  Different Partitions may be located on the same or different physical machines.
- **Kafka Broker** - physical part of Kafka Cluster.
  Only one Broker can be on the same physical machine.
  Broker may contain different Partitions from different Topics.
- **Throughput** - thanks to Topic Partitioning,
  we can have several Clients that use the same Topic, but consume their specific data simultaneously.
- **Scalability** - we can increase performance horizontally by adding new Kafka Brokers
  and rebalancing Partitions.
- **Durability** - each Partition of each Topic is replicated in different Brokers.
  In case one Broker is down, we can continue work with other replicas that are in live Brokers.
  Event is sent to a Leader replica.
  Event may be read from any replica of given Partition.

## Software Preparation (Windows OS)

First, we have to set up WSL and install Kafka and ZooKeeper servers on our machine.

Follow this
instruction: [How to Install Apache Kafka on Windows?](https://www.conduktor.io/kafka/how-to-install-apache-kafka-on-windows/)

Used version `kafka_2.13-3.7.0`

Additionally, we have to set up `listeners` value in `~/kafka_2.13-3.7.0/config/server.properties` file:

run `nano ~/kafka_2.13-3.7.0/config/server.properties`

find, uncomment and update `listeners` value to `PLAINTEXT://[::1]:9092`

We will use it as `--bootstrap-server` for Kafka CLI and `bootstrap.servers` for Java SDK.

## Start Kafka

Start local services for Zookeeper and Kafka:

In first Ubuntu window run `zookeeper-server-start.sh ~/kafka_2.13-3.7.0/config/zookeeper.properties`

In second Ubuntu window run `kafka-server-start.sh ~/kafka_2.13-3.7.0/config/server.properties`

## Module 1: Basics

There are common concepts of Kafka implementation in practice.
We will use command line interface as well as java programming.

Follow this [link](Apache-Kafka-Basics/README.md) to check README file of this module.

## Module 2: Pet Project

There is use case where we play with Kafka in real project scenario.

Follow this [link](Wikimedia-Pet-Project/README.md) to check README file of this module.
