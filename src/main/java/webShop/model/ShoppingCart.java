package webShop.model;

import java.util.ArrayList;

public class ShoppingCart {

    private ArrayList<Product> cart;

    public ShoppingCart() {
        cart = new ArrayList<Product>();
    }

    public ShoppingCart(Product p) {
        super();
        cart.add(p);
    }

    public void addToCart(Product p) {
        cart.add(p);
    }

    public void removeFromCart(int index) {
        cart.remove(index);
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }

}