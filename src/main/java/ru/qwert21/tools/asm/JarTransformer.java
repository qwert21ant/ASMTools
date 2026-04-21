package ru.qwert21.tools.asm;

import com.google.common.io.ByteStreams;
import ru.qwert21.tools.asm.transformers.ClassTransformer;

import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Iterates every entry in an input JAR, passes .class files through a {@link ClassTransformer}, and writes the result to an output JAR.
 * Non-class entries are copied verbatim.
 * */
public class JarTransformer {
	private final ClassTransformer ct;

	public JarTransformer(ClassTransformer ct) {
		this.ct = ct;
	}

	public void transform(ZipInputStream in, ZipOutputStream out) throws IOException {
		for (ZipEntry je = in.getNextEntry(); je != null; je = in.getNextEntry()) {
			if (je.isDirectory())
				continue;

			String name = je.getName();
			if (name.endsWith(".class")) {
				byte[] data = ByteStreams.toByteArray(in);
				data = ct.transform(data);

				out.putNextEntry(new JarEntry(name));
				out.write(data);
			} else {
				out.putNextEntry(new JarEntry(name));
				ByteStreams.copy(in, out);
			}
		}
	}
}
