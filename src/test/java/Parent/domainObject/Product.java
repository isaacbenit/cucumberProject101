package Parent.domainObject;

public class Product {
    private String product;

    public Product(String product) {
        this.product = product;
    }

    public String getProductName() {
        return product;
    }

    public void setProductName(String product) {
        this.product = product;
    }
}