package com.example.demo.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "orders")
public class Order {
	@Id
    private String id;
    private String username;
    private List<CartItem> items;
    private String address;
    private String paymentMethod;
    private String status; 
    private Instant createdAt;

    public Order(){}

    public Order(String username, List<CartItem> items, String address, String paymentMethod){
        this.username=username; this.items=items; this.address=address; this.paymentMethod=paymentMethod;
        this.status= "pending";
        this.createdAt=Instant.now();
    }
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getUsername(){return username;} public void setUsername(String username){this.username=username;}
    public List<CartItem> getItems(){return items;} public void setItems(List<CartItem> items){this.items=items;}
    public String getAddress(){return address;} public void setAddress(String address){this.address=address;}
    public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String paymentMethod){this.paymentMethod=paymentMethod;}
    public String getStatus(){return status;} public void setStatus(String status){this.status=status;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant createdAt){this.createdAt=createdAt;}
}
