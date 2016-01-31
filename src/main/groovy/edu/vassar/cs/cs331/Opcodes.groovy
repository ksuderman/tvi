package edu.vassar.cs.cs331

/**
 * @author Keith Suderman
 */
class Opcodes {
    private Opcodes() { }

    protected static Map<String,Integer> operandCount = [
            call:1,
            param:1,
            push:1,
            move:2,
            inp:1,
            outp:1,
            finp:1,
            foutp:1,
            print:1,
            ltof:2,
            ftol:2,
            goto:1,
            exit:0,
            newl:0,
            PROCEND:0
    ]

    public static int getNOperands(String opcode) {
        Integer result = operandCount[opcode];
        if (result == null) {
            return 3
        }
        return result
    }
}
