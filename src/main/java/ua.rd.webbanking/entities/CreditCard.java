package electronicPaymentSystem.entities;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Date;
import java.util.Locale;

/**
 * Created by lenovo on 12.12.2016.
 */
public class CreditCard{
    private long cardID;
    private String cardPIN;
    private String cardStatus;
    private java.sql.Date cardValidDate;
    private Account cardAccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditCard that = (CreditCard) o;

        if (cardID != that.cardID) return false;
        if (cardPIN != null ? !cardPIN.equals(that.cardPIN) : that.cardPIN != null) return false;
        if (cardStatus != null ? !cardStatus.equals(that.cardStatus) : that.cardStatus != null) return false;
        if (cardValidDate != null ? !cardValidDate.equals(that.cardValidDate) : that.cardValidDate != null)
            return false;
        return cardAccount != null ? cardAccount.equals(that.cardAccount) : that.cardAccount == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (cardID ^ (cardID >>> 32));
        result = 31 * result + (cardPIN != null ? cardPIN.hashCode() : 0);
        result = 31 * result + (cardStatus != null ? cardStatus.hashCode() : 0);
        result = 31 * result + (cardValidDate != null ? cardValidDate.hashCode() : 0);
        result = 31 * result + (cardAccount != null ? cardAccount.hashCode() : 0);
        return result;
    }

    public void setCardID(long cardID){
        this.cardID = cardID;
    }

    public void setCardStatus(String cardStatus){
        this.cardStatus = cardStatus;
    }

    public void setCardValidDate(String cardValidDateString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        try {

            java.util.Date utilDate = formatter.parse(cardValidDateString);
            java.sql.Date mySQLDate= new java.sql.Date(utilDate.getTime());

            cardValidDate = mySQLDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setCardValidDate(Date cardValidDate){
        this.cardValidDate = cardValidDate;
    }

    public void setCardPIN (String cardPIN){ this.cardPIN = cardPIN; }

    public void setCardAccount (Account cardAccount){
        this.cardAccount = cardAccount;
    }


    public static String cardNumberToString(long cardID){
        String TempStr = Long.toString(cardID);
        return TempStr.substring(0, 4) + " " + TempStr.substring(4, 8) + " " + TempStr.substring(8, 12) + " " + TempStr.substring(12);
    }

    public String getCardPIN(){return  cardPIN; }

    public long getCardID(){
        return cardID;
    }

    public String getCardStatus(){
        return cardStatus;
    }

    public Date getCardValidDate(){
        return cardValidDate;
    }

    public Account getCardAccount(){
        return cardAccount;
    }

}
