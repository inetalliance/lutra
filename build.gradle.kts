plugins {
    java
    idea
}

subprojects {
    apply(plugin = "java")
    java {
        sourceCompatibility = JavaVersion.VERSION_1_10
        targetCompatibility = JavaVersion.VERSION_1_10
    }
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Werror")
        options.compilerArgs.add("-Xlint")
    }
    apply(plugin = "maven")
    apply(plugin = "idea")
    idea {
        module {
            outputDir = file("build/classes/main")
            testOutputDir = file("build/classes/test")
        }
    }
    repositories {
        mavenLocal()
        mavenCentral()

    }
    group = "net.inetalliance.lutra"
    version = "1.1"
}

