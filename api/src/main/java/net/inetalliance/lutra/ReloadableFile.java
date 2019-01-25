package net.inetalliance.lutra;

import java.io.InputStream;

public interface ReloadableFile {
	void load(final InputStream inputStream)
		throws Exception;
}
