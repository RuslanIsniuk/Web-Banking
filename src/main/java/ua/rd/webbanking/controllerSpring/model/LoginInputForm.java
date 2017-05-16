package ua.rd.webbanking.controllerSpring.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class LoginInputForm {
    @NotEmpty
    @Pattern(regexp = "[A-Za-z0-9_]{1,25}")
    private String userName;
    @NotEmpty
    @Pattern(regexp = "[A-Za-z0-9_]{1,25}")
    private String userPassword;
    private String errorMessage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
