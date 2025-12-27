package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins= "http://localhost:3000")

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepo, BCryptPasswordEncoder encoder, JwtUtil jwtUtil){
        this.userRepo=userRepo; this.encoder=encoder; this.jwtUtil=jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String,String> body){
        String username=body.get("username");
        String email=body.get("email");
        String password=body.get("password");
        if(username==null || password==null) return ResponseEntity.badRequest().body(Map.of("message","username and password required"));
        if(userRepo.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message","Already registered please login"));
        }
        if(userRepo.findByEmail(email).isPresent()){
            return ResponseEntity.badRequest().body(Map.of("message","Email already used"));
        }
        User u = new User(username, email, encoder.encode(password), "USER");
        userRepo.save(u);
        return ResponseEntity.ok(Map.of("message","Registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body){
        String username=body.get("username");
        String password=body.get("password");
        Optional<User> u=userRepo.findByUsername(username);
        if(u.isPresent() && encoder.matches(password, u.get().getPassword())) {
            String token=jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token, "username", username));
        } else {
            return ResponseEntity.status(401).body(Map.of("message","Invalid credentials"));
        }
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forget(@RequestBody Map<String,String> body){
        String email=body.get("email");
        var uOpt=userRepo.findByEmail(email);
        if(uOpt.isEmpty()) return ResponseEntity.badRequest().body(Map.of("message","No user with this email"));
        User u=uOpt.get();
        String temp= "temp1234"; 
        u.setPassword(encoder.encode(temp));
        userRepo.save(u);
        return ResponseEntity.ok(Map.of("message","Temporary password set. Use: "+temp));
    }
}
