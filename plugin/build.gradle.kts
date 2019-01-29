group = "net.inetalliance.lutra"
plugins {
  groovy
  idea
  `java-gradle-plugin`
  id("com.gradle.plugin-publish") version "0.10.0"
}

dependencies {
  compile(project(":api"))
  testCompile("junit:junit:4.8.2")
}
pluginBundle {
  website = "https://github.com/inetalliance" 
  vcsUrl = "https://github.com/inetalliance/lutra" 
  tags = listOf("xhtml", "java") 
}

gradlePlugin {
  plugins {
    create("lutraPlugin") {
      id = "net.inetalliance.lutra"
      displayName = "Lutra XHTML to Java Generator"
      implementationClass = "net.inetalliance.lutra.LutraPlugin"
    }
  }
}


