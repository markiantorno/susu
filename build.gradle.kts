plugins {
    java
    kotlin("jvm") version "1.4.30-M1"
}

group = "ca.miantorno"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    // Apache POI - Java API To Access Microsoft Format Files
    implementation ("org.apache.poi:poi-ooxml:${property("poi-ooxml")}")
    // Kotlin Reflect
    implementation("org.jetbrains.kotlin:kotlin-reflect:${property("kotlin-reflect")}")
    // The Simple Logging Facade for Java (SLF4J)
    implementation ("org.slf4j:slf4j-api:${property("slf4j")}")
    // logback classic
    implementation ("ch.qos.logback:logback-classic:${property("logback-classic")}")
    // logback core
    implementation ("ch.qos.logback:logback-core:${property("logback-core")}")

    testImplementation(platform("org.junit:junit-bom:${property("junit-bom")}"))
    testImplementation("org.junit.jupiter:junit-jupiter:${property("junit-jupiter")}")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}