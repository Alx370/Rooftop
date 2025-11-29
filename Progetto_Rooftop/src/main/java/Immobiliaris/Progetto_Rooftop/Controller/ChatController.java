package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Model.ChatRequest;
import Immobiliaris.Progetto_Rooftop.Model.ChatResponse;
import Immobiliaris.Progetto_Rooftop.Services.OllamaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

    // Endpoint REST to stream chat messages
    @PostMapping("/api/chat/stream")
    @ResponseBody
    public SseEmitter chatStream(@RequestBody ChatRequest request) {

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        new Thread(() -> {
            try {
                ollamaService.chatStream(request.getMessage(), token -> {
                    try {
                        emitter.send(SseEmitter.event().name("message").data(token));
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                    }
                });

                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }

}
