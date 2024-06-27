import PricePackage.InvalidPriceOperationException;
import PricePackage.Price;
import PricePackage.PriceFactory;

import java.util.HashMap;
import java.util.Random;

public class TrafficSim {
    /*
-In order to trade with realistic prices, the TrafficSim class stores base price values that will be used to
generate BUY and SELL prices.

-For test purposes, we will create 5 users and 4 product books.

-The TrafficSim will generate a fixed number of orders (i.e., 100) generated from random users, for
random products with generated prices and quantities (volumes).
*/

    //double ?? can we use int?
    private static HashMap<String, Double> basePrices = new HashMap<>();

    public static void runSim() throws DataValidationException, InvalidPriceOperationException {

        //create users
        String[] strArray = new String[]{"ANN", "BOB", "CAT", "DOG", "EGG"};
        UserManager um = UserManager.getInstance();
        um.init(strArray);

        //create product books
        ProductManager pm = ProductManager.getInstance();
        pm.addProduct("WMT");
        pm.addProduct("TGT");
        pm.addProduct("AMZN");
        pm.addProduct("TSLA");

        //add symbol-price pairs
        basePrices.put("WMT", 140.98);
        basePrices.put("TGT", 174.76);
        basePrices.put("AMZN", 102.11);
        basePrices.put("TSLA", 196.81);

        for (int i = 1; i <= 40; i++)
        {
            User newUser = um.getRandomUser();
            if (Math.random() < 0.9)
            {
                String newProduct = pm.getRandomProduct();
                Random random = new Random();
                BookSide bs;
                if (random.nextBoolean())
                {
                    bs = BookSide.BUY;
                }
                else
                {
                    bs = BookSide.SELL;
                }
                int newVolume = (int) (25 + (Math.random() * 300));
                newVolume = (int) Math.round(newVolume / 5.0) * 5;

                Price orderPrice = getPrice(newProduct, bs);
                Order newOrder = new Order(newUser.getUserId(), newProduct, orderPrice, newVolume, bs);
                System.out.println(i + ") " + "ADD: " + bs + ": " + newOrder.toString());
                OrderDTO returnedOrder = pm.addOrder(newOrder);
                newUser.addOrder(returnedOrder);


            }
            else
            {
                if (newUser.hasOrderWithRemainingQty())
                {
                    OrderDTO newDTO = newUser.getOrderWithRemainingQty();
                    OrderDTO cancelledDTO = pm.cancel(newDTO);
                    if (cancelledDTO != null)
                    {
                        newUser.addOrder(cancelledDTO);
                        System.out.println(i + ") " + "CANCEL: " + cancelledDTO.side + " Order: " + cancelledDTO.id + " Cxl Qty: " + cancelledDTO.cancelledVolume);
                    }
                }
                else
                {
                    System.out.println(i + ") " + "No order to cancel");
                }
            }

        }
        System.out.println("\nProductBooks:");
        System.out.print("\n" + pm);
        System.out.println("Users:\n");
        System.out.println(um);
    }

    private static Price getPrice(String symbol, BookSide side)
    {
        double basePrice = basePrices.get(symbol);
        double priceWidth = 0.02;
        double startPoint = 0.01;
        double tickSize = 0.1;

        double gapFromBase = basePrice * priceWidth; // 5.0
        double priceVariance = gapFromBase * (Math.random());
        double priceToUse;
        double priceToTick;
        if (side == BookSide.BUY)
        {
            priceToUse = basePrice * (1 - startPoint);
            priceToUse += priceVariance;
            priceToTick = Math.round(priceToUse * (1/tickSize)) / 10.0;
        }
        else
        {
            priceToUse = basePrice * (1 + startPoint);
            priceToUse -= priceVariance;
            priceToTick = Math.round(priceToUse * (1/tickSize)) / 10.0;
        }
        return PriceFactory.makePrice((int)(priceToTick * 100));
    }


}
