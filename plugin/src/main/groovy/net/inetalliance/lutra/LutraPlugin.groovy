package net.inetalliance.lutra

import org.gradle.api.Plugin
import org.gradle.api.Project

@SuppressWarnings("unused")
class LutraPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    project.apply(plugin: 'java')
    LutraExtension ext = project.extensions.create('lutraExt', LutraExtension.class);
    project.configurations.maybeCreate('lutra')

     def lutra = project.task('lutra',
        type: LutraTask,
        group: 'lutra',
        description: 'Generate lutra source from html files')

     def clean = project.task('deleteGeneratedSources',
        type: CleanTask,
        group: 'lutra',
        description: 'Delete java source code generated from html files.')

    project.tasks.compileJava.dependsOn lutra
    project.tasks.compileTestJava.dependsOn lutra

    project.afterEvaluate {
      project.sourceSets.main.java.srcDirs += project.lutra.outputDirectory
    }
    if (ext.deleteGeneratedSourcesOnClean) {
      project.tasks.clean.dependsOn clean
    }
  }
}
