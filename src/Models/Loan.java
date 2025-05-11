package Models;

import java.sql.Date;

public class Loan {
    private int id_loan;
    private int id_user;
    private int id_bookList;
    private Date inicioDate;
    private boolean status;

    public Loan(int id_loan, int id_user, int id_bookList, Date inicioDate, boolean status) {
        this.id_loan = id_loan;
        this.id_user = id_user;
        this.id_bookList = id_bookList;
        this.inicioDate = inicioDate;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}