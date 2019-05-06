import org.gradle.api.tasks.wrapper.Wrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application

    id("org.jetbrains.kotlin.jvm").version("1.3.31")
    id("org.jetbrains.kotlin.kapt").version("1.3.31")
    id("org.jetbrains.kotlin.plugin.allopen").version("1.3.31")
    id("com.github.johnrengelman.shadow").version("4.0.2")
}


group = "hello.world"
version = "0.1"

val developmentOnly by configurations.creating

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile(platform("io.micronaut:micronaut-bom:1.1.0"))
    compile(platform("org.jdbi:jdbi3-bom:3.8.2"))
    kapt(platform("io.micronaut:micronaut-bom:1.1.0"))

    kapt("io.micronaut", "micronaut-inject-java")
    kapt("io.micronaut", "micronaut-validation")

    compile("io.jaegertracing", "jaeger-thrift", "0.31.0")
    compile("io.micronaut", "micronaut-http-client")
    compile("io.micronaut", "micronaut-http-server-netty")
    compile("io.micronaut", "micronaut-management")
    compile("io.micronaut", "micronaut-runtime")
    compile("io.micronaut", "micronaut-tracing")
    compile("io.micronaut.configuration", "micronaut-liquibase")
    compile("io.micronaut.configuration", "micronaut-micrometer-core")
    compile("io.micronaut.configuration", "micronaut-micrometer-registry-prometheus")
    compile("javax.annotation", "javax.annotation-api")
    compile("org.jdbi", "jdbi3-core")
    compile("org.jdbi", "jdbi3-kotlin")
    compile("org.jetbrains.kotlin", "kotlin-reflect", "${extra["kotlinVersion"]}")
    compile("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", "${extra["kotlinVersion"]}")
    compile("org.postgresql", "postgresql", "42.2.5")

    runtime("ch.qos.logback", "logback-classic", "1.2.3")
    runtime("com.fasterxml.jackson.module", "jackson-module-kotlin", "2.9.8")
    runtime("io.micronaut.configuration", "micronaut-jdbc-hikari")

    kaptTest("io.micronaut", "micronaut-inject-java")

    testCompile("org.jetbrains.spek", "spek-api", "1.1.5")
    testCompile("org.junit.jupiter", "junit-jupiter-api")
    testRuntime("org.jetbrains.spek", "spek-junit-platform-engine", "1.1.5")
    testRuntime("org.junit.jupiter", "junit-jupiter-engine")
}

allOpen {
    annotation("io.micronaut.aop.Around")
}

application {
    mainClassName = "hello.world.Application"
}

tasks {
    // Parens required to prevent the compiler from resolving this to the `kotlin.run` extension
    // method.
    (run) {
        classpath += developmentOnly
        jvmArgs = listOf("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
    }

    shadowJar {
        mergeServiceFiles()
    }

    test {
        useJUnitPlatform()

        classpath += developmentOnly
    }

    wrapper {
        gradleVersion = "5.4.1"
        distributionType = Wrapper.DistributionType.ALL
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            //Will retain parameter names for Java reflection
            javaParameters = true
        }
    }
}

