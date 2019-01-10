group = "net.inetalliance.lutra"
plugins {
    groovy
    idea
    `java-gradle-plugin`
}

dependencies {
    compile(project(":api"))
    testCompile("junit:junit:4.8.2")
}

gradlePlugin {
    plugins {
        create("simplePlugin") {
            id = "net.inetalliance.lutra"
            implementationClass = "net.inetalliance.lutra.LutraPlugin"
        }
    }
}


