plugins {
    java
    id("maven-publish")
}

group = "com.noximity"
version = "1.0.0"
description = "Framework is a library made by Noximity that is inside all of Noximity plugins."

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
    compileOnly("io.github.waterfallmc:waterfall-api:1.19-R0.1-SNAPSHOT")
    implementation("commons-io:commons-io:2.13.0")
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("mysql:mysql-connector-java:8.0.33")
}

tasks {
    withType<Jar> {
        manifest {
            attributes["name"] = project.name
            attributes["version"] = project.version
            attributes["description"] = project.description
            attributes["apiVersion"] = "1.0.0"
        }
    }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
    }
}