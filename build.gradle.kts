plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("plugin.serialization") version "1.9.25"
}

group = "HJP"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    //implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")
    implementation("mysql:mysql-connector-java:8.0.33")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //jjwt
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

    // SWAGGER
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    // oauth2
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // jsoup
    implementation("org.jsoup:jsoup:1.15.3")


    // 코루틴
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") // 코루틴 기본 라이브러리
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")

    // ✅ Spring AI OpenAI Starter (OpenAI API 사용)
    implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter:1.0.0-M6")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

}


kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
