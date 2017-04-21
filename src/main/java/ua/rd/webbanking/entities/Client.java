package ua.rd.webbanking.entities;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "client_id", unique = true, nullable = false)
    private int clientID;
    @Column(name = "client_full_name", nullable = false, length = 45)
    private String clientFullName;
    @Column(name = "client_login", nullable = false, length = 25)
    private String clientLogin;
    @Column(name = "client_password", nullable = false, length = 25)
    private String clientPass;
    @Column(name = "admin_flag")
    private boolean adminFlag;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accountClient")
    private Set<Account> accountSet = null;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (clientID != client.clientID) return false;
        if (adminFlag != client.adminFlag) return false;
        if (clientFullName != null ? !clientFullName.equals(client.clientFullName) : client.clientFullName != null)
            return false;
        if (clientLogin != null ? !clientLogin.equals(client.clientLogin) : client.clientLogin != null) return false;
        return clientPass != null ? clientPass.equals(client.clientPass) : client.clientPass == null;
    }

    @Override
    public int hashCode() {
        int result = clientID;
        result = 31 * result + (clientFullName != null ? clientFullName.hashCode() : 0);
        result = 31 * result + (clientLogin != null ? clientLogin.hashCode() : 0);
        result = 31 * result + (clientPass != null ? clientPass.hashCode() : 0);
        result = 31 * result + (adminFlag ? 1 : 0);
        return result;
    }

    public void setAccountSet(Set<Account> accountSet) {
        this.accountSet = accountSet;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public void setClientPass(String clientPass) {
        this.clientPass = clientPass;
    }

    public void setAdminFlag(boolean adminFlag) {
        this.adminFlag = adminFlag;
    }

    public void setAdminFlag(int adminFlagInt) {
        if (adminFlagInt == 0) {
            adminFlag = false;
        } else {
            adminFlag = true;
        }
    }

    public boolean isAdminFlag() {
        return adminFlag;
    }


    public int getAdminFlag() {
        int adminFlagInt = 0;

        if (adminFlag) {
            adminFlagInt = 1;
        }
        return adminFlagInt;
    }


    public int getClientID() {
        return clientID;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public String getClientPass() {
        return clientPass;
    }

    public Set<Account> getAccountSet() {
        return accountSet;
    }
}
