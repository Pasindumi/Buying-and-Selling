



public class MobilePhone implements Product {
    private String productName;
    private String color;
    private int quantity;
    private int warranty;
    private double price;
    private int storage;

  
    public MobilePhone(String productName, String color, int quantity, int warranty, double price, int storage) {
        this.productName = productName;
        this.color = color;
        this.quantity = quantity;
        this.warranty = warranty;
        this.price = price;
        this.storage = storage;
    }

   
    @override
    public String getProductName() { 
        return productName;
     }

     @override
    public String getColor() { 
        return color; 
    }
    @override
    public int getQuantity() {
         return quantity;
    }
    @override
    public int getWarranty() {
         return warranty; 
    }
    @override
    public double getPrice() {
         return price; 
    }
    @override
    public int getStorage() {
         return storage; 
    }
}
