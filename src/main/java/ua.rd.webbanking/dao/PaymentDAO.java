package electronicPaymentSystem.dao;

import electronicPaymentSystem.entities.Payment;

import java.util.List;

/**
 * Created by Руслан on 28.12.2016.
 */
public interface PaymentDAO {
    void create (Payment payment);
    List<Payment> readUsingCardID (long cardID);
    Payment read(int paymentID);
    void update (Payment payment);
    void delete (int paymentID);
}
