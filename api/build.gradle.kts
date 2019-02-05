description = "library to generate java classes from XHTML"
plugins {
    maven
    `maven-publish`
    signing
}
repositories {
    mavenCentral()
}
dependencies {
    testCompile("junit:junit:4.11")
}

apply(plugin = "maven-publish")
apply(plugin = "signing")

tasks {
    register<Jar>("sourcesJar") {
        from(sourceSets.main.get().allJava)
        archiveBaseName.set("lutra")
        archiveClassifier.set("sources")
    }

    register<Jar>("javadocJar") {
        from(javadoc)
        archiveBaseName.set("lutra")
        archiveClassifier.set("javadoc")
    }
    jar {
        archiveBaseName.set("lutra")
    }
    build {
        dependsOn("sourcesJar")
        dependsOn("javadocJar")
    }
}

publishing {
    publications {
        create<MavenPublication>("lutra") {
            artifactId = "lutra"
            groupId = "net.inetalliance"
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
            pom {
                name.set(project.name)
                description.set(project.description)
                url.set("https://github.com/inetalliance/lutra")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("mquinnv")
                        name.set("Michael Ventura")
                        email.set("michael.ventura@gmail.com")
                    }
                    developer {
                        id.set("erikras")
                        name.set("Erik Rasmussen")
                        email.set("erik.rasmussen@inetalliance.net")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/inetalliance/lutra.git")
                    developerConnection.set("scm:git:git@github.com/inetalliance/lutra.git")
                    url.set("https://github.com/inetalliance/lutra")
                }
            }
        }
    }
    repositories {
        maven {
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.property("ossrhUsername") as String
                password = project.property("ossrhPassword") as String
            }
        }
    }
}

signing {
    sign(publishing.publications["lutra"])
}

