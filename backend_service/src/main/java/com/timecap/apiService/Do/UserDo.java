package com.timecap.apiService.Do;

public class UserDo {
    private String userGmail;
    private Boolean isPurchaseda;
    private Boolean isPurchasedb;

    public String getUserGmail() {
        return userGmail;
    }

    public void setUserGmail(String userGmail) {
        this.userGmail = userGmail;
    }

    public Boolean getPurchaseda() {
        return isPurchaseda;
    }

    public void setPurchaseda(Boolean isA) {
        isPurchaseda = isA;
    }

    public Boolean getPurchasedb() {
        return isPurchasedb;
    }

    public void setPurchasedb(Boolean isB) {
        isPurchasedb = isB;
    }

    @Override
    public String toString() {
        return "UserDo{" +
                "userGmail='" + userGmail + '\'' +
                ", isPurchaseda='" + isPurchaseda + '\'' +
                ", isPurchasedb=" + isPurchasedb +
                '}';
    }
}
