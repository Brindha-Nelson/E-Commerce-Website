package com.example.demo.model;

public class Wishlist {
        private String productId;
	    private String productName;
	    private double price;
	    private String imageUrl;  

	    public Wishlist() {}

	    public Wishlist(String productId, String productName, double price, String imageUrl) {
	        this.productId=productId;
	        this.productName=productName;
	        this.price=price;
	        this.imageUrl=imageUrl;
	    }

	    public String getProductId() {
	        return productId;
	    }
	    public String getProductName() {
	        return productName;
	    }
	    public double getPrice() {
	        return price;
	    }
	    public String getImageUrl() {
	        return imageUrl;
	    }
	    public void setProductId(String productId) {
	        this.productId = productId;
	    }
	    public void setProductName(String productName) {
	        this.productName = productName;
	    }
	    public void setPrice(double price) {
	        this.price = price;
	    }
	    public void setImageUrl(String imageUrl) {
	        this.imageUrl = imageUrl;
	    }
	
}
