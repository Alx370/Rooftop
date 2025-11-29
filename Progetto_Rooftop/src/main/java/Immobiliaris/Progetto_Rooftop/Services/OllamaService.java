package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.OllamaRequest;
import Immobiliaris.Progetto_Rooftop.Model.OllamaResponse;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OllamaService {
    
    private final WebClient webClient;
    private final String ollamaModel;
    private final String systemPrompt;
    
    public OllamaService(
            @Value("${ollama.api.url}") String ollamaApiUrl,
            @Value("${ollama.model}") String ollamaModel,
            @Value("${ollama.system.prompt}") String systemPrompt) { 
        this.webClient = WebClient.builder()
                .baseUrl(ollamaApiUrl)
                .build();
        this.ollamaModel = ollamaModel;
        this.systemPrompt = systemPrompt;
    }
    
    public String chat(String userMessage) {
        OllamaRequest request = new OllamaRequest(ollamaModel, userMessage, systemPrompt);
        
        try {
            OllamaResponse response = webClient.post()
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OllamaResponse.class)
                    .block();
            
            return response != null ? response.getResponse() : "Errore nella risposta";
        } catch (Exception e) {
            return "Errore nella comunicazione con Ollama: " + e.getMessage();
        }
    }

    // Streaming chat method
    public void chatStream(String userMessage, Consumer<String> onToken) {
    OllamaRequest request = new OllamaRequest(ollamaModel, userMessage, systemPrompt);

    webClient.post()
            .bodyValue(request)
            .retrieve()
            .bodyToFlux(String.class) // recive as stream of strings
            .subscribe(
                    chunk -> {
                        try {
                            // Extracts the token from the JSON (Ollama sends JSON for each line)
                            if (chunk.contains("\"response\"")) {
                                String token = chunk.split("\"response\":\"")[1].split("\"")[0];
                                onToken.accept(token);
                            }
                        } catch (Exception ignored) {}
                    },
                    error -> {
                        onToken.accept("[STREAM ERROR] " + error.getMessage());
                    }
            );
    }

    

}
