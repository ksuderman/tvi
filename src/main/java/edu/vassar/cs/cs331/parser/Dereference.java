package edu.vassar.cs.cs331.parser;

import edu.vassar.cs.cs331.Memory;

/**
 * @author Keith Suderman
 */
public class Dereference extends Operand
{
	protected MemoryAccess access;

	public Dereference(MemoryAccess access)
	{
		this.access = access;
	}

	@Override
	public void write(Memory memory, byte[] buffer)
	{
		int pointer = access.readInt(memory);
		System.out.println("Writing to dereferenced memory. pointer=" + pointer + " buffer=" + Memory.asInt(buffer));
		memory.write(pointer, buffer);
	}

	@Override
	public byte[] read(Memory memory)
	{
		int pointer = access.readInt(memory);
		System.out.println("Reading from dereferenced memory. pointer=" + pointer);
		return memory.read(pointer, 4);
	}

	@Override
	public int readInt(Memory memory)
	{
		int pointer = access.readInt(memory);
		byte[] buffer = memory.read(pointer, 4);
		return Memory.asInt(buffer);
	}

	@Override
	public float readFloat(Memory memory)
	{
		int pointer = access.readInt(memory);
		byte[] buffer = memory.read(pointer, 4);
		return Memory.asFloat(buffer);
	}

	@Override
	public int address(Memory memory)
	{
		throw new UnsupportedOperationException("Can not get address of a dereference.");
	}

	@Override
	public byte[] dereference(Memory memory)
	{
		return access.dereference(memory);
	}

	@Override
	public String print()
	{
		return "^" + access.print();
	}
}
