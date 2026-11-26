plugins {
	kotlin("jvm") version "2.4.21"
	kotlin("plugin.spring") version "2.4.21"
	id("org.springframework.boot") version "4.1.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("info.solidsoft.pitest") version "1.19.0"
}

group = "io.github.bangddong"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("tools.jackson.module:jackson-module-kotlin")
	testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property", "-Xemit-jvm-type-annotations")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// 뮤테이션 테스트 (competencies.md §5 「뮤테이션 점수」, 시험 중). check 에 묶지 않는다 — 수동 `./gradlew pitest`.
// 리포트: build/reports/pitest/index.html
pitest {
	pitestVersion = "1.30.2"
	junit5PluginVersion = "1.2.3"
	targetClasses = setOf("io.github.bangddong.ai.*")
	excludedClasses = setOf("io.github.bangddong.ai.AiApplication*")
	threads = 4
	outputFormats = setOf("HTML", "XML")
	timestampedReports = false
}
