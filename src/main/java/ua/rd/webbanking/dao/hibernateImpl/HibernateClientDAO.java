package ua.rd.webbanking.dao.hibernateImpl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.query.Query;
import ua.rd.webbanking.DBdata.HibernateUtil;
import ua.rd.webbanking.dao.ClientDAO;
import ua.rd.webbanking.entities.Client;

import java.util.ArrayList;
import java.util.List;


public class HibernateClientDAO implements ClientDAO {
    private static final Logger logger = Logger.getLogger(HibernateClientDAO.class);
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    public Client readByLogin(String clientLogin, String clientPass) {
        Transaction trns = null;
        Client client = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            String sqlQuery = "FROM " + Client.class.getName() + " c  WHERE c.clientLogin = :Login and c.clientPass = :Pass";
            Query<Client> query = (Query<Client>) session.createQuery(sqlQuery);
            query.setParameter("Login", clientLogin);
            query.setParameter("Pass", clientPass);
            client = query.getSingleResult();
            session.getTransaction().commit();
        } catch (SQLGrammarException sge) {
            logger.error(sge);
        } catch (RuntimeException e) {
            logger.error(e);
        } finally {
            session.close();
        }
        return client;
    }

    public Client read(int clientID) {
        Client client = null;
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            client = session.get(Client.class, Integer.valueOf(clientID));
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
        return client;
    }

    public int insert(Client client) {
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            session.save(client);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            logger.error(e);
        } finally {
            session.close();
        }
        return client.getClientID();
    }

    public List<Client> readAll() {
        List<Client> clientList = new ArrayList<>();
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.getTransaction();
            trns.begin();
            String sqlQuery = "from " + Client.class.getName();
            Query<Client> query = (Query<Client>) session.createQuery(sqlQuery);
            clientList = query.getResultList();
        } catch (SQLGrammarException sge) {
            logger.error(sge);
        } catch (RuntimeException e) {
            logger.error(e);
        } finally {
            session.close();
        }
        return clientList;
    }

    public void update(Client client) {
        Transaction trns = null;
        Session session = factory.getCurrentSession();

        try {
            trns = session.beginTransaction();
            session.update(client);
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

    public void delete(int clientID) {
        Transaction trns = null;
        Session session = factory.getCurrentSession();
        
        try {
            trns = session.beginTransaction();
            Client client = session.get(Client.class, Integer.valueOf(clientID));
            session.delete(client);
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
