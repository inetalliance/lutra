description = "library to generate java classes from XHTML"
group = "net.inetalliance"
plugins {
    `maven-publish`
    signing
		`java-library`
}
repositories {
    mavenCentral()
}
dependencies {
    api("org.jsoup:jsoup:1.14.3")
    testImplementation("junit:junit:4.13.2")
}


java {
    withJavadocJar()
    withSourcesJar()
}
apply(plugin = "maven-publish")
tasks {
    build {
        dependsOn("sourcesJar")
        dependsOn("javadocJar")
    }
    withType<Jar> {
        archiveBaseName.set("lutra")
    }
}
publishing {
    publications {
        create<MavenPublication>("lutra") {
            artifactId = "lutra"
            groupId = "net.inetalliance"
            from(components["java"])
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
    if(project.hasProperty("ossrhUsername")) {
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
}
if (project.hasProperty("ossrhUsername")) {
    signing {
        sign(publishing.publications["lutra"])
    }
    apply(plugin = "signing")
}

