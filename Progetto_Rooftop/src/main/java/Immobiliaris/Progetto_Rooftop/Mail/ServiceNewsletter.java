package Immobiliaris.Progetto_Rooftop.Mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * SUBSCRIPTION FLOW:
 * 1. User sends their email
 * 2. Check if it already exists
 * 3. Save to the database
 * 4. Send confirmation email
 */
@Service
@RequiredArgsConstructor
public class ServiceNewsletter {

    private final RepoNewsletter newsletterRepository;
    private final EmailService emailService;

    /**
     * Subscribe a new user to the newsletter
     * @param email - the email to subscribe
     * @return Newsletter - the object saved in the DB
     */
    public Newsletter iscriviNewsletter(String email) {
        // 1. Check if already subscribed
        if (newsletterRepository.existsByEmail(email)) {
            throw new RuntimeException("Email già iscritta alla newsletter");
        }

        // 2. Create and save in the database
        Newsletter newsletter = new Newsletter();
        newsletter.setEmail(email);
        Newsletter saved = newsletterRepository.save(newsletter);

        // 3. Send confirmation email
        emailService.inviaConfermaNewsletter(email);

        return saved;
    }

    /**
     * Unsubscribe a user from the newsletter (does not delete, sets active = false)
     * @param email - the email to unsubscribe
     */
    public void disiscriviNewsletter(String email) {
        Newsletter newsletter = newsletterRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Email non trovata"));
        
        newsletter.setAttivo(false);
        newsletterRepository.save(newsletter);
    }
}
