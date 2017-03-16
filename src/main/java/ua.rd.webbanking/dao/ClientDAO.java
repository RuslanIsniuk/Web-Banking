package electronicPaymentSystem.dao;

import electronicPaymentSystem.entities.Client;

import java.util.List;

/**
 * Created by lenovo on 11.12.2016.
 */
public interface ClientDAO{
    Client read(String clientLogin, String clientPass);
    Client read(int clientID);
    int create (Client client);
    List<Client> readAll();
    void update(Client client);
    void delete(int clientID);
    }
