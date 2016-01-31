package edu.vassar.cs.cs331.parser;

import edu.vassar.cs.cs331.Memory;

/**
 * @author Keith Suderman
 */
public class LocalMemoryAccess extends MemoryAccess
{
	public LocalMemoryAccess(int offset)
	{
		super(offset);
	}

	@Override
	public String print()
	{
		return "%" + offset;
	}

	@Override
	public byte[] read(Memory memory)
	{
		int pointer = memory.getFP() + offset;
		return memory.read(pointer, 4);
	}

	@Override
	public void write(Memory memory, byte[] buffer)
	{
		int pointer = memory.getFP() + offset;
		memory.write(pointer, buffer);
	}

	@Override
	public int address(Memory memory)
	{
		int r = memory.getFP() + offset;
		System.out.println("@local FP=" + memory.getFP());
		System.out.println("   offset=" + offset);
		return r;
	}
}
