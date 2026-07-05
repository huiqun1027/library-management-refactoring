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

## Identified Code Smells (Original State)
1. **God Class (Blob):** The `LibrarySystem` class handled books, members, transaction history, fine rules, and system logic.
2. **Long Method:** The `borrowBook()` method in `LibrarySystem` was 111 lines long, handling multiple nested responsibilities.
3. **Long Parameter List:** Methods like `borrowBook()` and `validateMemberForBorrowing()` had 11 parameters in their signatures.
4. **Duplicate Code:** Dates, overdue computations, and profile field validations were copy-pasted across validator and utility files.
5. **Magic Numbers:** Literal numbers like `5` (borrow limit) and `25.0` (fine limit) were hardcoded throughout the logic.
6. **Feature Envy:** `BookUtilities` and `MemberValidator` classes were excessively querying data getters/seters of domain entities.

---

## Code Refactoring Summary (Clean State)
We refactored the original smelly codebase to apply clean design principles:
1. **God Class $\rightarrow$ Cohesive Delegation:** Responsibilities were moved from `LibrarySystem` into `DateUtils`, `Member`, and `Book`.
2. **Long Method $\rightarrow$ Extract Method:** Split `borrowBook()` into small, cohesive helpers (`resolveOrCreateMember()`, `resolveOrCreateBook()`, `printConfirmation()`).
3. **Long Parameter List $\rightarrow$ Parameter Object:** Introduced `BorrowRequest` to wrap the 11 parameters in a clean object container.
4. **Duplicate Code $\rightarrow$ Centralized Utilities:** Extracted duplicated calculations to `DateUtils` and profile checks to static `Member.validateProfile()`.
5. **Magic Numbers $\rightarrow$ Named Constants:** Replaced hardcoded values with descriptive constants (`MAX_BORROW_LIMIT = 5`, `MAX_FINE_LIMIT = 25.0`).
6. **Feature Envy $\rightarrow$ Tell, Don't Ask:** Moved `generateReport()` into `Book.java` and `calculateRisk()` into `Member.java` so behavior sits with the data it uses.

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