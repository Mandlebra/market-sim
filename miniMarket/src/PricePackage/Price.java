package PricePackage;

import java.util.Objects;

public class Price implements Comparable<Price>{
    private final int price;
    protected Price(int newPrice)
    {
        price = newPrice;
    }
    public int getPrice()
    {
        return price;
    }

    public boolean isNegative()
    {
        return (price < 0);
    }
    public Price add(Price p) throws InvalidPriceOperationException {
        if (p == null) {
            throw new InvalidPriceOperationException("Price is null");
        }
        return PriceFactory.makePrice(price + p.getPrice());
    }
    public Price subtract(Price p) throws InvalidPriceOperationException {
        if (p == null) {
            throw new InvalidPriceOperationException("Price is null");
        }
        return PriceFactory.makePrice(price - p.getPrice());
    }
    public Price multiply(int x)
    {
        return PriceFactory.makePrice(price * x);
    }
    public boolean greaterOrEqual(Price p) throws InvalidPriceOperationException {
        if (p == null) {
            throw new InvalidPriceOperationException("Price is null");
        }
        return (price >= p.getPrice());
    }
    public boolean lessOrEqual(Price p) throws InvalidPriceOperationException {
        if (p == null) {
            throw new InvalidPriceOperationException("Price is null");
        }
        return (price <= p.getPrice());
    }
    public boolean greaterThan(Price p) throws InvalidPriceOperationException {
        if (p == null) {
            throw new InvalidPriceOperationException("Price is null");
        }
        return (price > p.getPrice());
    }
    public boolean lessThan(Price p) throws InvalidPriceOperationException {
        if (p == null) {
            throw new InvalidPriceOperationException("Price is null");
        }
        return (price < p.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return price == price1.price;
    }
    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
    @Override
    public int compareTo(Price p)
    {
        if (p == null)
        {
            return -1;
        }
        return (price - p.getPrice());
    }


    @Override
    public String toString() {
        String x;
        String y;

        x = String.valueOf(price/100);
        y = String.valueOf(price%100);

        if (x.equals("0"))
        {
            x = "";
        }
        if (y.length() == 1)
        {
            y = y + "0";
        }

        if (y.charAt(0) == '-')
        {

            y = y.replace("-", "");
            if (x.charAt(0) != '-')
            {
                x = "-" + x;
            }
        }

        return ("$" + (x) + "." + (y));
    }
}
