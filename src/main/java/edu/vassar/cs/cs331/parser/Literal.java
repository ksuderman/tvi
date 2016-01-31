package edu.vassar.cs.cs331.parser;

import edu.vassar.cs.cs331.Memory;

/**
 * @author Keith Suderman
 */
public class Literal<T> extends Operand
{
	protected final T value;

	public Literal(T value)
	{
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public String print()
	{
		return value.toString();
	}

	public int readInt(Memory memory)
	{
		if (value instanceof Integer)
		{
			return (Integer) value;
		}
		throw new RuntimeException("Literal is not an Integer.");
	}

	public float readFloat(Memory memory)
	{
		if (value instanceof Float)
		{
			return (Float) value;
		}
		throw new RuntimeException("Literal is not a Float.");
	}

	@Override
	public int address(Memory memory)
	{
		throw new UnsupportedOperationException("Can not get the address of a literal value.");
	}

	@Override
	public byte[] dereference(Memory memory)
	{
		throw new UnsupportedOperationException("Can not dereference a literal value.");
	}

	public byte[] read(Memory memory)
	{
		if (value instanceof Integer)
		{
			return Memory.convert((Integer)value);
		}
		return Memory.convert((Float) value);
	}

	public void write(Memory memory)
	{
		throw new RuntimeException("Invalid destination: Can not write to a literal destination.");
	}

	public void write(Memory memory, byte[] ignored)
	{
		throw new RuntimeException("Can not use a literal value as a destination.");
	}
}
