package ua.rd.webbanking.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "account_id", unique = true, nullable = false)
    private int accountID;
    @Column(name = "balance", unique = true, nullable = false)
    private BigDecimal accountBalance;
    @Column(name = "status", unique = true, nullable = false, length = 20)
    private String accountStatus;
    @Column(name = "data_open", unique = true, nullable = false)
    private java.sql.Date accountDateOpen;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client accountClient;
//    private Set<CreditCard> creditCardSet = null;
//    public Set<CreditCard> getCreditCardSet() {
//        return creditCardSet;
//    }

//    public void setCreditCardSet(Set<CreditCard> creditCardSet) {
//        this.creditCardSet = creditCardSet;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountID != account.accountID) return false;
        if (accountBalance != null ? !accountBalance.equals(account.accountBalance) : account.accountBalance != null)
            return false;
        if (accountStatus != null ? !accountStatus.equals(account.accountStatus) : account.accountStatus != null)
            return false;
        if (accountDateOpen != null ? !accountDateOpen.equals(account.accountDateOpen) : account.accountDateOpen != null)
            return false;
        return accountClient != null ? accountClient.equals(account.accountClient) : account.accountClient == null;
    }

    @Override
    public int hashCode() {
        int result = accountID;
        result = 31 * result + (accountBalance != null ? accountBalance.hashCode() : 0);
        result = 31 * result + (accountStatus != null ? accountStatus.hashCode() : 0);
        result = 31 * result + (accountDateOpen != null ? accountDateOpen.hashCode() : 0);
        result = 31 * result + (accountClient != null ? accountClient.hashCode() : 0);
        return result;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setAccountDateOpen(Date accountDataOpen) {
        this.accountDateOpen = accountDataOpen;
    }

    public void setAccountClient(Client accountClient) {
        this.accountClient = accountClient;
    }

    public void setAccountDateOpen(String accountDateOpenString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {

            java.util.Date utilDate = formatter.parse(accountDateOpenString);
            java.sql.Date mySQLDate = new java.sql.Date(utilDate.getTime());

            accountDateOpen = mySQLDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getAccountID() {
        return accountID;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public Date getAccountDateOpen() {
        return accountDateOpen;
    }

    public Client getAccountClient() {
        return accountClient;
    }

}
