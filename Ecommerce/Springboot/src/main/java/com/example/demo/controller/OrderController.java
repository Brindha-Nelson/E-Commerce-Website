package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.demo.model.Order;
import com.example.demo.model.CartItem;
import com.example.demo.repository.OrderRepository;
import com.example.demo.controller.CartController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderRepository orderRepo;
    private final CartController cartController;

    public OrderController(OrderRepository orderRepo, CartController cartController){
        this.orderRepo=orderRepo;
        this.cartController=cartController;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String,String> body){
        String username=body.get("username");
        String address=body.get("address");
        String payment=body.get("paymentMethod");
        List<CartItem> items=cartController.getCart(username);
        if(items.isEmpty()) return ResponseEntity.badRequest().body(Map.of("message","Cart empty"));
        Order o=new Order(username, items, address, payment);
        orderRepo.save(o);
        cartController.clear(Map.of("username", username));
        return ResponseEntity.ok(Map.of("message","Order placed", "orderId", o.getId()));
    }

    @GetMapping("/user/{username}")
    public List<Order> userOrders(@PathVariable String username){
        return orderRepo.findByUsername(username);
    }

    @GetMapping("/admin/all")
    public List<Order> allOrders(){ return orderRepo.findAll(); }

    @PostMapping("/admin/mark-delivered")
    public ResponseEntity<?> markDelivered(@RequestBody Map<String,String> body){
        String orderId=body.get("orderId");
        var o=orderRepo.findById(orderId);
        if(o.isEmpty()) return ResponseEntity.badRequest().body(Map.of("message","Order not found"));
        var order=o.get();
        order.setStatus("delivered");
        orderRepo.save(order);
        return ResponseEntity.ok(Map.of("message","Marked delivered"));
    }
}
