package edu.vassar.cs.cs331

import edu.vassar.cs.cs331.parser.Literal
import edu.vassar.cs.cs331.parser.Operand
import org.anc.util.Stack

import static edu.vassar.cs.cs331.parser.TVIParserConstants.*

/**
 * @author Keith Suderman
 */
class Processor {
    List<Quadruple> instructions = []
    Memory memory = new Memory()
    Stack<Integer> callStack = new Stack<Integer>();
    Stack<Integer> frameStack = new Stack<Integer>();
    Map<Integer,Integer> labeledInstructions;
    Map<String,Integer> jumpTable;
    boolean tracing = false

    int IP = 0

    public Processor(List<Quadruple> instructions) {
        this.instructions = instructions
        memory = new Memory()
        IP = 0
    }

    int int1(Quadruple quadruple) {
        return quadruple.operand1.readInt(memory)
    }
    int int2(Quadruple quadruple) {
        return quadruple.operand2.readInt(memory)
    }
    int float1(Quadruple quadruple) {
        return quadruple.operand1.readFloat(memory)
    }
    int float2(Quadruple quadruple) {
        return quadruple.operand2.readFloat(memory)
    }
    void write(Quadruple quadruple, int value) {
        quadruple.operand3.write(memory, value)
    }
    void write(Quadruple quadruple, float value) {
        quadruple.operand3.write(memory, value)
    }

    void run() {
        byte[] buffer;
        int v1, v2;
        float f1, f2;
        String s;
        def trace = { }
        if (tracing) {
            println "Labeled Instructions:"
            labeledInstructions.each { label, offset ->
                println "Label $label : $offset"
            }
            println "Jump Table"
            jumpTable.each { name, offset ->
                println "Procedure $name : $offset"
            }
            trace = { println it }
        }
        Quadruple quad = instructions[IP]
        while (quad) {
            ++IP
            trace(quad.print())
            switch (quad.opcode) {
                case MOVE:
                    buffer = quad.operand1.read(memory)
//                    println "MOVING: ${buffer}"
                    quad.operand2.write(memory, buffer)
                    break
                case ADD:
                    v1 = int1(quad)
                    v2 = int2(quad)
                    trace "$v1 + $v2"
                    write(quad, v1 + v2)
                    break;
                case SUB:
                    v1 = int1(quad)
                    v2 = int2(quad)
                    trace "$v1 - $v2"
                    write(quad, v1 - v2)
                    break;
                case MUL:
                    v1 = int1(quad)
                    v2 = int2(quad)
                    trace "$v1 * $v2"
                    write(quad, v1 * v2)
                    break;
                case DIV:
                    v1 = int1(quad)
                    v2 = int2(quad)
                    trace "$v1 / $v2"
                    write(quad, v1 / v2);
                    break;
                case FADD:
                    f1 = float1(quad)
                    f2 = float2(quad)
                    write(quad, f1 + f2)
                    break;
                case FSUB:
                    f1 = float1(quad)
                    f2 = float2(quad)
                    write(quad, f1 - f2)
                    break;
                case FMUL:
                    f1 = float1(quad)
                    f2 = float2(quad)
                    write(quad, f1 * f2)
                    break;
                case FDIV:
                    f1 = float1(quad)
                    f2 = float2(quad)
                    write(quad, f1 / f2)
                    break;
                case LTOF:
                    v1 = int1(quad)
                    quad.operand2.write(memory, (float)v1);
                    break;
                case FTOL:
                    f1 = float1(quad)
                    quad.operand2.write(memory, (int)f1);
                    break;
                case OUTP:
                    v1 = int1(quad)
                    print v1
                    break
                case FOUTP:
                    print float1(quad)
                    break
                case GOTO:
                    v1 = int1(quad)
                    IP = labeledInstructions[v1]
                    break
                case PRINT:
                    s = quad.operand1.print()
                    print s[1..-2]
                    break
                case NEWL:
                    println()
                    break
                case BEQ:
                    v1 = int1(quad)
                    v2 = int2(quad)
                    if (v1 == v2) {
                        trace("IP: ${IP}")
                        v1 = quad.operand3.readInt(memory)
                        IP = labeledInstructions[v1]
                        trace "Branching to label ${v1} at offset ${IP}"
                    }
                    break;
                case BNE:
                    v1 = int1(quad)
                    v2 = int2(quad)
                    if (v1 != v2) {
                        v1 = quad.operand3.readInt(memory)
                        IP = labeledInstructions[v1]
                    }
                    break;
                case BLT:
                    v1 = int1(quad)
                    v2 = int2(quad)
                    if (v1 < v2) {
                        v1 = quad.operand3.readInt(memory)
                        IP = labeledInstructions[v1]
                    }
                    break;
                case BLE:
                    v1 = int1(quad)
                    v2 = int2(quad)
                    if (v1 <= v2) {
                        v1 = quad.operand3.readInt(memory)
                        IP = labeledInstructions[v1]
                    }
                    break;
                case BGT:
                    v1 = int1(quad)
                    v2 = int2(quad)
                    if (v1 > v2) {
                        v1 = quad.operand3.readInt(memory)
                        IP = labeledInstructions[v1]
                    }
                    break;
                case BGE:
                    v1 = int1(quad)
                    v2 = int2(quad)
                    if (v1 >= v2) {
                        v1 = quad.operand3.readInt(memory)
                        IP = labeledInstructions[v1]
                    }
                    break;
                case CALL:
                    s = quad.operand1.print()
                    callStack.push(IP)
                    frameStack.push(memory.FP)
                    memory.FP = memory.TOF
                    memory.TOS = memory.TOF
                    IP = jumpTable[s]
                    break
                case PROCEND:
                    IP = callStack.pop()
                    memory.TOF = memory.FP
                    memory.TOS = memory.TOF
                    memory.FP = frameStack.pop()
                    break
                case ALLOC:
                    v1 = int1(quad)
                    memory.alloc(v1)
                    break
                case FREE:
                    memory.free(int1(quad))
                    break
                case PARAM:
                case PUSH:
                    v1 = int1(quad)
                    memory.push(v1)
                    break
                case EXIT:
                    IP = instructions.size()
                    break;
                case INP:
                    String input = System.console().readLine()
                    if (!input.isInteger()) {
                        throw new RuntimeException("Invalid input for inp instruction")
                    }
                    int n = Integer.parseInt(input)
                    quad.operand1.write(memory, n)
                    break;
                case FINP:
                    String input = System.console().readLine()
                    if (!input.isFloat()) {
                        throw new RuntimeException("Invalid input for inp instruction")
                    }
                    float f = Float.parseFloat(input)
                    quad.operand1.write(memory, f)
                    break;
                case ASSERT:
                    v1 = int1(quad)
                    v2 = int2(quad)
                    if (v1 != v2)
                    {
                        throw new AssertionError("Assertion failed $v1 != $v2");
                    }
                    trace "Assertion passed $v1 == $v2"
                    break;
                case DUMP:
                    println("FP : " + memory.FP)
                    println("TOF: " + memory.TOF)
                    println("TOS: " + memory.TOS)
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown opcode " + quad.opcode)
            }
            if (IP < instructions.size()) {
                quad = instructions[IP]
            }
            else {
                quad = null
            }
        }
        println "Program terminated."
    }

    static void main(String... args) {
        Processor cpu = new Processor()
        cpu.init()
        cpu.run()
    }
}
