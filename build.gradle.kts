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
    tasks {
        val sourcesJar by creating(Jar::class) {
            archiveClassifier.set("sources")
            from(sourceSets.main.get().allSource)
        }
        artifacts {
            add("archives", sourcesJar)
        }
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

