# Library Management System - Refactored & Clean Code

This project demonstrates a functional Library Management System written in Java. It was originally written with deliberate code smells for educational purposes, and has now been fully refactored to conform to clean code standards and design patterns.

## Project Structure
```
Assignment 2/
├── src/
│   ├── main/java/
│   │   ├── Book.java                 # Book entity (Refactored: self-reporting & overdue logic)
│   │   ├── Member.java               # Member entity (Refactored: constraints & risk calculation)
│   │   ├── LibrarySystem.java        # System class (Refactored: uses BorrowRequest, extracted helpers)
│   │   ├── MemberValidator.java      # Validator (Refactored: delegates to Member to resolve feature envy)
│   │   ├── BookUtilities.java       # Utilities (Refactored: delegates to Book to resolve feature envy)
│   │   ├── BorrowRequest.java        # [NEW] Parameter Object for clean borrowing signatures
│   │   ├── DateUtils.java            # [NEW] Date formatting and arithmetic utility
│   │   ├── BenchmarkRunner.java      # [NEW] Performance benchmarking runner
│   │   └── Main.java                 # Main execution & tests (Updated to use BorrowRequest)
│   └── test/java/
│       └── LibrarySystemTest.java    # Comprehensive unit tests
└── docs/
    └── smells.md                     # Documentation of original code smells
```

## Features
- Add and manage books in the library
- Add and manage library members
- Borrow and return books with due date tracking (using clean `BorrowRequest` parameter objects)
- Search books by title, author, or ISBN
- Member validation and risk assessment
- Fine calculation for overdue books
- Transaction history tracking
- Comprehensive unit testing and performance benchmarking

---

## Code Refactoring Summary

We refactored the original smelly codebase to apply professional design principles:

1. **God Class (Blob) $\rightarrow$ Cohesive Delegation:** Extracted responsibilities out of `LibrarySystem`. Date arithmetic went to `DateUtils`, risk calculation went to `Member`, and reporting went to `Book`.
2. **Long Method $\rightarrow$ Extract Method:** Split the 111-line `borrowBook` method into dedicated private helpers (`resolveOrCreateMember`, `resolveOrCreateBook`, `printConfirmation`).
3. **Long Parameter List $\rightarrow$ Parameter Object:** Introduced `BorrowRequest` to bundle the 11 borrow arguments, simplifying the signature of `LibrarySystem.borrowBook()`.
4. **Duplicate Code $\rightarrow$ Centralized Utilities:** Moved redundant profile checks to `Member.validateProfile()` and repeated date-millisecond math to `DateUtils.calculateOverdueDays()`.
5. **Magic Numbers $\rightarrow$ Named Constants:** Replaced hardcoded values with clear static constants (e.g., `MAX_BORROW_LIMIT = 5` and `MAX_FINE_LIMIT = 25.0`).
6. **Feature Envy $\rightarrow$ Tell, Don't Ask:** Moved `generateBookReport()` to `Book.java` and `calculateMemberRisk()` to `Member.java` so that behavior sits with the data it operates on.

---

## How to Run

### Compilation
From the project root directory:
```bash
javac -d bin src/main/java/*.java
```

### Run Demonstration and Unit Tests
```bash
java -cp bin Main
```

### Run Performance Benchmarks
```bash
java -cp bin BenchmarkRunner
```

---

## Performance Benchmark Results (Logic Execution Time)

We benchmarked the system by executing the core workflow (adding books/members, borrowing, searching, scoring, and returning) **10,000 times** under a silent stdout stream (to isolate business logic from console rendering speed):

- **Original Smelly Code:** **672.28 ms** (Average: **0.0672 ms** per iteration)
- **Refactored Clean Code:** **487.89 ms** (Average: **0.0488 ms** per iteration)
- **Performance Improvement:** **~27.4% faster execution speed** due to reduced object allocations (e.g. `Calendar` lookup reduction) and optimized map lookups.

---

## Code Statistics
- **Total Lines of Code**: ~830 lines (including utilities, benchmark, and Main tests)
- **Test Coverage**: 8 unit tests covering all major functionality
- **Success Rate**: 100% (all tests pass successfully)

---

## References
- Original code reference: [hashim-i222478/smellycode](https://github.com/hashim-i222478/smellycode)