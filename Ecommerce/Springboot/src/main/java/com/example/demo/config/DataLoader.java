package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.Product;
import com.example.demo.model.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class DataLoader implements CommandLineRunner{
	private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    public DataLoader(ProductRepository productRepo, UserRepository userRepo, BCryptPasswordEncoder encoder){
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
    	if (productRepo.count()==0) {
            productRepo.save(new Product("Lenovo IdeaPad", "Laptops", "Affordable laptop", 45000, "/images/Lenovo IdeaPad.jpg"));
            productRepo.save(new Product("Dell Inspiron", "Laptops", "Powerful laptop", 75000, "/images/Dell Inspiron.jpg"));
            productRepo.save(new Product("Men's Sneaker", "Mens Shoes", "Comfort shoe", 2000, "/images/Men's Sneaker.jpg"));
            productRepo.save(new Product("Ladies Heels", "Ladies Shoes", "Stylish heels", 3500, "/images/Ladies Heels.jpg"));
            productRepo.save(new Product("Kids Running Shoe", "Kids Shoes", "Lightweight", 1200, "/images/Kids Running Shoe.jpg"));
            productRepo.save(new Product("Men's Shirt", "Mens Dresses", "Casual shirt", 800, "/images/Men's Shirt.jpg"));
            productRepo.save(new Product("Ladies Dress", "Ladies Dresses", "Elegant dress", 2500, "/images/Velvet ball gown.jpg"));
            productRepo.save(new Product("Kids Dress", "Kids Dress", "Cute dress", 900, "/images/Kids Dress.jpg"));
            productRepo.save(new Product("iPhone 14", "Mobiles", "Apple phone", 70000, "/images/iPhone 14.jpg"));
            productRepo.save(new Product("Samsung Galaxy", "Mobiles", "Android phone", 48000, "/images/Samsung Galaxy.jpg"));
        }
    	
        if (userRepo.findByUsername("admin").isEmpty()) {
            User admin = new User(
                    "admin",
                    "admin@ecom.com",
                    encoder.encode("admin123"),
                    "ADMIN"
            );
            userRepo.save(admin);
        }
    }
}
