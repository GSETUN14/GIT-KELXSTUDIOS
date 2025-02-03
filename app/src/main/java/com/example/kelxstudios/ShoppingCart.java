package com.example.kelxstudios;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private static ShoppingCart instance;
    private List<CartProduct> products;

    private ShoppingCart() {
        products = new ArrayList<>();
    }

    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void addProduct(CartProduct product) {
        for (CartProduct existingProduct : products) {
            if (existingProduct.getName().equals(product.getName()) &&
                    existingProduct.getSize().equals(product.getSize())) {
                existingProduct.incrementQuantity();
                return;
            }
        }
        products.add(product);
    }

    public void clearCart() {
        products.clear();
    }
}
