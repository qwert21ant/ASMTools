package ru.qwert21.tools.asm;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a signature mapping file and stores the results in three maps keyed by JVM internal name: one each for class, field, and method signatures.
 * Lines must match the pattern {@code (CL|FD|MD): <name> <signature>}.
 * */
public class SigContainer {
	private static final Pattern pattern = Pattern.compile("^(CL|FD|MD):\\s*(\\S*)\\s*(\\S*)\\s*$");

	public final LinkedHashMap<String, String> classSig, fieldSig, methodSig;

	public SigContainer() {
		this.classSig = new LinkedHashMap<>();
		this.fieldSig = new LinkedHashMap<>();
		this.methodSig = new LinkedHashMap<>();
	}

	public void loadFromFile(File file) throws IOException {
		List<String> lines = Files.readLines(file, StandardCharsets.UTF_8);

		for (String line : lines) {
			Matcher m = pattern.matcher(line);

			if (!m.find())
				continue;

			String type = m.group(1);
			String name = m.group(2);
			String sig = m.group(3);

			switch (type) {
				case "CL": {
					classSig.put(name, sig);
					break;
				}
				case "FD": {
					fieldSig.put(name, sig);
					break;
				}
				case "MD": {
					methodSig.put(name, sig);
					break;
				}
			}
		}
	}
}
