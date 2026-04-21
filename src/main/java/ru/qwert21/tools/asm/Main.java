package ru.qwert21.tools.asm;

import ru.qwert21.tools.asm.transformers.SigApplier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
	public static void main(String[] args) throws IOException {
		SigContainer sigs = new SigContainer();
		sigs.loadFromFile(new File(args[2]));

		JarTransformer tr = new JarTransformer(new SigApplier(sigs));

		ZipInputStream in = new ZipInputStream(Files.newInputStream(Paths.get(args[0])));
		ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(Paths.get(args[1])));

		tr.transform(in, out);

		in.close();
		out.close();
	}
}
