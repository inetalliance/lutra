plugins {
    java
    idea
}
subprojects {
    apply(plugin = "java")
    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Werror")
        options.compilerArgs.add("-Xlint:-this-escape")
        options.compilerArgs.add("--enable-preview")
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
    version = "1.5.3"
}
