package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	private final Map<String, List<CartItem>> carts=new HashMap<>();
    private final ProductRepository productRepo;

    public CartController(ProductRepository productRepo){
        this.productRepo=productRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Map<String,String> body){
        String username=body.get("username");
        String productId=body.get("productId");
        int qty=Integer.parseInt(body.getOrDefault("qty","1"));
        Optional<Product> p=productRepo.findById(productId);
        if(p.isEmpty()) return ResponseEntity.badRequest().body(Map.of("message","Product not found"));
        Product prod=p.get();
        List<CartItem> list=carts.getOrDefault(username, new ArrayList<>());
        
        boolean found=false;
        for(CartItem ci : list){
            if(ci.getProductId().equals(productId)){ ci.setQuantity(ci.getQuantity()+qty); found=true; break;}
        }
        if(!found){
            list.add(new CartItem(productId, prod.getName(), qty, prod.getPrice(),prod.getImageUrl()));
        }
        carts.put(username, list);
        return ResponseEntity.ok(Map.of("message","Added to cart"));
    }

    @GetMapping("/{username}")
    public List<CartItem> getCart(@PathVariable String username){
        return carts.getOrDefault(username, new ArrayList<>());
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody Map<String,String> body){
        String username=body.get("username");
        String productId=body.get("productId");
        List<CartItem> list=carts.getOrDefault(username, new ArrayList<>());
        list.removeIf(ci -> ci.getProductId().equals(productId));
        carts.put(username, list);
        return ResponseEntity.ok(Map.of("message","Removed"));
    }

    @PostMapping("/clear")
    public ResponseEntity<?> clear(@RequestBody Map<String,String> body){
        String username=body.get("username");
        carts.remove(username);
        return ResponseEntity.ok(Map.of("message","Cleared"));
    }
}
