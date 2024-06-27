package PricePackage;

public class InvalidPriceOperationException extends Exception {
    public InvalidPriceOperationException(String errorMessage) {
        super(errorMessage);
    }
}