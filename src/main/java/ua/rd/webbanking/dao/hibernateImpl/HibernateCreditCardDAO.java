package ua.rd.webbanking.dao.hibernateImpl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.query.Query;
import ua.rd.webbanking.DBdata.HibernateUtil;
import ua.rd.webbanking.dao.CreditCardDAO;
import ua.rd.webbanking.entities.CreditCard;

import java.util.ArrayList;
import java.util.List;

public class HibernateCreditCardDAO implements CreditCardDAO {
    private static final Logger logger = Logger.getLogger(HibernateClientDAO.class);
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    public List<CreditCard> readUsingAccountID(int accountID) {
        List<CreditCard> creditCardList = null;
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            String sqlQuery = "FROM " + CreditCard.class.getName() + " cc  WHERE cc.cardAccount.accountID = :ID";
            Query<CreditCard> query = (Query<CreditCard>) session.createQuery(sqlQuery);
            query.setParameter("ID", accountID);
            creditCardList = query.getResultList();
            session.getTransaction().commit();
        } catch (SQLGrammarException sge) {
            logger.error(sge);
        } catch (RuntimeException e) {
            logger.error(e);
        } finally {
            session.close();
        }

        return creditCardList;
    }

    public CreditCard read(long cardID) {
        CreditCard creditCard = null;
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            creditCard = session.get(CreditCard.class, Long.valueOf(cardID));
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
        return creditCard;
    }

    public List<CreditCard> readAll() {
        List<CreditCard> creditCardList = new ArrayList<>();
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            String sqlQuery = "from " + CreditCard.class.getName();
            Query<CreditCard> query = (Query<CreditCard>) session.createQuery(sqlQuery);
            creditCardList = query.getResultList();
        } catch (SQLGrammarException sge) {
            logger.error(sge);
        } catch (RuntimeException e) {
            logger.error(e);
        } finally {
            session.close();
        }
        return creditCardList;
    }

    public long insert(CreditCard creditCard) {
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            session.save(creditCard);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            logger.error(e);
        } finally {
            session.close();
        }
        return creditCard.getCardID();
    }

    public void update(CreditCard creditCard) {
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.beginTransaction();
            session.update(creditCard);
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

    public void delete(long cardID) {
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.beginTransaction();
            CreditCard creditCard = session.get(CreditCard.class, Long.valueOf(cardID));
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
