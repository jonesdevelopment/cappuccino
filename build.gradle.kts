plugins {
  id("java")
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

apply(plugin = "java")
apply(plugin = "com.github.johnrengelman.shadow")

version = "0.1.0"

repositories {
  mavenCentral() // Lombok
}

dependencies {
  compileOnly("org.projectlombok:lombok:1.18.28")
  annotationProcessor("org.projectlombok:lombok:1.18.28")

  testCompileOnly("org.projectlombok:lombok:1.18.28")
  testAnnotationProcessor("org.projectlombok:lombok:1.18.28")
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

tasks {
  compileJava {
    options.encoding = "UTF-8"
  }

  shadowJar {
    archiveFileName.set("${rootProject.name}-${version}-SNAPSHOT.jar")
  }

  // This is a small wrapper tasks to simplify the building process
  register("build-cappuchino") {
    dependsOn("clean", "shadowJar")
  }
}
