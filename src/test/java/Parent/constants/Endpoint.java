package Parent.constants;

public enum Endpoint {
    STORE("/store"),
    ACCOUNT("/account"),
    MEN("/men"),
    WOMEN("/women"),
    ACCESSORIES("/accessories"),
    CART("/cart"),
    CHECKOUT("/checkout");

    public final String url;
    Endpoint(String url){
        this.url=url;
    }


}
