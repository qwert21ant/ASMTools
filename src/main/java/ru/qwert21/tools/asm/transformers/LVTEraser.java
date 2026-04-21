package ru.qwert21.tools.asm.transformers;

import org.objectweb.asm.*;

/**
 * Strips the LocalVariableTable attribute from every method in a class.
 * This removes debug-only parameter/variable name metadata without affecting runtime behaviour.
 * */
public class LVTEraser implements ClassTransformer {
	public byte[] transform(byte[] clazz) {
		ClassReader cr = new ClassReader(clazz);
		ClassWriter cw = new ClassWriter(cr, 0);

		ClassVisitor cv = new RemoveLVTClassVisitor(Opcodes.ASM4, cw);

		cr.accept(cv, 0);

		return cw.toByteArray();
	}

	private static class RemoveLVTClassVisitor extends ClassVisitor {
		public RemoveLVTClassVisitor(int api, ClassVisitor cv) {
			super(api, cv);
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			return new RemoveLVTMethodVisitor(Opcodes.ASM4, super.visitMethod(access, name, desc, signature, exceptions));
		}
	}

	private static class RemoveLVTMethodVisitor extends MethodVisitor {

		public RemoveLVTMethodVisitor(int api, MethodVisitor mv) {
			super(api, mv);
		}

		@Override
		public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
//			super.visitLocalVariable(name, desc, signature, start, end, index);
		}
	}
}
