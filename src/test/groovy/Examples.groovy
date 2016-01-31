import org.junit.Ignore

/**
 * @author Keith Suderman
 */
@Ignore
class Examples {

    void run() {
        print "Enter something: "
        List<String> input = System.in.readLines()
        if (input) {
            println "You entered ${input.size()} lines of input."
            input.each { println it }
        }
        println "Done."
    }

    static void main(String[] args) {
        new Examples().run()
    }
}
