package ua.rd.webbanking.dao;

import ua.rd.webbanking.entities.Payment;

import java.util.List;

public interface PaymentDAO {
    void insert(Payment payment);
    List<Payment> readUsingCardID (long cardID);
    Payment read(int paymentID);
    void update (Payment payment);
    void delete (int paymentID);
}
