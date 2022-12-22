package webShop.exception;

public class WebShopException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    String err;
    public WebShopException(String error) {

        super(error);
        err = error;
    }
    public String getErr(){return err;}

}