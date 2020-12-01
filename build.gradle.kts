plugins {
    java
    idea
}
subprojects {
    apply(plugin = "java")
    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Werror")
        options.compilerArgs.add("-Xlint")
    }
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
    version = "1.3.3"
}

