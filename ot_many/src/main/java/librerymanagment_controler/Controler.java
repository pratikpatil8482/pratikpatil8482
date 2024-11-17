package librerymanagment_controler;

import java.util.List;
import java.util.Scanner;

import librerymanagment_dao.BookBorrowerDao;
import librerymanagment_dao.BookDao;
import librerymanagment_dao.BorrowerDao;
import librerymanagment_dto.Book;
import librerymanagment_dto.Borrower;

public class Controler {

    public static void main(String[] args) {

        BookDao bookDao = new BookDao();
        BorrowerDao borrowerDao = new BorrowerDao();
        BookBorrowerDao bookBorrowerDao = new BookBorrowerDao();

        while (true) {
            try {
                System.out.println("\nWelcome ,\n" + "1) Save Book\n" + "2) Update Book\n" + "3) Remove Book\n"
                        + "4) Save Borrower\n" + "5) Update Borrower\n" + "6) Remove Borrower\n" + "7) Borrow a Book\n"
                        + "8) Return a Book\n" + "9) View All Borrowers\n" + "10)Search Books\n" + "11)View Penalty\n"
                        + "12)Clear Penalty\n" + "13)Exit");
                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: {
                        System.out.println("=== Save Book ===");
                        Book book = new Book();
                        System.out.print("Enter ISBN for Book: ");
                        book.setIsbn(sc.nextLong());

                        System.out.print("Enter Title for Book: ");
                        book.setTitle(sc.next());

                        System.out.print("Enter Author for Book: ");
                        book.setAuther(sc.next());

                        System.out.print("Enter Genre for Book: ");
                        book.setGenre(sc.next());

                        System.out.print("Enter Quantity of Book: ");
                        book.setQuantity(sc.nextInt());

                        bookDao.saveBook(book);
                        System.out.println("Book Saved Successfully!");
                        break;
                    }
                    case 2: {
                        System.out.println("=== Update Book ===");
                        System.out.print("Enter ISBN No for Book to Update: ");
                        long isbn = sc.nextLong();
                        Book book = new Book();

                        System.out.print("Enter Title for Book: ");
                        book.setTitle(sc.next());

                        System.out.print("Enter Author for Book: ");
                        book.setAuther(sc.next());

                        System.out.print("Enter Genre for Book: ");
                        book.setGenre(sc.next());

                        System.out.print("Enter Quantity of Book: ");
                        book.setQuantity(sc.nextInt());

                        bookDao.updateBook(isbn, book);
                        System.out.println("Book Updated Successfully!");
                        break;
                    }
                    case 3: {
                        System.out.println("=== Remove Book ===");
                        System.out.print("Enter ISBN No of the Book to Remove: ");
                        bookDao.removeBook(sc.nextLong());
                        System.out.println("Book Removed Successfully!");
                        break;
                    }
                    case 4: {
                        System.out.println("=== Save Borrower ===");
                        Borrower borrower = new Borrower();
                        System.out.print("Enter Borrower Name: ");
                        borrower.setName(sc.next());

                        System.out.print("Enter Borrower Mobile: ");
                        borrower.setMobile(sc.nextLong());

                        borrowerDao.saveBorrower(borrower);
                        System.out.println("Borrower Saved Successfully!");
                        break;
                    }
                    case 5: {
                        System.out.println("=== Update Borrower ===");
                        System.out.print("Enter Borrower Membership ID: ");
                        int membershipId = sc.nextInt();
                        Borrower borrower = new Borrower();

                        System.out.print("Enter Borrower Name: ");
                        borrower.setName(sc.next());

                        System.out.print("Enter Borrower Mobile: ");
                        borrower.setMobile(sc.nextLong());

                        borrowerDao.updateBorrower(membershipId, borrower);
                        System.out.println("Borrower Updated Successfully!");
                        break;
                    }
                    case 6: {
                        System.out.println("=== Remove Borrower ===");
                        System.out.print("Enter Borrower Membership ID: ");
                        borrowerDao.removeBook(sc.nextInt());
                        System.out.println("Borrower Removed Successfully!");
                        break;
                    }
                    case 7: {
                        System.out.println("=== Borrow a Book ===");
                        System.out.print("Enter Borrower Membership ID: ");
                        int membershipId = sc.nextInt();

                        System.out.print("Enter Book ISBN: ");
                        long isbn = sc.nextLong();

                        bookBorrowerDao.borrewerBook(membershipId, isbn);
                        System.out.println("Book Borrowed Successfully!");
                        break;
                    }

                    case 8: {
                        System.out.println("=== Return a Book ===");
                        System.out.print("Enter Borrower Membership ID: ");
                        int membershipId = sc.nextInt();

                        System.out.print("Enter Book ISBN: ");
                        long isbn = sc.nextLong();

                        bookBorrowerDao.returnBook(membershipId, isbn);
                        break;
                    }

                    case 9: {
                        System.out.println("=== View All Borrowers ===");
                        borrowerDao.allBorrower();
                        break;
                    }
                    case 10: {
                        System.out.println("=== Search Books ===");
                        System.out.print("Enter Book Title, Author Name, or Genre to Search: ");
                        String searchValue = sc.next();
                        List<Book> books = bookDao.searchBook(searchValue);

                        if (books.isEmpty()) {
                            System.out.println("No books found for the given criteria.");
                        } else {
                            System.out.println("Books Found:");
                            for (Book book : books) {
                                System.out.println("ISBN: " + book.getIsbn() + ", Title: " + book.getTitle() + ", Author: "
                                        + book.getAuther() + ", Genre: " + book.getGenre() + ", Quantity: "
                                        + book.getQuantity());
                            }
                        }
                        break;
                    }

                    case 11: {
                        System.out.println("=== View Borrower Penalty ===");
                        System.out.print("Enter Borrower Membership ID: ");
                        int membershipId = sc.nextInt();

                        Borrower borrower = borrowerDao.findBorrower(membershipId);
                        if (borrower != null) {
                            System.out.println("Penalty Amount: " + borrower.getPenalty());
                        } else {
                            System.out.println("Borrower not found.");
                        }
                    }
                        break;
                    case 12: {

                        System.out.println("=== Clear Borrower Penalty ===");
                        System.out.print("Enter Borrower Membership ID: ");
                        int membershipId = sc.nextInt();

                        Borrower borrower = borrowerDao.findBorrower(membershipId);
                        if (borrower != null) {
                            borrower.setPenalty(0.0);
                            borrowerDao.updateBorrower(membershipId, borrower);
                            System.out.println("Penalty cleared successfully.");
                        } else {
                            System.out.println("Borrower not found.");
                        }
                        break;
                    }
                    case 13: {

                        System.out.println("Exit Successful! Goodbye!");
                        System.exit(0);

                    }
                    default: {
                        System.out.println("Invalid Choice. Please try again.");
                    }
                }
            } catch (Exception e) {
                System.err.println("Error occurred: ");
            }
        }
    }
}
