package net.inetalliance.lutra
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CleanTask extends DefaultTask {
  @TaskAction
  public void clean() {
    project.lutra.outputDirectory.deleteDir()

  }
}
