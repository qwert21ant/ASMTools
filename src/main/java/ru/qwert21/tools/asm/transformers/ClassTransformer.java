package ru.qwert21.tools.asm.transformers;

/**
 * Strategy for transforming the raw bytecode of a single class.
 * Implementations receive the original bytes and return the (possibly modified) bytes.
 * */
public interface ClassTransformer {
	byte[] transform(byte[] data);
}
