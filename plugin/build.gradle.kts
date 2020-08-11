group = "net.inetalliance.lutra"
plugins {
  groovy
  idea
  `java-gradle-plugin`
  id("com.gradle.plugin-publish") version "0.12.0"
}

dependencies {
  compile("net.inetalliance:lutra:1.3.1")
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
      description = "genrates java classes to represent valid xhtml documents"
      implementationClass = "net.inetalliance.lutra.LutraPlugin"
    }
  }
}


