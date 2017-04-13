package ua.rd.webbanking.dao.hibernateImpl;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.query.Query;
import ua.rd.webbanking.DBdata.HibernateUtil;
import ua.rd.webbanking.dao.PaymentDAO;
import ua.rd.webbanking.entities.Payment;

import java.util.List;

public class HibernatePaymentDAO implements PaymentDAO {
    private static final Logger logger = Logger.getLogger(HibernateClientDAO.class);
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    public List<Payment> readUsingCardID(long cardID) {
        List<Payment> paymentList = null;
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            String sqlQuery = "FROM " + Payment.class.getName() + " p  WHERE p.paymentCreditCard.cardID = :ID";
            Query<Payment> query = (Query<Payment>) session.createQuery(sqlQuery);
            query.setParameter("ID", cardID);
            paymentList = query.getResultList();
            session.getTransaction().commit();
        } catch (SQLGrammarException sge) {
            logger.error(sge);
        } catch (RuntimeException e) {
            logger.error(e);
        } finally {
            session.close();
        }

        return paymentList;
    }

    public Payment read(int paymentID) {
        Payment payment = null;
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            payment = session.get(Payment.class, Integer.valueOf(paymentID));
            session.getTransaction().commit();
        } catch (SQLGrammarException sge) {
            logger.error(sge);
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            logger.error(e);
        } finally {
            session.close();
        }
        return payment;
    }

    public void update(Payment payment) {
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.beginTransaction();
            session.update(payment);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            logger.error(e);
        } finally {
            session.close();
        }
    }

    public void insert(Payment payment) {
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            session.save(payment);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            logger.error(e);
        } finally {
            session.close();
        }
    }

    public void delete(int paymentID) {
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.beginTransaction();
            Payment creditCard = session.get(Payment.class, Integer.valueOf(paymentID));
            session.delete(creditCard);
            session.getTransaction().commit();
        } catch (SQLGrammarException sge) {
            logger.error(sge);
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            logger.error(e);
        } finally {
            session.close();
        }
    }
}
