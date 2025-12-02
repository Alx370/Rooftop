package Immobiliaris.Progetto_Rooftop.Mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * EMAIL SERVICE
 * Service to send emails using Gmail SMTP.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mittente;
    
    /**
     * Send an HTML email
     * @Async = executed in background (non-blocking)
     */
    @Async
    public void inviaEmail(String destinatario, String oggetto, String contenutoHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(mittente);
            helper.setTo(destinatario);
            helper.setSubject(oggetto);
            helper.setText(contenutoHtml, true); // true = it's HTML

            mailSender.send(message);
            System.out.println("Email inviata a: " + destinatario);
        } catch (Exception e) {
            System.out.println("Errore invio email a " + destinatario + ": " + e.getMessage());
        }
    }
    
    /**
     * Send confirmation email when someone subscribes to the newsletter
     */
    public void inviaConfermaNewsletter(String email) {
        String oggetto = "Benvenuto nella Newsletter di Immobiliaris!";
        
        String html = """
            <html>
            <body style="font-family: Arial; padding: 20px;">
                <h1 style="color: #667eea;"> Benvenuto!</h1>
                <p>Grazie per esserti iscritto alla newsletter di <strong>Immobiliaris</strong>!</p>
                <p>Riceverai:</p>
                <ul>
                    <li>Nuove offerte immobiliari</li>
                    <li>Consigli per comprare o vendere casa</li>
                    <li>Anteprime esclusive</li>
                </ul>
                <p>La tua email: <strong>%s</strong></p>
                <hr>
                <p style="color: #666; font-size: 12px;">© 2025 Immobiliaris</p>
            </body>
            </html>
            """.formatted(email);
        
        inviaEmail(email, oggetto, html);
    }

    /**
     * Send confirmation email to user when they submit a contact request
     */
    @Async
    public void inviaConfermaRichiestaContatto(String emailUtente, String nome, Integer idRichiesta) {
        String oggetto = "La tua richiesta è stata presa in carico - Immobiliaris";
        
        String html = """
            <html>
            <body style="font-family: Arial; padding: 20px;">
                <h1 style="color: #28a745;">✓ Richiesta Ricevuta!</h1>
                
                <p>Ciao <strong>%s</strong>,</p>
                
                <p>Grazie per averci contattato! La tua richiesta è stata <strong>presa in considerazione</strong> 
                e un nostro agente ti risponderà al più presto.</p>
                
                <div style="background: #f0f0f0; padding: 15px; border-radius: 5px; margin: 20px 0;">
                    <p><strong>Numero richiesta:</strong> #%d</p>
                </div>
                
                <p>Nel frattempo, ti invitiamo a visitare la nostra sezione 
                <a href="https://immobiliaris.it/faq">FAQ</a> per trovare risposte alle domande più frequenti.</p>
                
                <hr>
                <p style="color: #666; font-size: 12px;">© 2025 Immobiliaris - Questo è un messaggio automatico.</p>
            </body>
            </html>
            """.formatted(nome, idRichiesta);
        
        inviaEmail(emailUtente, oggetto, html);
    }

    /**
     * Notify the user that their question has been added to FAQs
     */
    @Async
    public void inviaNotificaFaqCreata(String emailUtente, String nome, String domanda, String risposta) {
        String oggetto = "La tua domanda è ora nelle FAQ - Immobiliaris";
        
        String html = """
            <html>
            <body style="font-family: Arial; padding: 20px;">
                <h1 style="color: #667eea;">La tua domanda è stata aggiunta alle FAQ!</h1>
                
                <p>Ciao <strong>%s</strong>,</p>
                
                <p>Grazie per la tua domanda! Abbiamo ritenuto che potesse essere utile anche ad altri utenti, 
                quindi l'abbiamo aggiunta alla nostra sezione FAQ.</p>
                
                <div style="background: #f0f0f0; padding: 15px; border-radius: 5px; margin: 20px 0;">
                    <p><strong>La tua domanda:</strong></p>
                    <p style="font-style: italic;">"%s"</p>
                    
                    <p><strong>La nostra risposta:</strong></p>
                    <p>%s</p>
                </div>
                
                <p>Puoi consultare tutte le FAQ sul nostro sito: 
                <a href="https://immobiliaris.it/faq">Visita le FAQ</a></p>
                
                <hr>
                <p style="color: #666; font-size: 12px;">© 2025 Immobiliaris</p>
            </body>
            </html>
            """.formatted(nome, domanda, risposta);
        
        inviaEmail(emailUtente, oggetto, html);
    }

    // CONTACT REQUEST NOTIFICATION EMAIL (to agent)
    public void inviaNotificaRichiestaContatto(String emailAgente, Integer idRichiesta, String nome, String cognome,
                                                String emailCliente, String telefono, String messaggio) {
        String oggetto = "Nuova Richiesta di Contatto - " + nome + " " + cognome + " (ID: " + idRichiesta + ")";

        String curlExample = "curl -X PUT http://localhost:8080/api/contatto/" + idRichiesta + 
                "/faq -H \"Authorization: Bearer <TOKEN_ADMIN>\"";

        String html = """
            <html>
            <body style="font-family: Arial; padding: 20px;">
                <h1 style="color: #28a745;"> Nuova Richiesta di Contatto</h1>

                <div style="background: #f0f0f0; padding: 15px; border-radius: 5px;">
                    <p><strong>Nome:</strong> %s %s</p>
                    <p><strong>Email:</strong> %s</p>
                    <p><strong>Telefono:</strong> %s</p>
                </div>

                <h3>Messaggio:</h3>
                <p style="background: #fff; padding: 15px; border-left: 4px solid #28a745;">%s</p>

                <p><em>Per salvare questa richiesta come FAQ puoi:</em></p>
                <ol>
                  <li>Accedere al pannello di amministrazione (se disponibile).</li>
                  <li>Eseguire il seguente comando curl (sostituire <TOKEN_ADMIN> con il tuo token):</li>
                </ol>
                <pre style="background:#f8f8f8;padding:10px;border-radius:4px;">%s</pre>

                <p>Oppure visita l'endpoint API `/api/contatto/%d/faq` con una richiesta HTTP PUT autenticata.</p>
                <hr>
                <p style="color: #666; font-size: 12px;">Sistema automatico Immobiliaris</p>
            </body>
            </html>
            """.formatted(nome, cognome, emailCliente,
                         telefono != null ? telefono : "Non fornito",
                         messaggio,
                         curlExample,
                         idRichiesta);

        inviaEmail(emailAgente, oggetto, html);
    }
}
