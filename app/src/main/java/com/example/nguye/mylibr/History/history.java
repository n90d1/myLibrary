package com.example.nguye.mylibr.History;

public class history {
    String idHistory;
    String borrowerId;
    String bookId;
    String dateBorrow;

    public String getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(String idHistory) {
        this.idHistory = idHistory;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(String dateBorrow) {
        this.dateBorrow = dateBorrow;
    }

    public history(String idHistory, String borrowerId, String bookId, String dateBorrow) {

        this.idHistory = idHistory;
        this.borrowerId = borrowerId;
        this.bookId = bookId;
        this.dateBorrow = dateBorrow;
    }

    public history() {

    }
}
