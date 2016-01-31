package edu.vassar.cs.cs331.parser;

import edu.vassar.cs.cs331.Memory;

/**
 * @author Keith Suderman
 */
public class GlobalMemoryAccess extends MemoryAccess
{
	public GlobalMemoryAccess(int offset)
	{
		super(offset);
	}

	@Override
	public byte[] read(Memory memory)
	{
		return memory.read(offset, 4);
	}

	@Override
	public void write(Memory memory, byte[] buffer)
	{
		memory.write(offset, buffer);
	}

	@Override
	public int address(Memory memory)
	{
		return offset;
	}

	@Override
	public String print()
	{
		return "_" + offset;
	}
}
