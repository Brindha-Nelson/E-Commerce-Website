package com.example.demo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductRepository repo;
    public ProductController(ProductRepository repo){this.repo=repo;}

    @GetMapping("/")
    public List<Product> all() { return repo.findAll(); }

    @GetMapping("/category/{cat}")
    public List<Product> byCategory(@PathVariable String cat) {return repo.findByCategory(cat); }

    @GetMapping("/{id}")
    public Object get(@PathVariable String id){
        Optional<Product> p=repo.findById(id);
        return p.isPresent() ? p.get() : ResponseEntity.notFound().build();
    }
}
