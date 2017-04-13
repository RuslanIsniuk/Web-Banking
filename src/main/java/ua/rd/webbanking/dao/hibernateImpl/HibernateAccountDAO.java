package ua.rd.webbanking.dao.hibernateImpl;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.query.Query;
import ua.rd.webbanking.DBdata.HibernateUtil;
import ua.rd.webbanking.dao.AccountDAO;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.entities.Account;
import ua.rd.webbanking.entities.Client;

import java.util.ArrayList;
import java.util.List;

public class HibernateAccountDAO implements AccountDAO{
    private static final Logger logger = Logger.getLogger(HibernateClientDAO.class);
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    public Account read(int accountID){
        Account account = null;
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            account = session.get(Account.class, Integer.valueOf(accountID));
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
        return account;
    }

    public List<Account> readUsingClientID(int clientID){
        List<Account> accountList = null;
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            String sqlQuery = "FROM " + Account.class.getName() + " a  WHERE a.accountClient.clientID = :ID";
            Query<Account> query = (Query<Account>) session.createQuery(sqlQuery);
            query.setParameter("ID", clientID);
            accountList = query.getResultList();
            session.getTransaction().commit();
        } catch (SQLGrammarException sge) {
            logger.error(sge);
        } catch (RuntimeException e) {
            logger.error(e);
        } finally {
            session.close();
        }

        return accountList;
    }

    public List<Account> readAll(){
        List<Account> accountList = new ArrayList<>();
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            String sqlQuery = "from " + Account.class.getName();
            Query<Account> query = (Query<Account>) session.createQuery(sqlQuery);
            accountList = query.getResultList();
        } catch (SQLGrammarException sge) {
            logger.error(sge);
        } catch (RuntimeException e) {
            logger.error(e);
        } finally {
            session.close();
        }
        return accountList;
    }

    public void insert(Account account){
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            session.save(account);
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

    public void update(Account account){
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.beginTransaction();
            session.update(account);
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

    public void delete(int accountID){
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.beginTransaction();
            Account account = session.get(Account.class, Integer.valueOf(accountID));
            session.delete(account);
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
