import java.util.Date;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String category;
    private boolean isAvailable;
    private Date dueDate;
    private String borrowerMemberId;
    
    public Book(String isbn, String title, String author, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isAvailable = true;
        this.dueDate = null;
        this.borrowerMemberId = null;
    }
    
    // Getters and setters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return isAvailable; }
    public Date getDueDate() { return dueDate; }
    public String getBorrowerMemberId() { return borrowerMemberId; }
    
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public void setBorrowerMemberId(String borrowerMemberId) { this.borrowerMemberId = borrowerMemberId; }
    
    public boolean isOverdue() {
        return DateUtils.calculateOverdueDays(dueDate) > 0;
    }

    public long getOverdueDays() {
        return DateUtils.calculateOverdueDays(dueDate);
    }

    public double getFineRate() {
        if ("Reference".equals(category)) {
            return 1.0;
        } else if ("Children".equals(category)) {
            return 0.25;
        } else if ("Non-Fiction".equals(category)) {
            return 0.75;
        }
        return 0.5; // Default (Fiction, etc.)
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== BOOK REPORT ===\n");
        report.append("ISBN: ").append(isbn).append("\n");
        report.append("Title: ").append(title).append("\n");
        report.append("Author: ").append(author).append("\n");
        report.append("Category: ").append(category).append("\n");
        report.append("Available: ").append(isAvailable ? "Yes" : "No").append("\n");
        
        if (!isAvailable) {
            report.append("Borrower: ").append(borrowerMemberId).append("\n");
            report.append("Due Date: ").append(dueDate).append("\n");
            
            if (isOverdue()) {
                long overdueDays = getOverdueDays();
                double totalFine = overdueDays * getFineRate();
                report.append("Overdue Days: ").append(overdueDays).append("\n");
                report.append("Fine Amount: $").append(totalFine).append("\n");
            }
        }
        
        report.append("==================\n");
        return report.toString();
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}