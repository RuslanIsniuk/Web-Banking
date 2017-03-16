package ua.rd.webbanking.dao;

import ua.rd.webbanking.entities.Account;

import java.util.List;

/**
 * Created by Руслан on 22.12.2016.
 */
public interface AccountDAO {
    Account read(int accountID);
    List<Account> readUsingClientID(int clientID);
    List<Account> readAll();
    void create(Account account);
    void update(Account account);
    void delete(int accountID);
}
