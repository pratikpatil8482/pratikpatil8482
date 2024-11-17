package librerymanagment_dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import librerymanagment_dto.Book;
import librerymanagment_dto.Borrower;

public class BookBorrowerDao {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pratik");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    public void borrewerBook(int membership_id, long lsbn) {
        try {
            Borrower borrower = entityManager.find(Borrower.class, membership_id);
            Book book = entityManager.find(Book.class, lsbn);
            if (borrower != null && book != null) {
                book.setQuantity(book.getQuantity() - 1);
                List<Book> bookdb = borrower.getBook();
                LocalDate borrowDate = LocalDate.now();
                LocalDate borrowDue = borrowDate.plusDays(14);
                borrower.setDate(borrowDate);
                borrower.setDueDate(borrowDue);
                bookdb.add(book);
                borrower.setBook(bookdb);
                entityTransaction.begin();
                entityManager.merge(borrower);
                entityTransaction.commit();
                System.out.println(borrower);
            } else {
                System.out.println("Not Present a book or User");
            }
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.err.println("Error occurred while borrowing the book.");
        }
    }

    public void removeBorrowerBook(int membership_ID, long isbn) {
        try {
            Borrower borrower = entityManager.find(Borrower.class, membership_ID);
            List<Book> book = borrower.getBook();
            Book bookdb = entityManager.find(Book.class, isbn);
            for (Book book2 : book) {
                if (book2.getIsbn() == isbn) {
                    book.remove(book2);
                }
            }

            if (borrower != null) {
                borrower.setBook(book);
                bookdb.setQuantity(bookdb.getQuantity() + 1);
                entityTransaction.begin();
                LocalDate borrowDate = null;
                LocalDate borrowDue = null;
                borrower.setDueDate(borrowDue);
                entityManager.merge(borrower);
                System.out.println(borrower);
                entityTransaction.commit();
            }
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.err.println("Error occurred while removing the borrower book.");
        }
    }

    // update
    public void returnBook(int membership_id, long isbn) {
        try {
            Borrower borrower = entityManager.find(Borrower.class, membership_id);
            Book book = entityManager.find(Book.class, isbn);

            if (borrower != null && book != null) {
                LocalDate today = LocalDate.now();
                LocalDate dueDate = borrower.getDueDate();

                // Check overdue and calculate penalty
                if (today.isAfter(dueDate)) {
                    long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);
                    double penalty = daysOverdue * 5.0; // Penalty rate: 5 units per day
                    borrower.setPenalty(borrower.getPenalty() + penalty);
                    System.out.println("Overdue! Penalty: " + penalty + " units.");
                }

                book.setQuantity(book.getQuantity() + 1);

                List<Book> borrowedBooks = borrower.getBook();
                borrowedBooks.removeIf(b -> b.getIsbn() == isbn);
                borrower.setBook(borrowedBooks);
                borrower.setDate(null);
                borrower.setDueDate(null);
                entityTransaction.begin();
                entityManager.merge(borrower);
                entityManager.merge(book);
                entityTransaction.commit();

                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Either the book or borrower does not exist.");
            }
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.err.println("Error occurred while returning the book.");
        }
    }
}
