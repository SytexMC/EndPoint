plugins {
    `java-library`
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("de.eldoria.plugin-yml.paper") version "0.7.1"
    id("com.gradleup.shadow") version "9.0.0-SNAPSHOT"
}

java { toolchain.languageVersion.set(JavaLanguageVersion.of(project.ext.get("javaToolchainVersion") as Int)) }

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(project.ext.get("javaToolchainVersion") as Int)
    }

    javadoc { options.encoding = Charsets.UTF_8.name() }
    processResources { filteringCharset = Charsets.UTF_8.name() }

    runServer {
        minecraftVersion("1.21.7")
        jvmArgs("-Dcom.mojang.eula.agree=true")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")  // PaperMC
    implementation("com.google.code.gson:gson:2.13.1")              // Gson
    implementation("org.reflections:reflections:0.10.2")            // Reflections
    implementation("io.javalin:javalin:6.7.0")                      // Javalin
    compileOnly("org.projectlombok:lombok:1.18.38")                 // Lombok
    annotationProcessor("org.projectlombok:lombok:1.18.38")         // Lombok
}

paper {
    main = "me.sytex.endpoint.EndPoint"

    apiVersion = "1.21"

    foliaSupported = true

    name = "EndPoint"
    description = "Exposes a RESTful API for interacting with Minecraft servers."
    version = project.version as String

    authors = listOf("Sytex")
    contributors = listOf()
}