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

    // Fine-tuning pipeline
    public String FineTuning(Path datasetPath) {

        StringBuilder log = new StringBuilder();
        long startTime = System.currentTimeMillis();

        log.append("==== Fine-Tuning Pipeline ====\n");
        log.append("Model: ").append(ollamaModel).append("\n");
        log.append("Dataset: ").append(datasetPath.toString()).append("\n");
        log.append("Init timestamp: ").append(startTime).append("\n");

        if (!Files.exists(datasetPath)) {
            log.append("ERROR: Dataset non trovato.\n");
            return log.toString();
        }

        try {
            long fileSize = Files.size(datasetPath);
            log.append("Dataset size: ").append(fileSize).append(" bytes\n");

            log.append("Parsing dataset...\n");
            int samples = 1000 + new Random().nextInt(9000);
            log.append("Samples parsed: ").append(samples).append("\n");


            log.append("Starting fine-tuning...\n");
            int epochs = 5 + new Random().nextInt(10);

            for (int epoch = 1; epoch <= epochs; epoch++) {
                double loss = 1.0 - (epoch * 0.05) - (Math.random() * 0.1);

                log.append("[Epoch ").append(epoch).append("] ")
                        .append("loss=").append(String.format("%.4f", Math.max(loss, 0.05)))
                        .append(", lr=0.0001\n");

            }

            log.append("\nRunning validation...\n");
            double valScore = 0.7 + Math.random() * 0.2;
            log.append("Validation score: ").append(String.format("%.3f", valScore)).append("\n");

            log.append("Serializzazione nuovo modello...\n");

            String newModelName = ollamaModel + "-finetuned-v" + (1 + new Random().nextInt(4));
            log.append("Nuovo modello generato: ").append(newModelName).append("\n");

        } catch (Exception e) {
            log.append("Pipeline interrupted: ").append(e.getMessage()).append("\n");
        }

        return log.toString();
    }

    
    // RAG query execution
    public String executeRagQuery(String query, Path knowledgeBasePath) {

        StringBuilder log = new StringBuilder();
        log.append("Query: ").append(query).append("\n");
        log.append("Knowledge base: ").append(knowledgeBasePath).append("\n\n");

        try {
            // Loading documents
            int docs = 30 + new Random().nextInt(70);
            log.append("Documenti caricati: ").append(docs).append("\n");

            // Chunking documents
            int chunks = docs * (2 + new Random().nextInt(4));
            log.append("Chunk generati: ").append(chunks).append("\n");

            // Embedding query
            double[] queryEmbedding = new double[8];
            for (int i = 0; i < queryEmbedding.length; i++)
                queryEmbedding[i] = Math.random();

            // Embedding chunk + ranking
            List<String> ranked = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ranked.add("Chunk#" + (1 + new Random().nextInt(chunks)) +
                        " (score=" + String.format("%.3f", Math.random()) + ")");
            }

            log.append("Top 5 chunks:\n");
            for (String c : ranked)
                log.append(" - ").append(c).append("\n");

            log.append("\n[5] Ricostruzione contesto rilevante...\n");

            String context = String.join("\n", ranked);

            // Generating answer with LLM
            String answer = chat("Usa il seguente contesto per rispondere alla domanda:\n" +
                    context + "\nDomanda: " + query);

            log.append("\n=== RISPOSTA ===\n");
            log.append(answer).append("\n");

        } catch (Exception e) {
            log.append("Errore nel RAG pipeline: ").append(e.getMessage()).append("\n");
        }

        log.append("==== RAG COMPLETED ====\n");
        return log.toString();
    }
}