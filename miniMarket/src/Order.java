import PricePackage.Price;

public class Order {
    private String user;
    private String product;
    private Price price;
    private BookSide side;
    private final String id;
    private int originalVolume;
    private int remainingVolume;
    private int cancelledVolume;
    private int filledVolume;

    public Order(String newUser, String product, Price price, int originalVolume, BookSide side) throws DataValidationException {
        setUser(newUser);
        setProduct(product);
        setPrice(price);
        setSide(side);
        id = user + product + price.toString() + System.nanoTime();
        setOriginalVolume(originalVolume);
        remainingVolume = originalVolume;
        cancelledVolume = 0;
        filledVolume = 0;
    }

    private void setUser(String newUser) throws DataValidationException {
        if (newUser.length() != 3)
        {
            throw new DataValidationException("Invalid User length");
        }
        for (int i = 0; i < 3; i++)
        {
            if (!Character.isAlphabetic(newUser.charAt(i)) || !Character.isUpperCase(newUser.charAt(i)))
            {
                throw new DataValidationException("Invalid user name");
            }
        }
        user = newUser;
    }
    private void setProduct(String newProduct) throws DataValidationException {
        if (newProduct.length() > 5 || newProduct.length() < 1)
        {
            throw new DataValidationException("Invalid product length");
        }
        for (int i = 0; i < newProduct.length(); i++)
        {
            char z = newProduct.charAt(i);
            if (((!Character.isAlphabetic(z) && !Character.isDigit(z)) || !Character.isUpperCase(z)) && z != '.')
            {
                throw new DataValidationException("Invalid product name");
            }
        }
        product = newProduct;
    }
    private void setPrice(Price newPrice) throws DataValidationException {
        if (newPrice == null)
        {
            throw new DataValidationException("Price cannot be null");
        }
        price = newPrice;
    }
    private void setSide(BookSide newSide) throws DataValidationException {
        if (newSide == null)
        {
            throw new DataValidationException("Side cannot be null");
        }
        side = newSide;
    }
    private void setOriginalVolume(int newVolume) throws DataValidationException {
        if (newVolume > 0 && newVolume < 10000)
        {
            originalVolume = newVolume;
        }
        else {
            throw new DataValidationException("Invalid Original Volume");
        }
    }

    public OrderDTO makeTradableDTO() {
        return new OrderDTO(user, product, price, side, originalVolume, remainingVolume, cancelledVolume, filledVolume, id);
    }
    @Override
    public String toString() {
        return String.format("%s order: %s %s at %s, Orig Vol: %d, Rem Vol: %d, Fill Vol: %d, CXL Vol: %d, ID: %s",
                user, side, product, price, originalVolume, remainingVolume, filledVolume, cancelledVolume, id);
    }

    public BookSide getSide()
    {
        return side;
    }

    public Price getPrice()
    {
        return price;
    }

    public String getId() {
        return id;
    }

    public int getRemainingVolume() {
        return remainingVolume;
    }

    public String getProduct() {
        return product;
    }

    public int getCancelledVolume() {
        return cancelledVolume;
    }

    public int getFilledVolume() {
        return filledVolume;
    }

    public void setRemainingVolume(int remainingVolume) {
        this.remainingVolume= remainingVolume;
    }

    public void setCancelledVolume(int cancelledVolume) {
        this.cancelledVolume = cancelledVolume;
    }

    public void setFilledVolume(int filledVolume) {
        this.filledVolume = filledVolume;
    }
}
