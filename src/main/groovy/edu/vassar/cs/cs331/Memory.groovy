package edu.vassar.cs.cs331

/**
 * @author Keith Suderman
 */
class Memory {
    public static final int SIZE = 4096

    private byte[] memory = new byte[SIZE];

    /*
     * The current Frame Pointer.  This is offset to the start of the
     * stack frame.
     */
    int FP = 0
    /*
     * The Top Of Frame. The offset to the to top of the current stack frame.
     */
    int TOF = 0
    /*
     * The Top Of Stack. The memory location values are "push"ed to by the
     * PUSH/PARAM opcodes.
     */
    int TOS = 0

    public Memory() { }

    public byte[] read(int offset, int size) {
        byte[] buffer = new byte[size]
        int end = offset + size
        for (int i = 0; i < size; ++i) {
            buffer[i] = memory[offset + i]
        }
        return buffer;
    }

    public int write(int offset, int value) {
        return write(offset, Memory.convert(value));
    }

    public int write(int offset, float value) {
        return write(offset, Memory.convert(value));
    }

    public int write(int offset, byte[] buffer) {
        for (int i = 0; i < buffer.length; ++i) {
            memory[offset + i] = buffer[i]
        }
        return buffer.length
    }

    public static byte[] convert(float value) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream()
        DataOutputStream output = new DataOutputStream(stream)
        output.writeFloat(value)
        output.close();
        return stream.toByteArray();
    }

    public static  byte[] convert(int value) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream()
        DataOutputStream output = new DataOutputStream(stream)
        output.writeInt(value)
        output.close();
        return stream.toByteArray();
    }

    public static int asInt(byte[] buffer) {
//        println "asInt buffer: ${buffer}"
        ByteArrayInputStream stream = new ByteArrayInputStream(buffer)
        DataInputStream input = new DataInputStream(stream)
        return input.readInt()
    }

    public static float asFloat(byte[] buffer) {
//        println "asFloat buffer: ${buffer}"
        ByteArrayInputStream stream = new ByteArrayInputStream(buffer)
        DataInputStream input = new DataInputStream(stream)
        return input.readFloat()
    }

    public void push(byte[] buffer) {
        TOS = TOS + this.write(TOS, buffer)
    }

    public void push(int value) {
        TOS = TOS + this.write(TOS, value)
    }

    public void push(float value) {
        TOS = TOS + this.write(TOS, value)
    }

    public void alloc(int size) {
        TOF += size;
        TOS = TOF
    }

    public void free(int size) {
        TOF -= size
        TOS = TOF
    }
}
