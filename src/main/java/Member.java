import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Member {
    public static final int MAX_BORROW_LIMIT = 5;
    public static final double MAX_FINE_LIMIT = 25.0;
    
    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Date membershipDate;
    private List<String> borrowedBooks;
    private int borrowCount;
    private double totalFines;
    
    public Member(String memberId, String name, String email, String phoneNumber, String address) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.membershipDate = new Date();
        this.borrowedBooks = new ArrayList<>();
        this.borrowCount = 0;
        this.totalFines = 0.0;
    }
    
    // Getters and setters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public Date getMembershipDate() { return membershipDate; }
    public List<String> getBorrowedBooks() { return borrowedBooks; }
    public int getBorrowCount() { return borrowCount; }
    public double getTotalFines() { return totalFines; }
    
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAddress(String address) { this.address = address; }
    public void setBorrowCount(int borrowCount) { this.borrowCount = borrowCount; }
    public void setTotalFines(double totalFines) { this.totalFines = totalFines; }
    
    public void addBorrowedBook(String isbn) {
        borrowedBooks.add(isbn);
        borrowCount++;
    }
    
    public void removeBorrowedBook(String isbn) {
        borrowedBooks.remove(isbn);
    }

    public boolean hasReachedBorrowLimit() {
        return borrowedBooks.size() >= MAX_BORROW_LIMIT;
    }

    public boolean hasOutstandingFinesExceeded() {
        return totalFines > MAX_FINE_LIMIT;
    }

    public boolean canBorrow() {
        return !hasReachedBorrowLimit() && !hasOutstandingFinesExceeded();
    }

    public boolean isValid() {
        return validateProfile(name, email, phoneNumber, address);
    }

    public static boolean validateProfile(String name, String email, String phone, String address) {
        if (name == null || name.trim().length() < 2) {
            return false;
        }
        if (email == null || !email.contains("@") || !email.contains(".")) {
            return false;
        }
        if (phone == null || phone.length() < 10) {
            return false;
        }
        if (address == null || address.trim().length() < 5) {
            return false;
        }
        return true;
    }

    public double calculateRisk() {
        double risk = 0.0;
        risk += borrowedBooks.size() * 0.1;
        risk += totalFines * 0.05;
        risk += borrowCount * 0.02;
        
        if (borrowedBooks.size() > 3) {
            risk += 1.5;
        }
        
        if (totalFines > 10.0) {
            risk += 2.0;
        }
        
        long daysSinceMembership = (new Date().getTime() - membershipDate.getTime()) / DateUtils.MILLISECONDS_IN_DAY;
        if (daysSinceMembership < 30) {
            risk += 1.0;
        }
        
        return risk;
    }
    
    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", borrowedBooks=" + borrowedBooks.size() +
                '}';
    }
}