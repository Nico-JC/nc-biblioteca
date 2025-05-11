package Exceptions;

public class PriceCapBreachException extends Exception{
    public PriceCapBreachException(String message){
        super(message);
    }
}
