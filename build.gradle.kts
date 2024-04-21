import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id ("org.springframework.boot") version "3.2.5"
    id ("io.spring.dependency-management") version "1.1.4"
    id ("org.jetbrains.kotlin.jvm") version "1.9.23"
    kotlin("kapt") version "1.9.23"
}

group = "com.seovalue"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
//    kapt("groupId:artifactId:version")
    implementation ("org.springframework.boot:spring-boot-starter")
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // xml
    implementation ("com.tickaroo.tikxml:retrofit-converter:0.8.13")
    implementation ("com.tickaroo.tikxml:annotation:0.8.13")
    implementation ("com.tickaroo.tikxml:core:0.8.13")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.2")

    kapt ("com.tickaroo.tikxml:processor:0.8.13")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}
