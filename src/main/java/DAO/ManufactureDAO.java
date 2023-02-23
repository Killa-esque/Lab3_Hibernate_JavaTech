package DAO;

import Pojo.Manufacture;
import org.hibernate.SessionFactory;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

public class ManufactureDAO implements ManufactureDAOInterface {
    private SessionFactory sessionFactory;

    @Override
    public boolean add(Manufacture newManu) {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean added = false;
        try {
            transaction = session.beginTransaction();
            session.save(newManu);
            transaction.commit();
            added = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return added;
    }

    @Override
    public Manufacture get(int id) {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            return session.get(Manufacture.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Manufacture> getAll() {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM Manufacture").getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean remove(int id) {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Manufacture manu = session.get(Manufacture.class, id);
            if (manu != null) {
                session.delete(manu);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean remove(Manufacture manu) {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(manu);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Manufacture manu) {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(manu);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public boolean checkAllManufacturersHaveMoreThan100Employees() {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT COUNT(*) FROM Manufacture WHERE employee <= 100";
            Query query = session.createQuery(hql);
            Long count = (Long) query.uniqueResult();
            return count == 0;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    public int sumAllEmployeesOfManufactures() {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT SUM(numOfEmployees) FROM Manufacture";
            Query query = session.createQuery(hql);
            Long sum = (Long) query.uniqueResult();
            return sum == null ? 0 : sum.intValue();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    public Manufacture getLastManufactureBasedInUS() {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Manufacture WHERE location = :location ORDER BY id DESC";
            Query query = session.createQuery(hql);
            query.setParameter("country", "US");
            query.setMaxResults(1);
            return (Manufacture) query.uniqueResult();
        } catch (UnsupportedOperationException e) {
            throw e;
        } finally {
            session.close();
        }
    }
}

