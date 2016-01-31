package edu.vassar.cs.cs331.parser;

import edu.vassar.cs.cs331.Memory;

/**
 * @author Keith Suderman
 */
public abstract class Operand
{
	public Operand()
	{
	}

	public abstract void write(Memory memory, byte[] buffer);
	public void write(Memory memory, int value) {
		byte[] buffer = Memory.convert(value);
		write(memory, buffer);
	}
	public void write(Memory memory, float value) {
		write(memory, Memory.convert(value));
	}
	public abstract byte[] read(Memory memory);
	public abstract int readInt(Memory memory);
	public abstract float readFloat(Memory memory);
	public abstract int address(Memory memory);
	public abstract byte[] dereference(Memory memory);
	public abstract String print();
}
