package edu.vassar.cs.cs331

import edu.vassar.cs.cs331.parser.Operand
import edu.vassar.cs.cs331.parser.TVIParserConstants

/**
 * @author Keith Suderman
 */
class Quadruple {
    int opcode
    Operand operand1
    Operand operand2
    Operand operand3
    Quadruple next

    Quadruple(int opcode, Operand operand1) {
        this.opcode = opcode
        this.operand1 = operand1
    }

    Quadruple(int opcode, Operand operand1, Operand operand2) {
        this.opcode = opcode
        this.operand1 = operand1
        this.operand2 = operand2
    }

    Quadruple(int opcode, Operand operand1, Operand operand2, Operand operand3) {
        this.opcode = opcode
        this.operand1 = operand1
        this.operand2 = operand2
        this.operand3 = operand3
    }

    Quadruple(int opcode, List<Operand> operands) {
        this.opcode = opcode;
        operand1 = operands[0]
        if (operands.size() > 1) {
            operand2 = operands[1]
            if (operands.size() > 2) {
                operand3 = operands[2]
            }
        }
    }

    String print() {
        String code = TVIParserConstants.tokenImage[opcode]
        if (operand3) {
            return "${code} ${operand1.print()}, ${operand2.print()}, ${operand3.print()}"
        }
        if (operand2) {
            return "${code} ${operand1.print()}, ${operand2.print()}"
        }
        if (operand1) {
            return "${code} ${operand1.print()}"
        }
        return code
    }
//    Quadruple next() { return next }
//    void append(Quadruple next) {
//        this.next = next
//    }
}
