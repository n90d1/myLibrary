package com.example.nguye.mylibr.Borrower;

import java.util.Date;

public class borrower {
    String borrowerId;
    String picBorrower;
    String nameBorrower;
    String birthdayBo;
    String phoneBo;
    String addressBo;
    String emailBo;

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getPicBorrower() {
        return picBorrower;
    }

    public void setPicBorrower(String picBorrower) {
        this.picBorrower = picBorrower;
    }

    public String getNameBorrower() {
        return nameBorrower;
    }

    public void setNameBorrower(String nameBorrower) {
        this.nameBorrower = nameBorrower;
    }

    public String getBirthdayBo() {
        return birthdayBo;
    }

    public void setBirthdayBo(String birthdayBo) {
        this.birthdayBo = birthdayBo;
    }

    public String getPhoneBo() {
        return phoneBo;
    }

    public void setPhoneBo(String phoneBo) {
        this.phoneBo = phoneBo;
    }

    public String getAddressBo() {
        return addressBo;
    }

    public void setAddressBo(String addressBo) {
        this.addressBo = addressBo;
    }

    public String getEmailBo() {
        return emailBo;
    }

    public void setEmailBo(String emailBo) {
        this.emailBo = emailBo;
    }

    public borrower(String borrowerId, String picBorrower, String nameBorrower, String birthdayBo, String phoneBo, String addressBo, String emailBo) {

        this.borrowerId = borrowerId;
        this.picBorrower = picBorrower;
        this.nameBorrower = nameBorrower;
        this.birthdayBo = birthdayBo;
        this.phoneBo = phoneBo;
        this.addressBo = addressBo;
        this.emailBo = emailBo;
    }

    public borrower() {

    }
}
