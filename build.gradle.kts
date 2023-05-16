plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    // You can run your app via task "run": ./gradlew run
    application

    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

repositories { // Where to search for dependencies
    mavenCentral()
}

dependencies {
    // Suppressions for SpotBugs
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.7.3")

    // to import JSON
    implementation("com.googlecode.json-simple:json-simple:1.1.1")

    // for image manipulation
    implementation("net.coobird:thumbnailator:0.4.6")

    // for hibernate
    implementation("org.hibernate:hibernate-core:6.2.2.Final")
}

application {
    // Define the main class for the application.
    mainClass.set("it.unibo.caesena.application.App")
}
