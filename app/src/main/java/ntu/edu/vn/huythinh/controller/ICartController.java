package ntu.edu.vn.huythinh.controller;

import java.util.List;

import ntu.edu.vn.huythinh.model.Product;

public interface ICartController {
    public List<Product> getAllProduct();
    public boolean addToCart(Product product);
    public List<Product> getCart();
    public boolean addProduct(Product product);
}
