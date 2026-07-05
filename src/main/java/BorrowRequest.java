public class BorrowRequest {
    public final String memberId;
    public final String isbn;
    public final int borrowDays;
    
    // Optional fields for new member registration
    public final String memberName;
    public final String memberEmail;
    public final String memberPhone;
    public final String memberAddress;
    public final boolean isNewMember;
    
    // Optional fields for new book registration
    public final String bookTitle;
    public final String bookAuthor;
    public final String bookCategory;

    // Primary constructor for existing member and book
    public BorrowRequest(String memberId, String isbn, int borrowDays) {
        this(memberId, isbn, borrowDays, null, null, null, null, false, null, null, null);
    }

    // Constructor with new member fields
    public BorrowRequest(String memberId, String isbn, int borrowDays, 
                         String memberName, String memberEmail, String memberPhone, String memberAddress, 
                         boolean isNewMember) {
        this(memberId, isbn, borrowDays, memberName, memberEmail, memberPhone, memberAddress, isNewMember, null, null, null);
    }

    // Full constructor
    public BorrowRequest(String memberId, String isbn, int borrowDays, 
                         String memberName, String memberEmail, String memberPhone, String memberAddress, 
                         boolean isNewMember, String bookTitle, String bookAuthor, String bookCategory) {
        this.memberId = memberId;
        this.isbn = isbn;
        this.borrowDays = borrowDays;
        this.memberName = memberName;
        this.emailOrNull(memberEmail); // simple storage
        this.memberEmail = memberEmail;
        this.memberPhone = memberPhone;
        this.memberAddress = memberAddress;
        this.isNewMember = isNewMember;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookCategory = bookCategory;
    }
    
    private void emailOrNull(String email) {
        // Just storage wrapper helper if needed, no-op for now
    }
}
