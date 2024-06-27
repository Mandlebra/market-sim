// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import PricePackage.InvalidPriceOperationException;
import PricePackage.Price;
import PricePackage.PriceFactory;

import javax.xml.crypto.Data;

//...
public class Main {
    public static void main(String[] args) {
        try {
            TrafficSim.runSim();
        }
        catch (InvalidPriceOperationException | DataValidationException e)
        {
            System.out.println(e);
        }
    }
}