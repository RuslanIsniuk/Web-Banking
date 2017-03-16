package ua.rd.webbanking.entities;

public class Client {
    private int clientID;
    private String clientFullName;
    private String clientLogin;
    private String clientPass;
    private boolean adminFlag;


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

    public void setClientID (int clientID){
        this.clientID = clientID;
    }

    public void setClientFullName(String clientFullName){
        this.clientFullName = clientFullName;
    }

    public void setClientLogin(String clientLogin){
        this.clientLogin = clientLogin;
    }

    public void setClientPass(String clientPass){
        this.clientPass = clientPass;
    }

    public void setAdminFlag(boolean adminFlag) { this.adminFlag = adminFlag; }

    public void setAdminFlag(int adminFlagInt){
        if(adminFlagInt == 0){
            adminFlag = false;
        }
        else{
            adminFlag = true;
        }
    }

    public boolean isAdminFlag(){ return adminFlag;}

    public int getAdminFlagInt(){
        int adminFlagInt = 0;

        if(adminFlag){
            adminFlagInt = 1;
        }
        return adminFlagInt;
    }

    public int getClientID(){
        return clientID;
    }

    public String getClientFullName(){
        return clientFullName;
    }

    public String getClientLogin(){
        return clientLogin;
    }

    public String getClientPass(){
        return clientPass;
    }

}
