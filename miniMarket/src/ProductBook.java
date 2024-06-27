import PricePackage.InvalidPriceOperationException;
import PricePackage.Price;

class ProductBook {
    private String product;
    private final ProductBookSide buySide;
    private final ProductBookSide sellSide;

    public ProductBook(String product) throws DataValidationException {
        setProduct(product);
        buySide = new ProductBookSide(BookSide.BUY);
        sellSide = new ProductBookSide(BookSide.SELL);
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
                throw new DataValidationException("Invalid product name, char" + z);
            }
        }
        product = newProduct;
    }
    public OrderDTO add(Order o) throws InvalidPriceOperationException {
        ProductBookSide side = (o.getSide() == BookSide.BUY) ? buySide : sellSide;
        side.add(o);
        tryTrade();
        return o.makeTradableDTO();
    }

    public OrderDTO cancel(BookSide side, String orderId) {
        ProductBookSide bookSide = (side == BookSide.BUY) ? buySide : sellSide;
        return bookSide.cancel(orderId);
    }

    public void tryTrade() throws InvalidPriceOperationException {
        //I had to remove these as its the only way the code would run, otherwise
        //I would either get nullpointerexceptions or I would get an infinite loop


        Price topBuy = buySide.topOfBookPrice();
        Price topSell = sellSide.topOfBookPrice();

        while (topBuy != null && topSell != null && topBuy.greaterOrEqual(topSell)) {
            //System.out.println("entered trytrade loop");

            //System.out.println("topBuy : " + topBuy.toString());
            //System.out.println("topSell : " + topSell.toString());

            int buyVolume = buySide.topOfBookVolume();
            int sellVolume = sellSide.topOfBookVolume();
            int tradeVolume = Math.min(buyVolume, sellVolume);

            //System.out.println("buyVol : " + buyVolume + " sellVol : " + sellVolume + " tradeVol : " + tradeVolume);

            sellSide.tradeOut(topSell, tradeVolume);
            buySide.tradeOut(topBuy, tradeVolume);

            topBuy = buySide.topOfBookPrice();
            topSell = sellSide.topOfBookPrice();

        }



        /*
        while (topBuy != null && topSell != null && topBuy.greaterOrEqual(topSell))
        {
            int buyVolume = buySide.topOfBookVolume();
            int sellVolume = sellSide.topOfBookVolume();
            int tradeVolume = Math.min(buyVolume, sellVolume);

            buySide.tradeOut(topSell, tradeVolume);
            sellSide.tradeOut(topBuy, tradeVolume);
            topBuy = buySide.topOfBookPrice();
            topSell = sellSide.topOfBookPrice();

        }

         */


    }

    @Override
    public String toString() {
        return String.format("Product: %s\n\t%s\t%s", product, buySide.toString(), sellSide.toString());
    }
}
