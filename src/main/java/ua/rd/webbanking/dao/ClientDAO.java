package ua.rd.webbanking.dao;

import ua.rd.webbanking.entities.Client;

import java.util.List;

public interface ClientDAO {
    Client readByLogin(String clientLogin, String clientPass);

    Client read(int clientID);

    int insert(Client client);

    List<Client> readAll();

    void update(Client client);

    void delete(int clientID);
}
