package ua.rd.webbanking.dao;

import ua.rd.webbanking.entities.Client;

import java.util.List;

public interface ClientDAO{
    Client read(String clientLogin, String clientPass);
    Client read(int clientID);
    int create (Client client);
    List<Client> readAll();
    void update(Client client);
    void delete(int clientID);
    }
