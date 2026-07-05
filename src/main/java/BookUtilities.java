public class BookUtilities {
    
    public boolean isBookOverdue(Book book) {
        if (book == null) return false;
        return book.isOverdue();
    }
    
    public long calculateOverdueDays(Book book) {
        if (book == null) return 0;
        return book.getOverdueDays();
    }
    
    public String generateBookReport(Book book) {
        if (book == null) return "";
        return book.generateReport();
    }
    
    public boolean isPopularCategory(String category) {
        if (category == null) return false;
        switch (category) {
            case "Fiction":
            case "Non-Fiction":
            case "Children":
                return true;
            default:
                return false;
        }
    }
}