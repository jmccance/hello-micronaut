import org.gradle.api.tasks.wrapper.Wrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    id("org.jetbrains.kotlin.kapt").version("1.3.21")
    id("org.jetbrains.kotlin.plugin.allopen").version("1.3.21")
    id("com.github.johnrengelman.shadow").version("4.0.2")
    id("application")
}



group = "hello.world"
version = "0.1"

val developmentOnly by configurations.creating

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(platform("io.micronaut:micronaut-bom:1.1.0"))
    kapt(platform("io.micronaut:micronaut-bom:1.1.0"))

    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")
    kapt("io.micronaut:micronaut-tracing")
    kaptTest("io.micronaut:micronaut-inject-java")

    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${extra["kotlinVersion"]}")
    compile("org.jetbrains.kotlin:kotlin-reflect:${extra["kotlinVersion"]}")
    compile("io.micronaut:micronaut-runtime")
    compile("io.micronaut:micronaut-http-client")
    compile("javax.annotation:javax.annotation-api")
    compile("io.micronaut:micronaut-http-server-netty")

    runtime("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    runtime("ch.qos.logback:logback-classic:1.2.3")

    testCompile("org.junit.jupiter:junit-jupiter-api")
    testCompile("org.jetbrains.spek:spek-api:1.1.5")
    testRuntime("org.junit.jupiter:junit-jupiter-engine")
    testRuntime("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
}

application {
    mainClassName = "hello.world.Application"
}

allOpen {
    annotation("io.micronaut.aop.Around")
}

tasks {
    named<JavaExec>("run") {
        classpath += developmentOnly
        jvmArgs = listOf("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
    }

    test {
        useJUnitPlatform()

        classpath += developmentOnly
    }

    wrapper {
        gradleVersion = "5.4.1"
        distributionType = Wrapper.DistributionType.ALL
    }
}
//mainClassName = "hello.world.Application"


tasks.withType<KotlinCompile> {
    	kotlinOptions {
	    jvmTarget = "1.8"
        //Will retain parameter names for Java reflection
	    javaParameters = true
	}
}

//shadowJar {
//    mergeServiceFiles()
//}
//
//run.classpath += configurations.developmentOnly
//run.jvmArgs("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
