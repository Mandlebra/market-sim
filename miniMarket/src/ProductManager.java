import PricePackage.InvalidPriceOperationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ProductManager {
    private static ProductManager singleton = null;
    private static HashMap<String, ProductBook> books = new HashMap<>();
    private ProductManager()
    {}

    public static ProductManager getInstance()
    {
        if (singleton == null)
            singleton = new ProductManager();

        return singleton;
    }
    public void addProduct(String symbol) throws DataValidationException {
        ProductBook pb = new ProductBook(symbol);
        books.put(symbol, pb);
    }
    public String getRandomProduct()
    {
        List<String> values = new ArrayList<String>(books.keySet());
        Collections.shuffle(values);

        return values.get(0);
    }
    public OrderDTO addOrder(Order o) throws InvalidPriceOperationException {
        for (String symbol : books.keySet())
        {
            if (o.getProduct().equals(symbol))
            {
                OrderDTO returnOrder = books.get(symbol).add(o);
                return (returnOrder);
            }
        }
        return null;
    }
    public OrderDTO cancel (OrderDTO o)
    {
        for (String symbol : books.keySet())
        {
            if (o.product.equals(symbol))
            {
                return books.get(symbol).cancel(o.side, o.id);
            }
        }
        System.out.println("failed to cancel order " + o.id);
        return null;
    }
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        for (ProductBook pb : books.values())
        {
            str.append(pb.toString()).append("\n");
        }
        return str.toString();
    }

}
