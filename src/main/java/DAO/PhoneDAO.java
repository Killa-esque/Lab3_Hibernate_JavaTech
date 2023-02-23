package DAO;

import Pojo.Phone;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAO implements PhoneDAOInterface {
    private SessionFactory sessionFactory;
    @Override
    public boolean add(Phone newPhone) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(newPhone);
            transaction.commit();
            System.out.println("Adding successfully");
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public Phone get(int id) {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Phone phone = session.get(Phone.class, id);
            transaction.commit();
            return phone;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Phone> getAll() {
        List<Phone> list = new ArrayList<>();
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Phone";
            list = session.createQuery(hql).list();
        } catch (HibernateError e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public boolean remove(int id) {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean deleted = false;
        try {
            transaction = session.beginTransaction();
            Phone phone = session.get(Phone.class, id);
            if (phone != null) {
                session.delete(phone);
                transaction.commit();
                deleted = true;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return deleted;
    }

    @Override
    public boolean remove(Phone p) {
        boolean removed = false;
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(p);
            transaction.commit();
            removed = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return removed;
    }

    @Override
    public boolean update(Phone p) {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean isUpdated = false;
        try {
            transaction = session.beginTransaction();
            session.update(p);
            transaction.commit();
            isUpdated = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return isUpdated;
    }

    public Phone getPhoneWithHighestPrice() {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query<Phone> query = session.createQuery("FROM Phone ORDER BY price DESC", Phone.class);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            session.close();
        }
    }

    public List<Phone> getPhonesSortedByCountryAndPrice() {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query<Phone> query = session.createQuery("FROM Phone ORDER BY country ASC, price DESC", Phone.class);
            return query.getResultList();
        } finally {
            session.close();
        }
    }


    public boolean hasPhonePricedAbove50Million() {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query<Phone> query = session.createQuery("FROM Phone WHERE price > 50000000", Phone.class);
            query.setMaxResults(1);
            return query.getResultList().size() > 0;
        } finally {
            session.close();
        }
    }

    public Phone getFirstPinkPhonePricedOver15Million() {
        sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query<Phone> query = session.createQuery("FROM Phone WHERE color = 'Pink' or color = 'pink' AND price > 15000000", Phone.class);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            session.close();
        }
    }
}
