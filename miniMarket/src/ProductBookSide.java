import PricePackage.Price;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductBookSide {
    private final BookSide side;
    private final HashMap<Price, ArrayList<Order>> bookEntries = new HashMap<>();

    public ProductBookSide(BookSide side) throws DataValidationException {
        if (side == null)
        {
            throw new DataValidationException("side cannot be null");
        }
        this.side = side;
    }

    public OrderDTO add(Order o) {
        if (!bookEntries.containsKey(o.getPrice())) {
            bookEntries.put(o.getPrice(), new ArrayList<>());
        }
        bookEntries.get(o.getPrice()).add(o);

        return o.makeTradableDTO();
    }

    public OrderDTO cancel(String orderId) {
        for (ArrayList<Order> orders : bookEntries.values()) {
            for (Order order : orders) {
                if (order.getId().equals(orderId)) {
                    int x = order.getRemainingVolume();
                    order.setRemainingVolume(0);
                    order.setCancelledVolume(order.getCancelledVolume() + x);
                    orders.remove(order);
                    if (orders.isEmpty()) {
                        bookEntries.remove(order.getPrice());
                    }
                    return order.makeTradableDTO();
                }
            }
        }
        return null;
    }

    public Price topOfBookPrice() {
        if (side == BookSide.BUY)
        {
            Price maxPrice = null;

            for (ArrayList<Order> orders : bookEntries.values())
            {
                for (Order order : orders) {
                    if (maxPrice == null || order.getPrice().compareTo(maxPrice) > 0)
                    {
                        maxPrice = order.getPrice();
                    }
                }
            }
            //System.out.println("maxPrice = " + maxPrice);
            return maxPrice;
        }
        else
        {
            Price minPrice = null;

            for (ArrayList<Order> orders : bookEntries.values())
            {
                for (Order order : orders) {
                    if (minPrice == null || order.getPrice().compareTo(minPrice) < 0)
                    {
                        minPrice = order.getPrice();
                    }
                }
            }
            //System.out.println("minPrice = " + minPrice);
            return minPrice;
        }
    }

    public int topOfBookVolume() {
        ArrayList<Order> orders = bookEntries.get(topOfBookPrice());
        int x = 0;
        for (Order o : orders) {
            x += o.getRemainingVolume();
        }
        return x;
    }

    public void tradeOut(Price price, int vol) {

        int remainingVol = vol;
        ArrayList<Order> orders = bookEntries.get(price);


        while (remainingVol > 0)
        {
            if (orders.get(0).getRemainingVolume() <= remainingVol)
            {
                int x = orders.get(0).getRemainingVolume();

                orders.get(0).setFilledVolume(x + orders.get(0).getFilledVolume());
                orders.get(0).setRemainingVolume(0);
                remainingVol -= x;
                System.out.println("FILL: (" + side + " " + x + ") " + orders.get(0));
                orders.remove(0);

            }
            else
            {
                orders.get(0).setFilledVolume(orders.get(0).getFilledVolume() + remainingVol);
                orders.get(0).setRemainingVolume(orders.get(0).getRemainingVolume() - remainingVol);
                System.out.println("PARTIAL FILL: (" + side + " " + remainingVol + ") " + orders.get(0));
                remainingVol = 0;
            }

        }
        //System.out.println(" orders : " + orders.toString());
        if (orders.isEmpty())
        {
            bookEntries.remove(price);
        }

        /*
        while (remainingVol > 0 && orders != null)
        {
            if (orders.isEmpty()){
                break;
            }
            if (orders.get(0).getRemainingVolume() <= remainingVol)
            {
                int x = orders.get(0).getRemainingVolume();
                orders.get(0).setFilledVolume(x + orders.get(0).getFilledVolume());
                orders.get(0).setRemainingVolume(0);
                remainingVol -= x;
                System.out.println("FILL: (" + side + " " + x + ")" + orders.get(0));
                orders.remove(0);
            }
            else
            {
                orders.get(0).setFilledVolume(orders.get(0).getFilledVolume() + remainingVol);
                orders.get(0).setRemainingVolume(orders.get(0).getRemainingVolume() - remainingVol);
                System.out.println("PARTIAL FILL: (" + side + " " + remainingVol + ")" + orders.get(0));
                remainingVol = 0;
            }
        }
        if (orders == null || orders.isEmpty())
        {
            bookEntries.remove(price);
        }

         */
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Side: %s\n", side));
        for (HashMap.Entry<Price, ArrayList<Order>> entry : bookEntries.entrySet()) {
            builder.append(String.format("\t\tPrice: %s\n", entry.getKey()));
            for (Order order : entry.getValue()) {
                builder.append("\t\t\t").append(order.toString()).append("\n");
            }
        }
        return builder.toString();
    }
}
