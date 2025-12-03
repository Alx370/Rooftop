package Immobiliaris.Progetto_Rooftop.ChatBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {
    
    @Autowired
    private OllamaService ollamaService;
    
    @GetMapping("/")
    public String chatPage() {
        return "chat";
    }
    
    // Endpoint REST to send chat messages
    @PostMapping("/api/chat")
    @ResponseBody
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String response = ollamaService.chat(request.getMessage());
        return new ChatResponse(response);
    }

}
