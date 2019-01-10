package net.inetalliance.lutra

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class LutraPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    project.apply(plugin: 'java')
    LutraExtension ext = project.extensions.create('lutraExt', LutraExtension.class);
    Configuration lutraConfiguration = project.configurations.maybeCreate('lutra')
    project.task('lutra', type: LutraTask, group: 'lutra', description: 'Generate lutra source from html files')
    project.task('deleteGeneratedSources', type: CleanTask, group: 'lutra', description: 'Delete java source code ' +
        'generated from html files.')
    project.afterEvaluate {
        project.sourceSets.main.java.srcDirs += project.lutra.outputDirectory
        project.compileJava.dependsOn project.lutra
    }
    if(ext.deleteGeneratedSourcesOnClean) {
      project.clean.dependsOn project.deleteGeneratedSources
    }
  }
}
