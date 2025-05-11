package Exceptions;

public class InvalidLoanException extends Exception{
    public InvalidLoanException(String mensaje){
        super(mensaje);
    }

}
