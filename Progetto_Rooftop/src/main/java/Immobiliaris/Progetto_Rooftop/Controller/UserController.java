package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Model.User;
import Immobiliaris.Progetto_Rooftop.Repository.UserRepository;
import Immobiliaris.Progetto_Rooftop.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email già registrata");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        savedUser.setPassword(null); // non restituire la password
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
        if (optionalUser.isEmpty()) return ResponseEntity.badRequest().body("Utente non trovato");

        User user = optionalUser.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            return ResponseEntity.badRequest().body("Password errata");

        String token = jwtUtil.generateToken(user.getEmail());
        user.setPassword(null);
        return ResponseEntity.ok(new AuthResponse(token, user));
    }

    public static class AuthResponse {
        private String token;
        private User user;

        public AuthResponse(String token, User user) {
            this.token = token;
            this.user = user;
        }

        public String getToken() { return token; }
        public User getUser() { return user; }
    }
}
