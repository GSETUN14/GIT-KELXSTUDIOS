package com.example.kelxstudios;

public class CartProduct {
    private int productId;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String size;
    private int quantity;

    // Constructor para inicializar los datos del producto
    public CartProduct(int productId, String name, String description, double price, String imageUrl, String size) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.size = size;
        this.quantity = 1; // La cantidad por defecto es 1
    }

    // Metodos getters para acceder a los valores de los campos
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    // Metodo setter para actualizar la cantidad
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Metodo para incrementar la cantidad en 1
    public void incrementQuantity() {
        this.quantity++;
    }
}
