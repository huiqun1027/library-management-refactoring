import java.io.OutputStream;
import java.io.PrintStream;

public class BenchmarkRunner {
    public static void main(String[] args) {
        // Redirect stdout to null to measure purely execution logic, not console I/O
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // Do nothing
            }
        }));

        int iterations = 10000;
        long startTime = System.nanoTime();

        for (int i = 0; i < iterations; i++) {
            runWorkflow(i);
        }

        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0;

        // Restore stdout and print result
        System.setOut(originalOut);
        System.out.printf("Total time for %d iterations: %.2f ms%n", iterations, durationMs);
        System.out.printf("Average time per iteration: %.4f ms%n", durationMs / iterations);
    }

    private static void runWorkflow(int id) {
        LibrarySystem library = new LibrarySystem();
        
        // Add books
        library.addBook("ISBN-" + id + "-1", "Effective Java", "Joshua Bloch", "Programming");
        library.addBook("ISBN-" + id + "-2", "Head First Design Patterns", "Eric Freeman", "Programming");
        library.addBook("ISBN-" + id + "-3", "Effective C++", "Scott Meyers", "Programming");
        
        // Add members
        library.addMember("M-" + id + "-1", "Ahmed Ali", "ahmed.ali@email.com", "1234567890", "123 Gulberg Lane");
        library.addMember("M-" + id + "-2", "Fatima Khan", "fatima.khan@email.com", "0987654321", "456 DHA Avenue");
        
        // Borrow
        library.borrowBook(new BorrowRequest("M-" + id + "-1", "ISBN-" + id + "-1", 14));
        library.borrowBook(new BorrowRequest("M-" + id + "-2", "ISBN-" + id + "-2", 7));
        
        // Search
        library.searchBooks("Java");
        
        // Utilities & Risk scoring
        BookUtilities utilities = new BookUtilities();
        Book book = library.getBook("ISBN-" + id + "-1");
        utilities.generateBookReport(book);
        
        MemberValidator validator = new MemberValidator();
        Member member = library.getMember("M-" + id + "-1");
        validator.calculateMemberRisk(member);
        
        // Return
        library.returnBook("ISBN-" + id + "-1");
        library.returnBook("ISBN-" + id + "-2");
    }
}
