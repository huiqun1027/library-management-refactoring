public class MemberValidator {
    
    public boolean validateMemberForBorrowing(Member member, String newName, String newEmail, 
                                             String newPhone, String newAddress, int maxBooks, 
                                             double maxFines, boolean checkHistory, boolean updateInfo, 
                                             boolean sendNotification, String notificationMessage) {
        
        // Delegate to Member's internal profile validation
        if (member == null || !member.isValid()) {
            return false;
        }
        
        // Delegate limit checks to Member's methods
        if (member.hasReachedBorrowLimit()) {
            return false;
        }
        
        if (member.hasOutstandingFinesExceeded()) {
            return false;
        }
        
        if (checkHistory && member.getBorrowCount() > 50) {
            return false;
        }
        
        return true;
    }
    
    public boolean validateMemberForRegistration(String name, String email, String phone, String address) {
        return Member.validateProfile(name, email, phone, address);
    }
    
    public double calculateMemberRisk(Member member) {
        if (member == null) return 0.0;
        return member.calculateRisk();
    }
}