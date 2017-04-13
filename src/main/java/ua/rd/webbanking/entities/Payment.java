package ua.rd.webbanking.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "payments_id", unique = true, nullable = false)
    private int paymentID;
    @Column(name = "payments_type", nullable = false, length = 45)
    private String paymentDestination;
    @Column(name = "payments_date", nullable = false)
    private java.sql.Date paymentDate;
    @Column(name = "payments_amount", nullable = false)
    private BigDecimal paymentAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private CreditCard paymentCreditCard;

    public Payment() {
    }

    public Payment(PaymentBuilder paymentBuilder) {
        this.paymentID = paymentBuilder.paymentID;
        this.paymentCreditCard = paymentBuilder.paymentCreditCard;
        this.paymentAmount = paymentBuilder.paymentAmount;
        this.paymentDestination = paymentBuilder.paymentDestination;
        setPaymentDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (paymentID != payment.paymentID) return false;
        if (paymentCreditCard != null ? !paymentCreditCard.equals(payment.paymentCreditCard) : payment.paymentCreditCard != null)
            return false;
        if (paymentDestination != null ? !paymentDestination.equals(payment.paymentDestination) : payment.paymentDestination != null)
            return false;
        if (paymentDate != null ? !paymentDate.equals(payment.paymentDate) : payment.paymentDate != null)
            return false;
        return paymentAmount != null ? paymentAmount.equals(payment.paymentAmount) : payment.paymentAmount == null;
    }

    @Override
    public int hashCode() {
        int result = paymentID;
        result = 31 * result + (paymentCreditCard != null ? paymentCreditCard.hashCode() : 0);
        result = 31 * result + (paymentDestination != null ? paymentDestination.hashCode() : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        result = 31 * result + (paymentAmount != null ? paymentAmount.hashCode() : 0);
        return result;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public void setPaymentDestination(String paymentType) {
        this.paymentDestination = paymentType;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentDate() {
        java.util.Date CurrentDate = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {

            java.util.Date utilDate = formatter.parse(formatter.format(CurrentDate));
            java.sql.Date mySQLDate = new java.sql.Date(utilDate.getTime());

            paymentDate = mySQLDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setPaymentCreditCard(CreditCard paymentCreditCard) {
        this.paymentCreditCard = paymentCreditCard;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public String getPaymentDestination() {
        return paymentDestination;
    }

    public java.sql.Date getPaymentDate() {
        return paymentDate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public CreditCard getPaymentCreditCard() {
        return paymentCreditCard;
    }

    public static class PaymentBuilder {
        private int paymentID;
        private CreditCard paymentCreditCard;
        private String paymentDestination;
        private BigDecimal paymentAmount;

        public PaymentBuilder paymentID(int paymentID) {
            this.paymentID = paymentID;

            return this;
        }

        public PaymentBuilder paymentCreditCard(CreditCard paymentCreditCard) {
            this.paymentCreditCard = paymentCreditCard;

            return this;
        }

        public PaymentBuilder paymentAmount(BigDecimal paymentAmount) {
            this.paymentAmount = paymentAmount;

            return this;
        }

        public PaymentBuilder paymentDestination(String paymentDestination) {
            this.paymentDestination = paymentDestination;

            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
