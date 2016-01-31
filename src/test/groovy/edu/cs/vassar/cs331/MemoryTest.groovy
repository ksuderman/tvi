package edu.cs.vassar.cs331

import edu.vassar.cs.cs331.Memory
import org.junit.*

import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
class MemoryTest {

    @Test
    void testWrite() {
        Memory memory = new Memory();
        int size = memory.write(0, 12345);
        byte[] buffer = memory.read(0, size)
        assertTrue 12345 == Memory.asInt(buffer)

        size = memory.write(1024, -1);
        buffer = memory.read(1024, size)
        assertTrue(-1 == Memory.asInt(buffer))
    }

    @Test
    void testConversion() {
        Memory memory = new Memory()
        int size = memory.write(0, 3.1415)
        println "Wrote ${size} bytes"
        byte[] buffer = memory.read(0, size);
        println "As int " + Memory.asInt(buffer)
        assertTrue(3.1415 - Memory.asFloat(buffer) < 0.001f)
    }
}
