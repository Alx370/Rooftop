package Immobiliaris.Progetto_Rooftop.Mail;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaFaq;
import Immobiliaris.Progetto_Rooftop.Model.Faq;
import Immobiliaris.Progetto_Rooftop.Repos.RepoFaq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * CONTACT REQUEST SERVICE
 * Contains the business logic for contact requests / FAQs.
 * 
 * FLOW:
 * 1. Customer fills out the form (first name, last name, email, phone, message)
 * 2. Save the request in the database
 * 3. Send an email notification to the agent
 * 4. The agent may save the request as an FAQ
 */
@Service
@RequiredArgsConstructor
public class ServiceRichiestaContatto {

    private final RepoRichiestaContatto richiestaRepository;
    private final RepoFaq faqRepository;
    private final EmailService emailService;

    // Agent email that receives notifications (configurable in application.properties)
    @Value("${app.email.agente:admin@rooftop-immobiliare.it}")
    private String emailAgente;

    /**
     * Create a new contact request
     * @return RichiestaContatto saved in the DB
     */
    public RichiestaContatto creaRichiesta(String nome, String cognome, String email, 
                                           String telefono, String messaggio) {
        // 1. Create and save the request
        RichiestaContatto richiesta = new RichiestaContatto();
        richiesta.setNome(nome);
        richiesta.setCognome(cognome);
        richiesta.setEmail(email);
        richiesta.setTelefono(telefono);
        richiesta.setMessaggio(messaggio);
        
        RichiestaContatto saved = richiestaRepository.save(richiesta);

        // 2. Send confirmation email to the user
        emailService.inviaConfermaRichiestaContatto(email, nome, saved.getIdRichiesta());

        // 3. Send notification email to the agent (include the generated request ID)
        emailService.inviaNotificaRichiestaContatto(emailAgente, saved.getIdRichiesta(), nome, cognome, email, telefono, messaggio);

        return saved;
    }

    /**
     * Get all requests
     */
    public List<RichiestaContatto> getTutteLeRichieste() {
        return richiestaRepository.findAll();
    }

    /**
     * Get requests by status (NUOVA, LETTA, COMPLETATA)
     */
    public List<RichiestaContatto> getRichiestePerStato(RichiestaContatto.StatoRichiesta stato) {
        return richiestaRepository.findByStato(stato);
    }

    /**
     * Agent marks the request as read
     */
    public RichiestaContatto marcaComeInLavorazione(Integer id) {
        RichiestaContatto richiesta = richiestaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Richiesta non trovata"));
        richiesta.setStato(RichiestaContatto.StatoRichiesta.IN_LAVORAZIONE);
        return richiestaRepository.save(richiesta);
    }

    /**
     * Agent saves the request as an FAQ with a response
     */
    public RichiestaContatto salvaComeFaq(Integer id, String risposta, CategoriaFaq categoria) {
        RichiestaContatto richiesta = richiestaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Richiesta non trovata"));

        if (risposta == null || risposta.isBlank()) {
            throw new RuntimeException("La risposta è obbligatoria per salvare come FAQ");
        }

        // 1. Create a new FAQ entry from the contact request
        Faq faq = new Faq();
        faq.setCategoria(categoria != null ? categoria : CategoriaFaq.GENERALE);
        faq.setDomanda(richiesta.getMessaggio());
        faq.setRisposta(risposta);
        faq.setOrdine(faqRepository.findAll().size() + 1);
        faqRepository.save(faq);

        // 2. Mark contact request as saved-as-FAQ and completed
        richiesta.setSalvataComeFaq(true);
        richiesta.setStato(RichiestaContatto.StatoRichiesta.COMPLETATA);
        RichiestaContatto saved = richiestaRepository.save(richiesta);

        // 3. Notify the user that their question has been added to FAQs
        emailService.inviaNotificaFaqCreata(richiesta.getEmail(), richiesta.getNome(), 
            richiesta.getMessaggio(), risposta);

        return saved;
    }

    /**
     * Delete a request
     */
    public void eliminaRichiesta(Integer id) {
        richiestaRepository.deleteById(id);
    }
}
