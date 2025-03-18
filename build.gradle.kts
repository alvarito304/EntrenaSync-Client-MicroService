plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "EntrenaSync"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //logging
    implementation("com.github.diamond-edge.klogging:klogging:0.4.4")
    implementation("org.lighthousegames:logging:2.0.3")
    implementation("ch.qos.logback:logback-classic:1.5.16")

    //mongodb
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    //validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    //api rest
    implementation("org.springframework.boot:spring-boot-starter-web")

    //serialization
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}


tasks.withType<Test> {
    useJUnitPlatform()
}
