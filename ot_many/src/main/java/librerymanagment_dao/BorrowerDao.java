package librerymanagment_dao;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import librerymanagment_dto.Book;
import librerymanagment_dto.Borrower;

public class BorrowerDao {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pratik");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    public void allBorrower() {
        try {
            String sql = "SELECT e FROM Borrower e";
            Query query = entityManager.createQuery(sql);
            List<Borrower> list = query.getResultList();
            System.out.println(list);
        } catch (Exception e) {
            // Exception handled silently
        }
    }

    public void saveBorrower(Borrower borrower) {
        try {
            entityTransaction.begin();
            entityManager.persist(borrower);
            System.out.println("Borrower Saved: " + borrower);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    public void updateBorrower(int membership_ID, Borrower borrower) {
        try {
            Borrower borrowerdb = entityManager.find(Borrower.class, membership_ID);
            if (borrowerdb != null) {
                entityTransaction.begin();
                borrower.setMembership_ID(borrowerdb.getMembership_ID());
                entityManager.merge(borrower);
                entityTransaction.commit();
            } 
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    public void removeBook(int membership_ID) {
        try {
            Borrower borrowerdb = entityManager.find(Borrower.class, membership_ID);
            if (borrowerdb != null) {
                entityTransaction.begin();
                entityManager.remove(borrowerdb);
                entityTransaction.commit();
            }
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }
    }

    public Borrower findBorrower(int membershipId) {
        try {
            return entityManager.find(Borrower.class, membershipId);
        } catch (Exception e) {
            return null;
        }
    }
}
