import PricePackage.Price;

import java.util.HashMap;

public class User {
    private String userId;
    private HashMap<String, OrderDTO> orders = new HashMap<>();

    public User(String newId) throws DataValidationException
    {
        setUserId(newId);
    }

    private void setUserId(String newId)throws DataValidationException
    {
        if (newId.length() != 3)
        {
            throw new DataValidationException("Invalid User length");
        }
        for (int i = 0; i < 3; i++)
        {
            if (!Character.isAlphabetic(newId.charAt(i)) || !Character.isUpperCase(newId.charAt(i)))
            {
                throw new DataValidationException("Invalid user name");
            }
        }
        userId = newId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void addOrder(OrderDTO o) throws DataValidationException
    {
        if (o == null)
        {
            throw new DataValidationException("Order cannot be null");
        }

        orders.put(o.id, o);
    }

    public boolean hasOrderWithRemainingQty()
    {
        for (OrderDTO o : orders.values())
        {
            if (o.remainingVolume > 0)
            {
                return true;
            }
        }
        return false;
    }

    public OrderDTO getOrderWithRemainingQty()
    {
        for (OrderDTO o : orders.values())
        {
            if (o.remainingVolume > 0)
            {
                return o;
            }
        }
        return null;
    }

    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append(String.format("User Id: %s", userId));
        for (OrderDTO o : orders.values())
        {
            str.append(String.format("\n\tProduct: %s, Price: %s, OriginalVolume: %d, RemainingVolume: %d," +
                    " CancelledVolume: %d, FilledVolume: %d, User: %s, Side: %s, Id: %s", o.product, o.price, o.originalVolume,
                    o.remainingVolume, o.cancelledVolume, o.filledVolume, o.user, o.side, o.id));
        }

        return str.toString();
    }


}
