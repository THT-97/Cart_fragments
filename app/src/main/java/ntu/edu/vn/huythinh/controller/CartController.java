package ntu.edu.vn.huythinh.controller;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import ntu.edu.vn.huythinh.model.Product;

public class CartController extends Application implements ICartController {
    List<Product> productList = new ArrayList<>();
    List<Product> cartList = new ArrayList<>();

    public CartController() {
        productList.add(new Product("RAM", 300000, "DDR4 16000MHz"));
        productList.add(new Product("CPU", 3000000, "I7 5th Gen"));
        productList.add(new Product("Mouse", 120000, "LogiTech Wireless"));
        productList.add(new Product("Pie", 12000, "Yummy pie"));
        productList.add(new Product("IceCream", 5000, "Yummy icecream"));
        productList.add(new Product("Water", 100000, "Overpriced tapwater"));
    }

    @Override
    public List<Product> getAllProduct() {
        return productList;
    }

    @Override
    public boolean addToCart(Product product) {
        if (cartList.contains(product)) return false;
        cartList.add(product);
        return true;
    }

    @Override
    public List<Product> getCart() {
        return cartList;
    }

    @Override
    public boolean addProduct(Product product) {
        if(productList.contains(product)) return false;
        productList.add(product);
        return true;
    }
}
