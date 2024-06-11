# Wikimedia Pet Project

There is work [branch](https://github.com/Yevhen-Tkachenko-1/Apache-Kafka-Demo/tree/project/wikimedia-recentchange-processor)
for Project implementation.

1. We will use [Wikimedia Recentchange](https://stream.wikimedia.org/v2/stream/recentchange)
   as an Event Source for Kafka.
   There is [implementation](https://github.com/Yevhen-Tkachenko-1/Apache-Kafka-Demo/commit/a250c65850f8c1aa65a9cdb7bdab1811fbc9f0b0)
   of reading Events from Wikimedia by HTTP. 
   For now, we run [java app](src/main/java/yevhent/demo/kafka/producer/WikimediaProducerMicroservice.java)
   for 10 seconds which just prints incoming messages to Log output.
   
   At this point project state and output looks like this:

   ![](picture/1.PNG)

2. Having Kafka services running (as described [here](../README.md))
   let's create new topic `wikimedia.recentchange` for this project:

   run `kafka-topics.sh --bootstrap-server [::1]:9092 --topic wikimedia.recentchange --create --partitions 3 --replication-factor 1`

   then check:

   run `kafka-topics.sh --bootstrap-server [::1]:9092 --list`

   Now, we are ready to Produce Events from Wikimedia to our Kafka server.

   There is current [implementation](https://github.com/Yevhen-Tkachenko-1/Apache-Kafka-Demo/commit/3b0ee6dabebb2a9819874c379e08e2317ff8bf18)

   For now, we run [java app](src/main/java/yevhent/demo/kafka/producer/WikimediaProducerMicroservice.java)
   for 10 seconds which reads Events from Wikimedia by HTTP and produces parsed data to Kafka topic.

   At this point project state and output looks like this:

   ![](picture/2.1.PNG)
   
   Additionally, we can check persistence of Events in Topic using CLI:

   run `kafka-console-consumer.sh --bootstrap-server [::1]:9092 --topic wikimedia.recentchange --property print.partition=true --from-beginning`

   Output looks like this:

   ![](picture/2.2.PNG)

   