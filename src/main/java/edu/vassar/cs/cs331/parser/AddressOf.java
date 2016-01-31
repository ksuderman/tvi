package edu.vassar.cs.cs331.parser;

import edu.vassar.cs.cs331.Memory;

/**
 * @author Keith Suderman
 */
public class AddressOf extends Operand
{
	protected MemoryAccess access;

	public AddressOf(MemoryAccess access)
	{
		this.access = access;
	}

	@Override
	public void write(Memory memory, byte[] buffer)
	{
		access.write(memory, buffer);
	}

	@Override
	public byte[] read(Memory memory)
	{
		int pointer = access.address(memory);
		return memory.read(pointer, 4);
	}

	@Override
	public int readInt(Memory memory)
	{
		return access.address(memory);
	}

	@Override
	public float readFloat(Memory memory)
	{
		throw new UnsupportedOperationException("Can not read an address as a floating point value.");
	}

	@Override
	public int address(Memory memory)
	{
		return access.address(memory);
	}

	@Override
	public byte[] dereference(Memory memory)
	{
		return access.dereference(memory);
	}

	@Override
	public String print()
	{
		return "@" + access.print();
	}
}
