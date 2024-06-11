plugins {
    id("java")
}

group = "yevhent.demo.kafka"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
    implementation("org.apache.kafka:kafka-clients:3.7.0")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.13")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:2.0.13")

    // https://mvnrepository.com/artifact/com.squareup.okhttp/okhttp
    implementation("com.squareup.okhttp:okhttp:2.7.5")
    // https://mvnrepository.com/artifact/com.launchdarkly/okhttp-eventsource
    implementation("com.launchdarkly:okhttp-eventsource:4.1.1")

    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20240303")
}

tasks.test {
    useJUnitPlatform()
}