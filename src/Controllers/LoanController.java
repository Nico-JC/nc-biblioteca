package Controllers;

import Data.LoanDAO;
import Models.Loan;

public class LoanController {

    private LoanDAO loan;

    public LoanController(){
        loan = new LoanDAO();
    }

    public boolean crateLoan(int userId, int bookId){
        try {
            return loan.createLoan(userId, bookId);
        }catch (Exception e){
            System.out.println("Ocurrio un erro al crear el prestamo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean editLoanStatus(int id, boolean status){
        if (id <= 0) {
            System.out.println("El ID del prestamo no es válido.");
            return false;
        }
        try {
            return loan.editLoanStatus(id, status);
        }catch (Exception e){
            System.out.println("Ocurrio un erro al crear el prestamo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public <T> Loan getLoan(String columna, T value) {
        return loan.getLoan(columna, value);
    }

    public Object[][] getLoanHistory(int option){
        return loan.getLoanHistory(option);
    }

    public Object[] generateReport(){
        return loan.generateReport();
    }


}
