package Immobiliaris.Progetto_Rooftop.Model;

public class OllamaRequest {
    private String model;
    private String prompt;
    private String system;
    private boolean stream;
    
    public OllamaRequest(String model, String prompt, String system) {
        this.model = model;
        this.prompt = prompt;
        this.system = system;
        this.stream = false;
    }
    
    // Getters e Setters
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getPrompt() {
        return prompt;
    }
    
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    
    public String getSystem() {
        return system;
    }
    
    public void setSystem(String system) {
        this.system = system;
    }
    
    public boolean isStream() {
        return stream;
    }
    
    public void setStream(boolean stream) {
        this.stream = stream;
    }
}