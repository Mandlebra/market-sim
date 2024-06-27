package PricePackage;

import java.util.HashMap;

public abstract class PriceFactory {


    private static HashMap<Integer, Price> prices = new HashMap<>();

    public static Price makePrice(String newPrice) throws InvalidPriceOperationException {
        int x = parseString(newPrice);
        if (prices.containsKey(x)) {
            return prices.get(x);
        } else {
            Price price = new Price(x);
            prices.put(x, price);
            return price;
        }
    }
    public static Price makePrice(int newPrice) {
        if (prices.containsKey(newPrice)) {
            return prices.get(newPrice);
        } else {
            Price price = new Price(newPrice);
            prices.put(newPrice, price);
            return price;
        }
    }

    private static int parseString (String newPrice) throws InvalidPriceOperationException {
        StringBuilder s = new StringBuilder();
        boolean isPeriod = false;
        boolean isNegative = false;
        boolean isMoney = false;
        char[] charArray = new char[10];
        int charCounter = 0;
        int x;

        for (int i = 0; i < newPrice.length(); i++)
        {
            char z = newPrice.charAt(i);

            if (Character.isDigit(z))
            {
                charArray[charCounter] = z;
                charCounter++;
            }
            else if (z != '$' && z != '.' && z != '-')
            {
                throw new InvalidPriceOperationException("Unexpected symbol " + z + " in price");
            }

            if (z == '$')
            {
                if ((i != 0)) {
                    throw new InvalidPriceOperationException("Wrong location for $ symbol");
                }
                isMoney = true;

            }

            if (z == '.')
            {
                if (isPeriod || i != newPrice.length() - 3)
                {
                    throw new InvalidPriceOperationException("Period character error");
                }
                isPeriod = true;
            }
            if (z == '-')
            {
                if (isNegative || (i != 0 && i != 1) || (i == 1 && !isMoney))
                {
                    throw new InvalidPriceOperationException("Minus character error");
                }
                isNegative = true;
            }
        }

        charCounter = 1;
        s = new StringBuilder(Character.toString(charArray[0]));
        while (charArray[charCounter] != '\u0000') //check if default char array value
        {
            s.append(charArray[charCounter]);
            charCounter++;

        }
        x = Integer.parseInt(s.toString());

        if (!isPeriod)
        {
            x = x * 100;
        }
        if (isNegative)
        {
            x = x * -1;
        }

        return x;
    }
}