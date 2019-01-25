package net.inetalliance.lutra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Pattern;

public class LutraFactory {
	private static final ThreadLocal<Set<Document>> trace = new ThreadLocal<>();
	private final PageRoot[] pageRoots;
	private final transient Map<Class<? extends LazyDocument>, Map<String, DocumentBuilder>> builders;

	public LutraFactory(final PageRoot... pageRoots) {
		this.pageRoots = pageRoots;
		builders = new HashMap<>(0);
	}

	public static void startTrace() {
		trace.set(new HashSet<>(4));
	}

	public static Set<Document> endTrace() {
		final Set<Document> documents = trace.get();
		trace.remove();
		return documents;
	}

	public <D extends LazyDocument> D create(final Class<D> type)
		throws FileNotFoundException, NoSuchFieldException, IllegalAccessException,
		NoSuchMethodException,
		InvocationTargetException, InstantiationException {
		final D d = create(type, (String) type.getField(LazyDocument.FILE_PATH_FIELD).get(null));
		final Collection<Document> documents = trace.get();
		if (documents != null) {
			documents.add(d);
		}
		return d;
	}

	public <D extends LazyDocument> D create(final Class<D> type, final String filePath)
		throws FileNotFoundException, IllegalAccessException,
		NoSuchMethodException,
		InvocationTargetException, InstantiationException {
		DocumentBuilder builder = null;
		Map<String, DocumentBuilder> typeBuilders = builders.get(type);
		if (typeBuilders == null) {
			typeBuilders = new HashMap<>(1);
			builders.put(type, typeBuilders);
		} else {
			builder = typeBuilders.get(filePath);
		}
		if (builder == null) {
			final File file = getFile(filePath);
			if (file == null) {
				throw new FileNotFoundException(String.format("%s not found in any page root", filePath));
			}
			builder = new DocumentBuilder(file);
			typeBuilders.put(filePath, builder);
		}
		return builder.build(type);
	}

	public File getFile(final String filePath) {
		// try to find static html file
		for (final PageRoot pageRoot : pageRoots) {
			final String adjusted = pageRoot.adjust(filePath);
			try {
				final File file = new File(new File(pageRoot.root, adjusted).getCanonicalPath());
				if (file.exists()) {
					return file;
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	public static class PageRoot {
		public final File root;
		public final String prefix;
		public final Set<Pattern> excludes;

		public PageRoot(final File root, final String prefix, final Set<Pattern> excludes) {
			this.root = root;
			this.prefix = prefix;
			this.excludes = excludes;
		}

		public static FileRoot[] toFileRoots(final PageRoot... pageRoots) {
			final FileRoot[] files = new FileRoot[pageRoots.length];
			for (int i = 0; i < pageRoots.length; i++) {
				files[i] = new FileRoot(pageRoots[i].root, pageRoots[i].excludes);
			}
			return files;
		}

		public String makeRelative(final String absoluteUrl) {
			final String rootPath = root.getAbsolutePath();
			final String withPrefix = rootPath + prefix;
			if (absoluteUrl.startsWith(withPrefix)) {
				return absoluteUrl.substring(withPrefix.length());
			} else if (absoluteUrl.startsWith(rootPath)) {
				return absoluteUrl.substring(rootPath.length());
			} else {
				return null;
			}
		}

		public String adjust(final String filePath) {
			if (prefix != null && filePath.startsWith(prefix)) {
				return filePath.substring(prefix.length());
			} else {
				return filePath;
			}
		}
	}
}