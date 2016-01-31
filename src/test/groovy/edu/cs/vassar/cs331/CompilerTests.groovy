package edu.cs.vassar.cs331

import org.junit.*
import edu.vassar.cs.cs331.TVICompiler;

/**
 * @author Keith Suderman
 */
//@Ignore
class CompilerTests {
//    edu.cs.vassar.cs331.Compiler compiler
    TVICompiler compiler

    @Before
    void setup() {
        compiler = new TVICompiler()
        compiler.debugging = true;
        compiler.tracingEnabled = true;
    }

    @After
    void cleanup() {
        compiler = null
    }

    @Test(expected=AssertionError)
    void assertTest() {
        String code = """
CODE
            assert 4, 4
            move 1, _0
            move 1, _4
            assert _0, _4
            move 2, _4
            assert _0, _4
            print "Done"
            newl
            exit
"""
        compiler.compile(code)
    }

    @Test
    void testAlloc() {
        String code = """
CODE
    print "before alloc"
    newl
    dump
    alloc 8
    print "after alloc, before main"
    newl
    dump
    call main
    print "after main"
    newl
    dump
    assert 0, @%0
    exit

    PROCBEGIN main
    print "in main"
    newl
    dump
    alloc 8
    print "main alloc"
    newl
    dump
    assert 8, @%0
    assert 12, @%4
    ;free 8
    print "in main, after free"
    newl
    dump
    PROCEND

"""
        compiler.compile(code)
    }
    @Test
    void testPrint() {
        String code = """
CODE
    print "Hello world"
    newl
    print "Again"
    newl
    newl
"""
        compiler.compile(code)
    }

    @Test
    void testGoto() {
        String code = """
CODE
    1:  print "one"
        newl
        goto 101
    2:  print "two"
        newl
        goto 102
    101: print "101"
        newl
        goto 2
    102: print "102"
        newl
        print "Done"
        newl
        exit
"""
        compiler.compile(code)
    }

    @Test
    void testSubtraction() {
        String code = """
CODE
    move 2, _0
    move 1, _4
    sub  _0, _4, _8
    outp _8
    assert _4, _8
    newl
"""
        compiler.compile(code)
    }

    @Test
    void testCall() {
        String code = """
CODE
    call main
    print "Done."
    newl
    exit

    PROCBEGIN main
        print "Hello world"
        newl
    PROCEND
"""
        compiler.compile(code)
    }

    @Test
    void testDeref() {
        String code = """
CODE
    alloc 8
    move 1, %4
    push @%4
    print "Memory location contains: "
    outp %4
    newl
    call main
    print "Now contains: "
    outp %4
    newl
    exit

    PROCBEGIN main
        alloc 8
        print "In main "
        outp %0
        print " contains "
        outp ^%0
        newl
        move 10, ^%0
        print "now it contains "
        outp ^%0
        newl
        free 8
    PROCEND
"""
        compiler.compile(code)
    }

    @Test
    void testParams() {
        String code = """
CODE
    move 5, _0
    push _0
    call main
    print "Done."
    newl
    exit

    PROCBEGIN main
    alloc 8
    print "In main"
    newl
    outp %0
    newl
    push @%4
    call sub1
    print "Returned value is "
    outp %4
    newl
    free 8
    PROCEND

    PROCBEGIN sub1
    alloc 4
    print "In sub1 "
    outp %0
    newl
    move 99, ^%0
    free 4
    PROCEND
"""
        compiler.compile(code)
    }
}
