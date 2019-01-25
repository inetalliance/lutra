package net.inetalliance.lutra

import net.inetalliance.lutra.elements.Attribute
import net.inetalliance.lutra.elements.Element
import net.inetalliance.lutra.elements.HtmlElement
import net.inetalliance.lutra.elements.TextContent
import net.inetalliance.lutra.rules.ValidationErrors
import net.inetalliance.lutra.util.Escaper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.xml.sax.SAXException

import java.nio.file.Files
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.function.Predicate
import java.util.regex.Pattern

class LutraTask extends DefaultTask {
  @Input
  String exclude = ''
  @OutputDirectory
  File outputDirectory = new File('src/lutra/java')
  @InputDirectory
  File basedir = new File(".")
  @Input
  String packageName

  LutraTask() {
  }

  @TaskAction
  def generate() {
    getLogger().debug("basedir is %s", basedir.getAbsolutePath())
    execute("src/main/webapp")


  }

  def execute(String path, boolean preparsed = false) {
    def root = new File(basedir, path)
    if (!root.exists()) {
      getLogger().info("no web root found")
      return
    }
    def log = getLogger()
    log.info("Generating ${preparsed ? 'pre-parsed' : 'lazy-parsed'} classes for html files in  ${root.getAbsolutePath()}")

    if (!outputDirectory.exists())
      if (!outputDirectory.mkdirs())
        log.error("Could not make output dir!")

    String packageName = "${packageName}.lutra"
    if (preparsed)
      packageName += ".preparsed"
    log.info("Package will be ${packageName}")

    final Collection<String> parseErrors = new ArrayList<>(1024)
    final Collection<String> validationErrors = new ArrayList<>(1024)

    Predicate<? super File> exclude
    if (preparsed || this.exclude == null || this.exclude.isEmpty()) {
      exclude = { File f -> true }
    } else {
      def p = Pattern.compile(this.exclude)
      exclude = { File f -> !p.matcher(f.getAbsolutePath()).matches() }
    }

    Files.walk((root.toPath()))
        .map({ p -> p.toFile() })
        .filter(exclude)
        .filter({ f -> f.getName().endsWith(".html") })
        .each({ genInput ->
      try {
        final String filePath = genInput.getAbsolutePath().replace(root.getAbsolutePath(), "")
        final int lastSlash = filePath.lastIndexOf('/')
        final String withoutFilename = lastSlash < 0 ? "" : filePath.substring(0, lastSlash)
        final String genPackage = String.format("%s%s", packageName, withoutFilename.replace('/', '.'))
        if (log.isDebugEnabled()) {
          log.debug(String.format("relative path: %s", filePath))
          log.debug(String.format("package: %s", genPackage))
          log.debug(String.format("without file: %s", withoutFilename))
        }
        generate(genPackage, genInput, filePath, parseErrors, validationErrors, preparsed)
      }
      catch (SAXException e) {
        log.error(String.format("Error while processing %s:\n%s", genInput.getAbsolutePath(), e.getMessage()))
      }
      catch (Exception e) {
        log.error("unknown problem", e)
      }
    })


    if (validationErrors.size() + parseErrors.size() > 0) {
      for (final String error : validationErrors)
        log.error(error)
      for (final String error : parseErrors)
        log.error(error)
      throw new TaskExecutionException(this, new IllegalArgumentException("source errors"))
    }
  }

  void generate(final String packageName, final File input, final String filePath,
                final Collection<String> parseErrors, final Collection<String> validationErrors,
                final boolean preparsed)
      throws IOException, SAXException {
    generate(outputDirectory, packageName, input, filePath, parseErrors, validationErrors, preparsed)
  }

  File generate(final File outputDirectory, final String packageName, final File input,
                final String filePath, final Collection<String> parseErrors,
                final Collection<String> validationErrors, final boolean preparsed)
      throws IOException, SAXException {
    def log = getLogger()
    final File outputDir = makeOutputDir(outputDirectory, packageName)
    if (log.isDebugEnabled())
      log.debug(String.format("output dir: %s", outputDir))
    final String name = filenameToClassName(input.getName())
    if (log.isDebugEnabled())
      log.debug(String.format("name: %s", name))
    final File file = new File(outputDir, name + ".java")
    if (log.isDebugEnabled())
      log.debug(String.format("src: %s", file.getAbsolutePath()))
    if (file.exists() && file.lastModified() > input.lastModified()) {
      if (log.isDebugEnabled())
        log.debug(String.format("%s is not modified", file.getAbsolutePath()))
      return file
    } else
      log.info(String.format("Generating %s API for %s", preparsed ? "pre-parsed" : "lazy-parsed", input.getAbsolutePath()))
    if (!file.getParentFile().exists() && !file.getParentFile().mkdirs())
      log.error(String.format("Could not make parent dir %s", file.getParentFile().getAbsolutePath()))

    final DocumentBuilder builder = new DocumentBuilder(input)
    try {
      log.debug("parsing")
      builder.parse()
    }
    catch (SAXException e) {
      log.debug("sax failed", e)
      file.deleteOnExit()
      parseErrors.add(e.getMessage())
      return null
    }

    log.debug("validating")
    final ValidationErrors errors = builder.validate(false)
    if (errors.isEmpty()) {
      log.debug("passed")
      if (file.exists() && !file.canWrite())
        if (!file.setWritable(true))
          log.error("could not set file as writable")
      final BufferedWriter out = new BufferedWriter(new FileWriter(file))
      try {
        final Map<String, Element> byId = builder.getById()

        out.append("package ").append(packageName).append(";\n\n")
        if (preparsed) {
          final LazyDocument document = builder.buildGeneric()
          final Set<Class> imports = new TreeSet<>({ a, b -> a.name.compareTo(b.name) })
          final StringBuilder cls = new StringBuilder(1024)
          imports.add(HtmlElement.class)
          imports.add(Attribute.class)

          cls.append("\n/**\n")
          cls.append(" * Generated from ").append(input.getAbsolutePath()).append(" on ")
          cls.append(DATE_FORMAT.format(new Date())).append(".\n")
          cls.append(" *\n")
          cls.append(" * @version ").append(DATE_FORMAT.format(new Date(input.lastModified()))).append("\n")
          cls.append(" */\n")
          cls.append("public class ").append(name).
              append(" extends ").append(HtmlElement.class.getSimpleName()).
              append("\n{\n")
          cls.append("\tpublic final HeadElement head;\n")
          cls.append("\tpublic final BodyElement body;\n\n")
          cls.append("\tpublic ").append(name).append("()\n").
              append("\t{")
          cls.append("\n\t\thead = ")
          generatePreparsed(cls, imports, document.head, "\t\t\t")
          cls.append(";\n\t\tbody = ")
          generatePreparsed(cls, imports, document.body, "\t\t\t")
          cls.append(";\n\t\taddChild(head, body);")
          for (final Map.Entry<Attribute, String> entry : document.root.getAttributes().entrySet()) {
            cls.append("\n\t\tsetAttribute(Attribute.")
            cls.append(entry.getKey().name()).append(", \"")
            cls.append(Escaper.javaString.escape(entry.getValue())).append("\");")
          }
          cls.append("\n\t}")
          cls.append("\n}")

          for (final Class imported : imports)
            addImport(out, imported)
          out.append(cls)
        } else {
          addImport(out, LazyDocument.class)
          addImport(out, DocumentBuilder.class)
          addImport(out, Element.class)
          addImport(out, ElementBundle.class)
          addImport(out, EnumMap.class)
          addImport(out, Map.class)
          final Set<Class> elementTypes = new HashSet<>(byId.size())
          for (final Element element : byId.values()) {
            if (!elementTypes.contains(element.getClass())) {
              elementTypes.add(element.getClass())
              addImport(out, element.getClass())
            }
          }

          out.append("\n/**\n")
          out.append(" * Generated from ").append(input.getAbsolutePath()).append(" on ")
          out.append(DATE_FORMAT.format(new Date())).append(".\n")
          out.append(" *\n")
          out.append(" * @version ").append(DATE_FORMAT.format(new Date(input.lastModified()))).append("\n")
          out.append(" */\n")
          out.append("public class ").append(name).
              append(" extends ").append(LazyDocument.class.getSimpleName()).
              append("\n{\n")

          out.append("\t/** An object holding all the elements in this document */\n")
          out.append("\tpublic final Tags tags;\n")
          out.append("\t/** A map holding all the elements in this document */\n")
          out.append("\tprivate final Map<Id,Element> map;\n")
          out.append("\n\tpublic static final String ").append(LazyDocument.FILE_PATH_FIELD).append(" = \"").append(
              filePath).append("\";\n")

          out.append("\n\tpublic ").append(name).append("(final ").append(DocumentBuilder.class.getSimpleName()).append(
              " builder)\n\t{\n")
          out.append("\t\tsuper(builder);\n")
          out.append("\t\tmap = new EnumMap<>(Id.class);\n")
          out.append("\t\ttags = new Tags(this, map);\n")
          out.append("\t}\n\n")
          generateTagsClass(out, byId, name)
          out.append("}")
        }
        return file
      }
      catch (IOException ignored) {
        file.deleteOnExit()
      }
      finally {
        out.close()
        if (!file.setWritable(false))
          log.debug("unset writable failed")
      }
    } else {
      log.debug("failed")
      file.deleteOnExit()
      errors.toString(validationErrors)
    }
    return null
  }

  private static void generatePreparsed(final Appendable cls, final Set<Class> imports, final Element element, final String indent) throws IOException {
    imports.add(element.getClass())
    if (element instanceof TextContent)
      cls.append("new TextContent(\"").append(Escaper.javaString.escape(element.getText())).append("\")")
    else {
      if (!element.getAttributes().isEmpty())
        cls.append('(').append(element.getClass().getSimpleName()).append(") ")
      cls.append("new ").append(element.getClass().getSimpleName()).append('(')
      final List<Element> children = element.getChildren()
      if (children.size() == 1 && children.get(0) instanceof TextContent) {
        cls.append('"').append(Escaper.javaString.escape(children.get(0).getText())).append('"')
      } else {
        if (children.size() > 1)
          cls.append('\n').append(indent)
        for (Iterator<Element> iterator = children.iterator(); iterator.hasNext();) {
          final Element child = iterator.next()
          generatePreparsed(cls, imports, child, indent + "\t")
          if (iterator.hasNext())
            cls.append(",\n").append(indent)
        }
      }
      cls.append(')')
      for (final Map.Entry<Attribute, String> entry : element.getAttributes().entrySet()) {
        cls.append('\n').append(indent).append('\t')
        cls.append(".setAttribute(Attribute.").append(entry.getKey().name())
        cls.append(", \"").append(entry.getValue()).append("\")")
      }
    }
  }
  private static final Pattern dotPattern = Pattern.compile("\\.")
  private static final Pattern invalidCharactersPattern = Pattern.compile("[.\\-_:]")

  private File makeOutputDir(final File outputRoot, final String packageName) {
    final String[] dirs = dotPattern.split(packageName)
    File parent = outputRoot
    for (final String dir : dirs) {
      final File childDir = new File(parent, dir)
      if (!childDir.exists() && !childDir.mkdir())
        getLogger().error("could not create child dir")
      parent = childDir
    }
    return parent
  }

  static String filenameToClassName(final String filename) {
    if (!filename.endsWith(".html"))
      throw new IllegalArgumentException("Expecting .html files!")
    final String withoutExtension = filename.substring(0, filename.length() - 5)
    final String replaced = invalidCharactersPattern.matcher(withoutExtension).replaceAll("")
    return "${Character.toUpperCase(replaced.charAt(0))}${replaced.substring(1)}Html"
  }
  public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss")  // avoiding dependency on joda-time
  private void generateTagsClass(final BufferedWriter out, final Map<String, Element> map, final String name)
      throws IOException {
    def log = getLogger()
    log.debug(String.format("generating tags class: %s", name))
    out.append("\tpublic static final class Tags extends ElementBundle\n").
        append("\t{\n")

    final StringBuilder constructor = new StringBuilder(1024)
    final StringBuilder ids = new StringBuilder(1024)
    final StringBuilder casters = new StringBuilder(1024)
    final Map<String, String> variables = new HashMap<>(8)
    for (final Map.Entry<String, Element> entry : map.entrySet()) {
      final String id = entry.getKey()
      final String variable = idToVariable(id)
      final String previous = variables.put(variable, id)
      if (previous != null)
        throw new IllegalStateException(
            String.format("Id \"%s\" resolves to same name as \"%s\".  Please make them more distinct.", id, previous))
    }
    final Date now = new Date()
    final List<String> variableNames = new ArrayList<>(variables.keySet())
    Collections.sort(variableNames)
    for (final String variable : variableNames) {
      final String id = variables.get(variable)
      if (ids.length() > 0)
        ids.append(",\n\t\t")
      final String enumValue = StringFun.decamel(variable).toUpperCase().replaceAll(' ', '_')
      ids.append(enumValue)
      final Element element = map.get(id)
      final String location = element.getLocation().toString()
      final String choppedLocation = location.substring(location.lastIndexOf('/') + 1)
      out.append(String.format(tag, Element.class.getSimpleName(), variable, choppedLocation, DATE_FORMAT.format(now)))
      constructor.append("\t\t\t").append(variable).append(" = document.getById(\"").append(id).append("\");\n")
      constructor.append("\t\t\t").append("map.put(Id.").append(enumValue).append(", ").append(variable).append(");\n")
      casters.append(String.format(caster,
          element.getClass().getSimpleName(),
          variable,
          element.@elementType,
          StringFun.titleCase(element.@elementType.toString()),
          choppedLocation,
          DATE_FORMAT.format(now),
          element.escapeAbbreviated()))
    }
    out.append("\n\t\tprivate Tags(final ").append(name).append(" document, final Map<Id, Element> map)\n\t\t{\n")
    out.append(constructor)
    out.append("\t\t}\n")
    out.append(casters)
    out.append("\t}\n")
    out.append("\n\tpublic static enum Id\n\t{\n\t\t")
    out.append(ids)
    out.append("\n\t}\n")
    out.append("\n\t/**\n")
    out.append("\t * Fetches a specific element by its id.\n")
    out.append("\t *\n")
    out.append("\t * @param id the id of the element to fetch\n")
    out.append("\t * @return Element the element that matches the given id (may be null if it doesn't exist)\n")
    out.append("\t */\n")
    out.append("\tpublic final Element get(final Enum id)\n")
    out.append("\t{\n")
    out.append("\t\treturn get((Id) id);\n")
    out.append("\t}\n")
    out.append("\n\t/**\n")
    out.append("\t * Fetches a specific element by its id.\n")
    out.append("\t *\n")
    out.append("\t * @param id the id of the element to fetch\n")
    out.append("\t * @return Element the element that matches the given id (may be null if it doesn't exist)\n")
    out.append("\t */\n")
    out.append("\tpublic final Element get(final Id id)\n")
    out.append("\t{\n")
    out.append("\t\treturn map.get(id);\n")
    out.append("\t}\n")
  }

  private static final String tag = '\t\t/** Generated from %3$s on %4$s */\n' +
      '\t\tpublic final %1$s %2$s;\n'
  private static final String caster = '\n\t\t/**\n' +
      '\t\t * Generated from %5$s on %6$s \n' +
      '\t\t *\n' +
      '\t\t * @return %1$s %7$s\n' +
      '\t\t */\n' +
      '\t\tpublic final %1$s %2$s%4$s()\n' +
      '\t\t{\n' +
      '\t\t\ttry\n' +
      '\t\t\t{\n' +
      '\t\t\t\treturn (%1$s) %2$s;\n' +
      '\t\t\t}\n' +
      '\t\t\tcatch(' + ClassCastException.class.getSimpleName() + ' e)\n' +
      '\t\t\t{\n' +
      '\t\t\t\tthrow new ' + IllegalStateException.class.getSimpleName() +
      '(\"Expecting element with id \\\"%2$s\\\" to be of type \\\"%3$s\\\"\");\n' +
      '\t\t\t}\n' +
      '\t\t}\n'

  private static final Set<String> keywords

  static
  {
    keywords = new HashSet<>(5)
    keywords.add("new")
    keywords.add("package")
    keywords.add("int")
    keywords.add("float")
    keywords.add("double")
    keywords.add("enum")
    keywords.add("for")
    keywords.add("continue")
    keywords.add("break")
    keywords.add("do")
    keywords.add("while")
    keywords.add("switch")
    keywords.add("document") // not really a keyword, but conflicts with local variable
    // add more here as necessary
  }

  private static String replaceKeywords(final String variable) {
    return keywords.contains(variable) ? '_' + variable : variable
  }

  private String idToVariable(final String id) {
    final String[] tokens = invalidCharactersPattern.split(id)
    final StringBuilder result = new StringBuilder(id.length())
    for (int i = 0; i < tokens.length; i++) {
      final String token = tokens[i]
      if (i == 0)
        result.append(Character.toLowerCase(token.charAt(0)))
      else
        result.append(Character.toUpperCase(token.charAt(0)))
      result.append(token.substring(1))
    }
    String res = replaceKeywords(result.toString())
    getLogger().info("i2V:" + id + ":" + res)
    return res
  }

  private static void addImport(final Appendable out, final Class type)
      throws IOException {
    out.append("import ").append(type.getName()).append(";\n")
  }

}
