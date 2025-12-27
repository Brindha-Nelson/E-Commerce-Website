package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	private final ProductRepository productRepo;
    public AdminController(ProductRepository productRepo){this.productRepo=productRepo;}

    @PostMapping("/product")
    public ResponseEntity<?> add(@RequestBody Product p){
        productRepo.save(p);
        return ResponseEntity.ok(Map.of("message","Product added"));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Product p){
        Optional<Product> op=productRepo.findById(id);
        if(op.isEmpty()) return ResponseEntity.badRequest().body(Map.of("message","Not found"));
        Product existing=op.get();
        existing.setName(p.getName()); existing.setCategory(p.getCategory());
        existing.setDescription(p.getDescription()); existing.setPrice(p.getPrice());
        existing.setImageUrl(p.getImageUrl());
        productRepo.save(existing);
        return ResponseEntity.ok(Map.of("message","Updated"));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        productRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message","Deleted"));
    }
}
