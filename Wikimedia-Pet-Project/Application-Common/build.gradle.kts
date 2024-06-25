plugins {
    id("java")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {

    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20240303")
}