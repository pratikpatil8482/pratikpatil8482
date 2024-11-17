package librerymanagment_dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import librerymanagment_dto.Book;

public class BookDao {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pratik");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    public void saveBook(Book book) {
        try {
            entityTransaction.begin();
            entityManager.persist(book);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.err.println("Error occurred while saving the book.");
        }
    }

    public void updateBook(long isbn, Book book) {
        try {
            Book bookdb = entityManager.find(Book.class, isbn);
            if (bookdb != null) {
                entityTransaction.begin();
                book.setIsbn(bookdb.getIsbn());
                entityManager.merge(book);
                entityTransaction.commit();
            } else {
                System.out.println("Book is not present.");
            }
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.err.println("Error occurred while updating the book.");
        }
    }

    public void removeBook(long isbn) {
        try {
            Book bookdb = entityManager.find(Book.class, isbn);
            if (bookdb != null) {
                entityTransaction.begin();
                entityManager.remove(bookdb);
                entityTransaction.commit();
            } else {
                System.out.println("Book is not present.");
            }
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            System.err.println("Error occurred while removing the book.");
        }
    }

    public List<Book> searchBook(String value) {
        List<Book> books = null;
        try {
            String hql = "SELECT e FROM librerymanagment_dto.Book e " +
                         "WHERE e.title = :value OR e.auther = :value OR e.genre = :value";
            Query query = entityManager.createQuery(hql);
            query.setParameter("value", value);
            books = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error occurred while searching for books.");
        }
        return books != null ? books : new ArrayList<>();
    }
}
