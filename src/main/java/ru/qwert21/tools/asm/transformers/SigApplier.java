package ru.qwert21.tools.asm.transformers;

import org.objectweb.asm.*;
import ru.qwert21.tools.asm.SigContainer;

/**
 * Rewrites generic type signatures in a class file using mappings from a {@link SigContainer}.
 * For each class, field, and method, the mapped signature replaces the existing one; elements with no mapping are left unchanged.
 * */
public class SigApplier implements ClassTransformer {
	private final SigContainer sigs;

	public SigApplier(SigContainer sigs) {
		this.sigs = sigs;
	}

	public byte[] transform(byte[] data) {
		ClassReader cr = new ClassReader(data);
		ClassWriter cw = new ClassWriter(cr, 0);

		ClassVisitor cv = new SigClassVisitor(Opcodes.ASM4, cw);

		cr.accept(cv, 0);

		return cw.toByteArray();
	}

	private class SigClassVisitor extends ClassVisitor {
		private String className;
		public SigClassVisitor(int api, ClassVisitor cv) {
			super(api, cv);
		}

		@Override
		public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			this.className = name;

			String sig = sigs.classSig.get(name);
			super.visit(version, access, name, sig != null ? sig : signature, superName, interfaces);
		}

		@Override
		public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
			String sig = sigs.fieldSig.get(className + "/" + name);

			return super.visitField(access, name, desc, sig != null ? sig : signature, value);
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			String sig = sigs.methodSig.get(className + "/" + name);

			return super.visitMethod(access, name, desc, sig != null ? sig : signature, exceptions);
		}
	}
}
