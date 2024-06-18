plugins {
    id("java")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}