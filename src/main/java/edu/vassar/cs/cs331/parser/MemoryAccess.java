package edu.vassar.cs.cs331.parser;

import edu.vassar.cs.cs331.Memory;

/**
 * @author Keith Suderman
 */
public abstract class MemoryAccess extends Operand
{
	protected final int offset;

	public MemoryAccess(int offset)
	{
		this.offset = offset;
	}

	public int readInt(Memory memory)
	{
		return Memory.asInt(read(memory));
	}

	public float readFloat(Memory memory)
	{
		return Memory.asFloat(read(memory));
	}

	public byte[] dereference(Memory memory)
	{
		int ref = readInt(memory);
		return memory.read(ref, 4);
	}
}
