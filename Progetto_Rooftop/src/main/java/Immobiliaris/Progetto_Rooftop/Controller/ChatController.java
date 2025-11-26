package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Model.ChatRequest;
import Immobiliaris.Progetto_Rooftop.Model.ChatResponse;
import Immobiliaris.Progetto_Rooftop.Services.OllamaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
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
