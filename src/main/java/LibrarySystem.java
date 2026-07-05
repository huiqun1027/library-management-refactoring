import java.util.*;

// Refactored LibrarySystem: Clean, cohesive, single responsibility delegates.
public class LibrarySystem {
    private Map<String, Book> books;
    private Map<String, Member> members;
    private List<String> transactionHistory;
    
    public LibrarySystem() {
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }
    
    // Backward-compatible overload with 11 parameters
    public boolean borrowBook(String memberId, String isbn, int borrowDays, String memberName, 
                              String memberEmail, String memberPhone, String memberAddress, 
                              boolean isNewMember, String bookTitle, String bookAuthor, String bookCategory) {
        BorrowRequest request = new BorrowRequest(
            memberId, isbn, borrowDays, 
            memberName, memberEmail, memberPhone, memberAddress, isNewMember, 
            bookTitle, bookAuthor, bookCategory
        );
        return borrowBook(request);
    }
    
    // Cleaner refactored borrow method accepting the parameter object
    public boolean borrowBook(BorrowRequest request) {
        Member member = resolveOrCreateMember(request);
        if (member == null) {
            return false;
        }
        
        if (!member.canBorrow()) {
            if (member.hasReachedBorrowLimit()) {
                System.out.println("Member has reached maximum borrowing limit of " + Member.MAX_BORROW_LIMIT + " books.");
            } else if (member.hasOutstandingFinesExceeded()) {
                System.out.println("Member has outstanding fines exceeding $" + Member.MAX_FINE_LIMIT + ". Cannot borrow.");
            }
            return false;
        }
        
        Book book = resolveOrCreateBook(request);
        if (book == null) {
            return false;
        }
        
        if (!book.isAvailable()) {
            System.out.println("Book is currently not available: " + book.getTitle());
            if (book.getDueDate() != null) {
                System.out.println("Estimated return date: " + DateUtils.formatDate(book.getDueDate()));
                if (book.isOverdue()) {
                    long overdueDays = book.getOverdueDays();
                    double fine = overdueDays * book.getFineRate();
                    System.out.println("Book is overdue by " + overdueDays + " days. Fine: $" + fine);
                }
            }
            return false;
        }
        
        // Process the borrowing
        Date dueDate = DateUtils.calculateDueDate(request.borrowDays);
        book.setAvailable(false);
        book.setDueDate(dueDate);
        book.setBorrowerMemberId(member.getMemberId());
        member.addBorrowedBook(book.getIsbn());
        
        // Record transaction
        String transaction = "BORROW: Member " + member.getMemberId() + " borrowed book " + book.getIsbn() + 
                           " (" + book.getTitle() + ") on " + DateUtils.formatDate(new Date()) + 
                           " due " + DateUtils.formatDate(dueDate);
        transactionHistory.add(transaction);
        
        printConfirmation(member, book, request.borrowDays, dueDate);
        
        return true;
    }
    
    private Member resolveOrCreateMember(BorrowRequest request) {
        Member member = members.get(request.memberId);
        if (member == null && request.isNewMember) {
            if (!Member.validateProfile(request.memberName, request.memberEmail, request.memberPhone, request.memberAddress)) {
                System.out.println("Invalid member details provided for registration.");
                return null;
            }
            
            member = new Member(request.memberId, request.memberName, request.memberEmail, request.memberPhone, request.memberAddress);
            members.put(request.memberId, member);
            System.out.println("New member created: " + request.memberName);
        } else if (member == null) {
            System.out.println("Member not found: " + request.memberId);
        }
        return member;
    }
    
    private Book resolveOrCreateBook(BorrowRequest request) {
        Book book = books.get(request.isbn);
        if (book == null) {
            if (request.bookTitle != null && request.bookAuthor != null && request.bookCategory != null) {
                book = new Book(request.isbn, request.bookTitle, request.bookAuthor, request.bookCategory);
                books.put(request.isbn, book);
                System.out.println("New book added to library: " + request.bookTitle);
            } else {
                System.out.println("Book not found and insufficient information to create new book.");
            }
        }
        return book;
    }
    
    private void printConfirmation(Member member, Book book, int borrowDays, Date dueDate) {
        System.out.println("=== BORROWING CONFIRMATION ===");
        System.out.println("Member: " + member.getName() + " (" + member.getMemberId() + ")");
        System.out.println("Book: " + book.getTitle() + " by " + book.getAuthor());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Category: " + book.getCategory());
        System.out.println("Borrow Date: " + DateUtils.formatDate(new Date()));
        System.out.println("Due Date: " + DateUtils.formatDate(dueDate));
        System.out.println("Borrowing Period: " + borrowDays + " days");
        System.out.println("Daily Fine Rate: $" + book.getFineRate());
        System.out.println("Books Currently Borrowed: " + member.getBorrowedBooks().size());
        System.out.println("Total Books Borrowed (Lifetime): " + member.getBorrowCount());
        System.out.println("==============================");
    }
    
    public boolean returnBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            System.out.println("Book not found: " + isbn);
            return false;
        }
        
        if (book.isAvailable()) {
            System.out.println("Book is not currently borrowed: " + book.getTitle());
            return false;
        }
        
        Member member = members.get(book.getBorrowerMemberId());
        double fine = 0.0;
        
        if (book.isOverdue()) {
            long overdueDays = book.getOverdueDays();
            fine = overdueDays * book.getFineRate();
            member.setTotalFines(member.getTotalFines() + fine);
        }
        
        // Process return
        book.setAvailable(true);
        book.setDueDate(null);
        member.removeBorrowedBook(isbn);
        book.setBorrowerMemberId(null);
        
        // Record transaction
        String transaction = "RETURN: Book " + isbn + " returned on " + DateUtils.formatDate(new Date()) + 
                           (fine > 0 ? " with fine $" + fine : "");
        transactionHistory.add(transaction);
        
        System.out.println("Book returned successfully" + (fine > 0 ? " with fine: $" + fine : ""));
        return true;
    }
    
    public void addBook(String isbn, String title, String author, String category) {
        if (books.containsKey(isbn)) {
            System.out.println("Book with ISBN " + isbn + " already exists.");
            return;
        }
        
        Book book = new Book(isbn, title, author, category);
        books.put(isbn, book);
        System.out.println("Book added successfully: " + title);
    }
    
    public void addMember(String memberId, String name, String email, String phone, String address) {
        if (members.containsKey(memberId)) {
            System.out.println("Member with ID " + memberId + " already exists.");
            return;
        }
        
        Member member = new Member(memberId, name, email, phone, address);
        members.put(memberId, member);
        System.out.println("Member added successfully: " + name);
    }
    
    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                book.getIsbn().contains(query)) {
                results.add(book);
            }
        }
        return results;
    }
    
    public Member getMember(String memberId) {
        return members.get(memberId);
    }
    
    public Book getBook(String isbn) {
        return books.get(isbn);
    }
    
    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
    
    public int getTotalBooks() {
        return books.size();
    }
    
    public int getTotalMembers() {
        return members.size();
    }
    
    public int getAvailableBooks() {
        int count = 0;
        for (Book book : books.values()) {
            if (book.isAvailable()) count++;
        }
        return count;
    }
}