# Apache-Kafka-Demo
Learn and play with Apache Kafka

Implemented based on LinkedIn Learning course: [Complete Guide to Apache Kafka for Beginners](https://www.linkedin.com/learning/complete-guide-to-apache-kafka-for-beginners)

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

Follow this instruction: [How to Install Apache Kafka on Windows?](https://www.conduktor.io/kafka/how-to-install-apache-kafka-on-windows/) 

Used version `kafka_2.13-3.7.0`

## Start Kafka

Start local services for Zookeeper and Kafka:

In first Ubuntu window run `zookeeper-server-start.sh ~/kafka_2.13-3.7.0/config/zookeeper.properties`

In second Ubuntu window run `kafka-server-start.sh ~/kafka_2.13-3.7.0/config/server.properties`

## Kafka CLI: Topics

Having Zookeeper and Kafka running, we can create new Topic.

In third Ubuntu window run `kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 1` 

As we are using localhost, `replication-factor` can't be more than 1 (number of server machines). 

Then check Topics, run `kafka-topics.sh --bootstrap-server localhost:9092 --list`

Then describe Topic, run `kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --describe`

Output looks like this:

|                    |                                 |                   |                      |          |
|--------------------|---------------------------------|-------------------|----------------------|----------|
| Topic: first_topic | TopicId: BfQ0D9GXRWufAz0zyzPrhQ | PartitionCount: 3 | ReplicationFactor: 1 | Configs: |

|                    |              |           |             |        |
|--------------------|--------------|-----------|-------------|--------|
| Topic: first_topic | Partition: 0 | Leader: 0 | Replicas: 0 | Isr: 0 |
| Topic: first_topic | Partition: 1 | Leader: 0 | Replicas: 0 | Isr: 0 |
| Topic: first_topic | Partition: 2 | Leader: 0 | Replicas: 0 | Isr: 0 |

## Kafka CLI: Event Sending

Having Topic named `first_topic` we can write Events to cmd. 
To open input, run `kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic`

Now we can send Event by typing text and clicking Enter. Each text line represents 1 Event (message).
To exit input click combination `ctrl+c`.

The same way, we can send Events with Key specified, run `kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --property parse.key=true --property key.separator=:`

So, message format is `key:value`, e.g. `city:Kyiv`

For our case, next messages were entered:

| key  | value         |
|------|---------------|
|      | Hello world 1 |
|      | Yevhen 2      |
|      | See ya 3      |
| name | Yevhen        |
| name | Vasyia        |
| name | Petro         |
| name | Test          |
| city | K             |
| city | Kyiv          |
| city | Lviv          |

## Kafka CLI: Event Receiving

#### Consumer

Having some Events sent to `first_topic` Topic we can read all of them and then start waiting for new messages.

run `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic 
--property print.timestamp=true 
--property print.partition=true 
--property print.offset=true 
--property print.key=true 
--property print.value=true 
--from-beginning`

Output looks like this:

| timestamp                | partition   | offset   | key  | value         |
|--------------------------|-------------|----------|------|---------------|
| CreateTime:1715873430011 | Partition:1 | Offset:0 | name | Yevhen        |
| CreateTime:1715873436869 | Partition:1 | Offset:1 | name | Vasyia        |
| CreateTime:1715873444213 | Partition:1 | Offset:2 | name | Petro         |
| CreateTime:1715873452693 | Partition:1 | Offset:3 | name | Test          |
| CreateTime:1715873458901 | Partition:1 | Offset:4 | city | K             |
| CreateTime:1715873466165 | Partition:1 | Offset:5 | city | Kyiv          |
| CreateTime:1715873470981 | Partition:1 | Offset:6 | city | Lviv          |
| CreateTime:1715872525844 | Partition:2 | Offset:0 | null | Hello world 1 |
| CreateTime:1715872528469 | Partition:2 | Offset:1 | null | Yevhen 2      |
| CreateTime:1715872536453 | Partition:2 | Offset:2 | null | See ya 3      |

In new Ubuntu window we can send new Events one by one, and these messages will appear in output almost immediately.

Let's say now we've entered 

| key  | value         |
|------|---------------|
| name | NewYevhen     |
| text | Hello world 2 |
| text | See ya!       |
| null | Null check    |

So, total output is next:

| timestamp                | partition   | offset   | key  | value         |
|--------------------------|-------------|----------|------|---------------|
| CreateTime:1715873430011 | Partition:1 | Offset:0 | name | Yevhen        |
| CreateTime:1715873436869 | Partition:1 | Offset:1 | name | Vasyia        |
| CreateTime:1715873444213 | Partition:1 | Offset:2 | name | Petro         |
| CreateTime:1715873452693 | Partition:1 | Offset:3 | name | Test          |
| CreateTime:1715873458901 | Partition:1 | Offset:4 | city | K             |
| CreateTime:1715873466165 | Partition:1 | Offset:5 | city | Kyiv          |
| CreateTime:1715873470981 | Partition:1 | Offset:6 | city | Lviv          |
| CreateTime:1715872525844 | Partition:2 | Offset:0 | null | Hello world 1 |
| CreateTime:1715872528469 | Partition:2 | Offset:1 | null | Yevhen 2      |
| CreateTime:1715872536453 | Partition:2 | Offset:2 | null | See ya 3      |
| CreateTime:1715943659435 | Partition:1 | Offset:7 | name | NewYevhen     |
| CreateTime:1715943684511 | Partition:1 | Offset:8 | city | NewKyiv       |
| CreateTime:1715943774864 | Partition:2 | Offset:3 | text | Hello world 2 |
| CreateTime:1715943807390 | Partition:2 | Offset:4 | text | See ya!       |
| CreateTime:1715943820942 | Partition:2 | Offset:5 | null | Null check    |

We can close output by pressing `ctrl+c`.

Let's imagine we need to get all messages starting from point where `city` was received first time.
If we know corresponding `Partition` and `Offset`, we can do like this:

run `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic
--property print.timestamp=true
--property print.partition=true
--property print.offset=true
--property print.key=true
--property print.value=true
--partition 1
--offset 4`

Output looks like this:

| timestamp                | partition   | offset   | key  | value         |
|--------------------------|-------------|----------|------|---------------|
| CreateTime:1715873458901 | Partition:1 | Offset:4 | city | K             |
| CreateTime:1715873466165 | Partition:1 | Offset:5 | city | Kyiv          |
| CreateTime:1715873470981 | Partition:1 | Offset:6 | city | Lviv          |
| CreateTime:1715943659435 | Partition:1 | Offset:7 | name | NewYevhen     |
| CreateTime:1715943684511 | Partition:1 | Offset:8 | city | NewKyiv       |

By default, Events are read from the tail. 
So, if we aren't interested in history, we are free to not specify reading start point.
We can just open output and wait for new Events:

run `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic
--property print.timestamp=true
--property print.partition=true
--property print.offset=true
--property print.key=true
--property print.value=true`

#### Consumer Group

So, what about reading Events only once? Let's try `Consumer Groups`.
When we specify Group, Kafka stores state of Event consuming by this Group.

In first Ubuntu window let's read all Events for `first_microservice` Group:

run `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic 
--group first_microservice 
--property print.partition=true 
--from-beginning`

By this, we got all messages from `first_topic`. Output looks like this:

| partition   | value         |
|-------------|---------------|
| Partition:1 | Yevhen        |
| Partition:1 | Vasyia        |
| Partition:1 | Petro         |
| Partition:1 | Test          |
| Partition:1 | K             |
| Partition:1 | Kyiv          |
| Partition:1 | Lviv          |
| Partition:2 | Hello world 1 |
| Partition:2 | Yevhen 2      |
| Partition:2 | See ya 3      |
| Partition:1 | NewYevhen     |
| Partition:1 | NewKyiv       |
| Partition:2 | Hello world 2 |
| Partition:2 | See ya!       |
| Partition:2 | Null check    |

Now, we're going to add one more Consumer within the same Group.

In second Ubuntu window try to read the same way:

run `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic 
--group first_microservice 
--property print.partition=true 
--from-beginning`

As a result, we got no message, as they were already processed by first Consumer.

Now, we're going to add one more Consumer with the _different_ Group.

In third Ubuntu window try to read: 

run `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic 
--group second_microservice
--property print.partition=true
--from-beginning`

Again, we got all messages from `first_topic` as these messages were not consumed by `second_microservice`. Output looks like this:

| partition   | value         |
|-------------|---------------|
| Partition:1 | Yevhen        |
| Partition:1 | Vasyia        |
| Partition:1 | Petro         |
| Partition:1 | Test          |
| Partition:1 | K             |
| Partition:1 | Kyiv          |
| Partition:1 | Lviv          |
| Partition:2 | Hello world 1 |
| Partition:2 | Yevhen 2      |
| Partition:2 | See ya 3      |
| Partition:1 | NewYevhen     |
| Partition:1 | NewKyiv       |
| Partition:2 | Hello world 2 |
| Partition:2 | See ya!       |
| Partition:2 | Null check    |

Now let's try to send Events on the fly, so we can see How messages are distributed across different Consumers:

In forth Ubuntu window we're going to send Events:

run `kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic 
--producer-property partitioner.class=org.apache.kafka.clients.producer.RoundRobinPartitioner`

Let's send next messages one by one:

| value |
|-------|
| a     |
| b     |
| c     |
| d     |
| e     |
| f     |

For first Consumer within `first_microservice` Group, I got next Events:

| Partition   | value |
|-------------|-------|
| Partition:1 | a     |
| Partition:0 | b     |
| Partition:1 | d     |
| Partition:0 | e     |

For second Consumer within `first_microservice` Group, I got next Events:

| Partition   | value |
|-------------|-------|
| Partition:2 | c     |
| Partition:2 | f     |

For Consumer within `second_microservice` Group, I got next Events:

| Partition   | value |
|-------------|-------|
| Partition:1 | a     |
| Partition:0 | b     |
| Partition:2 | c     |
| Partition:1 | d     |
| Partition:0 | e     |
| Partition:2 | f     |

So, for `first_microservice` Group messages were distributed across 2 Consumers.
Moreover, we see that first Consumer received messages from 2 Partitions,
while second Consumer just from one Partition.
For `second_microservice` Group we got all same Events.

#### Consumer Offset

Now let's check specific properties for Consumer Group status.

In new Ubuntu window we're going to consume all Events with new Group:

run `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic
--group third_microservice
--property print.partition=true
--from-beginning`

Then check offset for this Group:

run `kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group third_microservice`

Output looks like this:

| GROUP              | TOPIC       | PARTITION | CURRENT-OFFSET | LOG-END-OFFSET | LAG | CONSUMER-ID                                           | HOST       | CLIENT-ID        |
|--------------------|-------------|-----------|----------------|----------------|-----|-------------------------------------------------------|------------|------------------|
| third_microservice | first_topic | 0         | 4              | 4              | 0   | console-consumer-61feed63-f17a-4fb5-9b34-4b55fadac1b9 | /127.0.0.1 | console-consumer |
| third_microservice | first_topic | 1         | 17             | 17             | 0   | console-consumer-61feed63-f17a-4fb5-9b34-4b55fadac1b9 | /127.0.0.1 | console-consumer |
| third_microservice | first_topic | 2         | 8              | 8              | 0   | console-consumer-61feed63-f17a-4fb5-9b34-4b55fadac1b9 | /127.0.0.1 | console-consumer |

which means we've consumed all available Events.

Now in sending Ubuntu window we can produce more messages like this:
  
| value |
|-------|
| i     |
| j     |
| k     | 

Let's check offset again:

run `kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group third_microservice`

Output looks like this:

| GROUP              | TOPIC       | PARTITION | CURRENT-OFFSET | LOG-END-OFFSET | LAG | CONSUMER-ID                                           | HOST       | CLIENT-ID        |
|--------------------|-------------|-----------|----------------|----------------|-----|-------------------------------------------------------|------------|------------------|
| third_microservice | first_topic | 0         | 5              | 5              | 0   | console-consumer-61feed63-f17a-4fb5-9b34-4b55fadac1b9 | /127.0.0.1 | console-consumer |
| third_microservice | first_topic | 1         | 18             | 18             | 0   | console-consumer-61feed63-f17a-4fb5-9b34-4b55fadac1b9 | /127.0.0.1 | console-consumer |
| third_microservice | first_topic | 2         | 9              | 9              | 0   | console-consumer-61feed63-f17a-4fb5-9b34-4b55fadac1b9 | /127.0.0.1 | console-consumer |

as we can see, offsets of each Partition is increased, but still we are in sync.

Now let's stop consume, and exit reading output. In sending Ubuntu window produce more messages like this:

| value |
|-------|
| l     |
| m     |
| n     | 
| o     | 
| p     | 
| q     | 
| r     | 

Let's compare offset now:

run `kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group third_microservice`

Output looks like this:

| GROUP              | TOPIC       | PARTITION | CURRENT-OFFSET | LOG-END-OFFSET | LAG | CONSUMER-ID                                           | HOST       | CLIENT-ID        |
|--------------------|-------------|-----------|----------------|----------------|-----|-------------------------------------------------------|------------|------------------|
| third_microservice | first_topic | 0         | 5              | 7              | 2   | console-consumer-61feed63-f17a-4fb5-9b34-4b55fadac1b9 | /127.0.0.1 | console-consumer |
| third_microservice | first_topic | 1         | 18             | 21             | 3   | console-consumer-61feed63-f17a-4fb5-9b34-4b55fadac1b9 | /127.0.0.1 | console-consumer |
| third_microservice | first_topic | 2         | 9              | 11             | 2   | console-consumer-61feed63-f17a-4fb5-9b34-4b55fadac1b9 | /127.0.0.1 | console-consumer |

which means we have some Events to consume, e.g. there are 2 new Messages in Partition 0 and so on.

Additionally, we can check who else consume in our application:

run `kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list`

Output shows all Groups we've specified

| Consumer Group      |
|---------------------|
| second_microservice |
| third_microservice  |
| first_microservice  |

Now let's check how "anonymous" consumer looks like. Open new Ubuntu window for reading:

run `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic`

and check all Groups again:

run `kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list`

Now we see one more _active_ consumer:

| Consumer Group         |
|------------------------|
| console-consumer-58267 |
| second_microservice    |
| third_microservice     |
| first_microservice     |