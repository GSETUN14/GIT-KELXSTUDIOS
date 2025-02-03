package com.example.kelxstudios;

public class Product {
    private String name;
    private String description;
    private String price;
    private String imageUrl;

    // Constructor
    public Product(String name, String description, String price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}

