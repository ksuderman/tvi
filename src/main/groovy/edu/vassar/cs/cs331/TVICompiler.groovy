package edu.vassar.cs.cs331

import edu.vassar.cs.cs331.parser.TVIParser

/**
 * @author Keith Suderman
 */
class TVICompiler {

    boolean tracingEnabled = false;
    boolean debugging = false;

    public void compile(File file) {
        compile(new FileReader(file))
    }

    public void compile(String code) {
        compile(new StringReader(code))
    }

    public void compile(Reader reader) {
        TVIParser parser = new TVIParser(reader)
        if (tracingEnabled) {
            parser.enable_tracing()
        }
        else {
            parser.disable_tracing()
        }

        Processor cpu = parser.Program();
        println "Code size returned: ${cpu.instructions.size()}"
        if (debugging) {
            cpu.tracing = true;
        }
        cpu.run();
    }

//    public void run() {
//        compile(new StringReader(code));
//        println "Done."
//    }

    public static void main(String[] args) {
        CliBuilder cli = new CliBuilder()
        cli.header = "The Vassar Interpreter"
        cli.usage = "java -jar tvi-${Version.version} [options] <filename>"
        cli.v(longOpt:'version', 'displays the current version number.')
        cli.d(longOpt:'debug', 'enables debugging output during execution.')
        cli.p(longOpt:'parser', 'enables verbose mode in the parser.')
        cli.h(longOpt:'help', 'prints this help message.')

        boolean parseTrace = false;
        boolean debugTrace = false;
        def params = cli.parse(args)
        if (!params) {
            return
        }
        if (params.h) {
            println()
            cli.usage()
            println()
            return
        }
        if (params.v) {
            println()
            println "The Vassar Interpreter v${Version.version}"
            println "Copyright 2015 Vassar College. All rights reserved."
            println()
            return
        }
        if (params.d) {
            println "Runtime debugging output enabled."
            debugTrace = true
        }
        if (params.p) {
            println "Parser debugging output enabled."
            parseTrace = true
        }
        def filename = params.arguments()
        if (filename == null || filename.size() == 0) {
            println "No filename provided."
            return
        }
        File file = new File(filename[0])
        if (!file.exists()) {
            println "File not found: ${file.path}"
            return
        }

        TVICompiler compiler = new TVICompiler()
        if (parseTrace) {
            compiler.tracingEnabled = true
        }
        if (debugTrace) {
            compiler.debugging = true
        }
        compiler.compile(file)
    }
}
