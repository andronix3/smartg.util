package com.imagero.java.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompressor implements AutoCloseable {

	private final ZipOutputStream out;
	private Path userDir;
	private List<Path> exclusion;

	public ZipCompressor(File destination) throws FileNotFoundException {
		this(destination, ZipOutputStream.DEFLATED, 9);
		System.out.println(destination);
	}

	public ZipCompressor(File destination, int method, int level) throws FileNotFoundException {
		this.out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destination)));
		this.out.setMethod(method);
		this.out.setLevel(level);
	}

	public void add(Path f) throws IOException {
		add(f, null);
	}

	public void add(Path f, List<Path> exclusion) throws IOException {
		this.exclusion = exclusion;
		if (!f.toFile().isDirectory()) {
			put(f);
		} else {
			addDirectory(f.toFile());
		}
	}

	@Override
	public void close() throws Exception {
		out.finish();
		out.close();
	}

	private void put(Path f) throws IOException {
		if (!f.getRoot().equals(userDir.getRoot())) {
			throw new IllegalArgumentException();
		}
		StringBuilder sb = new StringBuilder();
		int start = userDir.getNameCount();
		int end = f.getNameCount();
		for (int i = start; i < end; i++) {
			sb.append(f.getName(i));
			sb.append("/");
		}
		sb.deleteCharAt(sb.length() - 1);

		out.putNextEntry(new ZipEntry(sb.toString()));
		if (!f.toFile().isDirectory()) {
			Files.copy(f, out);
		}
	}

	private void addDirectory(File dir) throws IOException {
		File[] files = dir.listFiles();
		if(files != null) {
			for (File f : files) {
				Path path = f.toPath();
				if (exclusion == null || !exclusion.contains(path)) {
					if (f.isDirectory()) {
						addDirectory(f);
					} else {
						put(path);
					}
				}
			}
		}
	}

	public void setUserDir(Path userDir) {
		System.out.println("setUserDir: " + userDir);
		this.userDir = userDir;
	}

	public static void main(String[] args) throws Exception {
		try (ZipCompressor zu = new ZipCompressor(new File("C:\\Users\\User\\Desktop\\LabworksBugs.zip"))) {
			zu.add(new File("E:\\KimReed\\LabworksBugs").toPath(),
					Collections.singletonList(new File("E:\\KimReed\\exclude").toPath()));
		}
	}
}
