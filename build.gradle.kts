plugins {
    id("java")

}

group = "com.noximity"
version = "1.0.0"
description = "Framework is library made by Noximity that is inside of all of Noximity plugins."

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven ("https://repo.dmulloy2.net/repository/public/")
    maven ("https://oss.sonatype.org/content/groups/public/")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
    compileOnly ("io.github.waterfallmc:waterfall-api:1.19-R0.1-SNAPSHOT")
    implementation("commons-io:commons-io:2.13.0")
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("mysql:mysql-connector-java:8.0.33")

}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
        val props = mapOf(
            "name" to project.name,
            "version" to project.version,
            "description" to project.description,
            "apiVersion" to "1.0.0"
        )
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}