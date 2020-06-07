import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("idea")
	id("java")
	id("groovy")
	id("application")
	id("org.springframework.boot") version "2.3.0.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("kapt") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
}

group = "io.spring.start.kotlin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	///// kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	///// spring
	/// actuator
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	/// aop
	implementation("org.springframework.boot:spring-boot-starter-aop")
	/// data
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	/// jdbc
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	/// mybatis
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.3")
	/// security
	implementation("org.springframework.boot:spring-boot-starter-security")
	/// web
	implementation("org.springframework.boot:spring-boot-starter-web")
	/// validation
	implementation("org.springframework.boot:spring-boot-starter-validation")
	/// other
	// flyway
	implementation("org.flywaydb:flyway-core")
	// jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	/// for dev
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	///// library
	/// database
	implementation("mysql:mysql-connector-java:8.0.20")
	implementation("mysql:mysql-connector-java:8.0.20")
	///// test
	/// spock
	testImplementation("org.codehaus.groovy:groovy-all:2.5.12")
	testImplementation("org.spockframework:spock-core:1.3-groovy-2.5")
	testImplementation("org.spockframework:spock-spring:1.3-groovy-2.5")
	// allows mocking of classes
	testImplementation("net.bytebuddy:byte-buddy:1.10.10")
	// allows mocking of classes without default constructor
	testImplementation("org.objenesis:objenesis:3.1")
	testImplementation("cglib:cglib-nodep:3.2.12")
	/// spring boot
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		//exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	/// spring security
	testImplementation("org.springframework.security:spring-security-test")
	/// instead of mysql
	testImplementation("com.h2database:h2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
