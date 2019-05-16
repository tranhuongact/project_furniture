package application.model.viewmodel.cart;

public class CartProductVM {

    private int id;
    private int productId;
    private String mainImage;
    private int amount;
    private String productName;
    private String price;
    private String pricePerProduct;
    private int amountInStore;

    public int getAmountInStore() {
        return amountInStore;
    }

    public void setAmountInStore(int amountInStore) {
        this.amountInStore = amountInStore;
    }

    public String getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(String pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
