package net.inetalliance.lutra;

import java.io.File;
import java.util.Set;
import java.util.regex.Pattern;

public class FileRoot {
	public final File root;
	private final Set<Pattern> excluded;

	public FileRoot(final File root, final Set<Pattern> excluded) {
		this.root = root;
		this.excluded = excluded;
	}

	public boolean isExcluded(final String url) {
		if (excluded != null) {
			for (final Pattern pattern : excluded) {
				if (pattern.matcher(url).matches())
					return true;
			}
		}
		return false;
	}
}
