package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
	private final Map<String, List<CartItem>> lists=new HashMap<>();
    private final ProductRepository productRepo;

    public WishlistController(ProductRepository productRepo){this.productRepo=productRepo; }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Map<String,String> body){
        String username=body.get("username");
        String productId=body.get("productId");
        Optional<Product> p=productRepo.findById(productId);
        if(p.isEmpty()) return ResponseEntity.badRequest().body(Map.of("message","Product not found"));
        var prod=p.get();
        List<CartItem> list=lists.getOrDefault(username, new ArrayList<>());
        boolean exists=list.stream().anyMatch(ci -> ci.getProductId().equals(productId));
        if(!exists) list.add(new CartItem(productId, prod.getName(),1, prod.getPrice(),prod.getImageUrl()));
        lists.put(username, list);
        return ResponseEntity.ok(Map.of("message","Added to wishlist"));
    }

    @GetMapping("/{username}")
    public List<CartItem> get(@PathVariable String username){
        return lists.getOrDefault(username, new ArrayList<>());
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody Map<String,String> body){
        String username=body.get("username");
        String productId=body.get("productId");
        List<CartItem> list=lists.getOrDefault(username, new ArrayList<>());
        list.removeIf(ci -> ci.getProductId().equals(productId));
        lists.put(username, list);
        return ResponseEntity.ok(Map.of("message","Removed"));
    }
}
