plugins {
    java
    kotlin("jvm") version "1.4.30-M1"
}

group = "com.iantorno"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    // Apache POI - Java API To Access Microsoft Format Files
    implementation ("org.apache.poi:poi-ooxml:5.0.0")
    // Kotlin Reflect
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.10")
    // The Simple Logging Facade for Java (SLF4J)
    implementation ("org.slf4j:slf4j-api:1.7.30")
    // logback classic
    implementation ("ch.qos.logback:logback-classic:1.2.3")
    // logback core
    implementation ("ch.qos.logback:logback-core:1.2.3")

    testImplementation(platform("org.junit:junit-bom:5.7.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}