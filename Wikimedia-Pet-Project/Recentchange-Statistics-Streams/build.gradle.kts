plugins {
    id("java")
}

group = "yevhent.project.wikimedia"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-streams
    implementation("org.apache.kafka:kafka-streams:3.7.0")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.13")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:2.0.13")

    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20240303")

    implementation(project(":Wikimedia-Pet-Project:Application-Common"))
}

tasks.test {
    useJUnitPlatform()
}