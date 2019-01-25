package net.inetalliance.lutra;

import org.xml.sax.SAXParseException;

import java.io.*;
import java.net.URL;

public class FileReloader {
	private static final long DEFAULT_TIME_BETWEEN_UPDATE_CHECKS = 1000L;// one second

	private final Data data;
	private final long timeBetweenUpdateChecks;
	private final ReloadableFile reloadable;
	private long lastRead;
	private long lastCheckedForUpdate;

	private static interface Data {
		InputStream toInputStream() throws IOException;

		long lastModified();

		String getAbsolutePath();
	}

	private static class FileData implements Data {
		private final File file;

		private FileData(final File file) {
			this.file = file;
		}

		@Override
		public InputStream toInputStream() throws FileNotFoundException {
			return new FileInputStream(file);
		}

		@Override
		public long lastModified() {
			return file.lastModified();
		}

		@Override
		public String getAbsolutePath() {
			return file.getAbsolutePath();
		}
	}

	private static class JarData implements Data {
		private final File jar;
		private final URL url;

		private JarData(final URL url) {
			final String path = new File(url.getFile()).getParentFile().getPath();
			// path looks like file:PATH!
			this.jar = new File(path.substring(5, path.length() - 1));
			this.url = url;
		}

		@Override
		public InputStream toInputStream() throws IOException {
			return url.openStream();
		}

		@Override
		public long lastModified() {
			return jar.lastModified();
		}

		@Override
		public String getAbsolutePath() {
			return url.toString();
		}
	}

	public FileReloader(final File file, final ReloadableFile reloadable) {
		this(file, reloadable, DEFAULT_TIME_BETWEEN_UPDATE_CHECKS);
	}

	private FileReloader(final File file, final ReloadableFile reloadable, final long timeBetweenUpdateChecks) {
		this.data = new FileData(file);
		this.reloadable = reloadable;
		this.timeBetweenUpdateChecks = timeBetweenUpdateChecks;
	}

	public FileReloader(final URL url, final ReloadableFile reloadable) {
		this(url, reloadable, DEFAULT_TIME_BETWEEN_UPDATE_CHECKS);
	}

	private FileReloader(final URL url, final ReloadableFile reloadable, final long timeBetweenUpdateChecks) {
		if ("jar".equals(url.getProtocol()))
			this.data = new JarData(url);
		else
			this.data = new FileData(new File(url.getFile()));
		this.reloadable = reloadable;
		this.timeBetweenUpdateChecks = timeBetweenUpdateChecks;
	}

	public void forceReload() {
		synchronized (data) {
			lastRead = 0;
		}
	}

	public long getLastRead() {
		synchronized (data) {
			return lastRead;
		}
	}

	public void setLastRead(final long lastRead) {
		synchronized (data) {
			this.lastRead = lastRead;
		}
	}

	public boolean needReload() {
		boolean needReload = false;
		if (lastCheckedForUpdate + timeBetweenUpdateChecks < System.currentTimeMillis()) {
			final long lastModified = data.lastModified();
			needReload = lastModified > lastRead;
			lastCheckedForUpdate = System.currentTimeMillis();
		}
		return needReload;
	}

	public boolean reloadIfNeeded() {
		synchronized (data) {
			if (needReload()) {
				try {
					final InputStream inputStream = data.toInputStream();
					try {
						reloadable.load(inputStream);
						lastRead = System.currentTimeMillis();
						return true;
					} finally {
						inputStream.close();
					}
				} catch (SAXParseException e) {
					throw new RuntimeException(
						String.format("Parse error: %s:%s [%s]: %s", data.getAbsolutePath(), e.getLineNumber(),
							e.getColumnNumber(), e.getMessage()));
				} catch (RuntimeException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			return false;
		}
	}
}
