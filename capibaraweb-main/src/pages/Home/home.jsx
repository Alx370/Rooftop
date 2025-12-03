import React, { useRef, useEffect } from "react";
import "./home.css";
import { Link } from "react-router-dom";
import { setPageMeta, setStructuredData } from "../../utils/seo";
import { subscribeNewsletter } from "../api/newsletterApi.js";


// Immagini card e USP
import finestraImg from "../../assets/images/home/finestra.webp";
import lettoImg from "../../assets/images/home/letto.webp";
import edificioImg from "../../assets/images/home/edificio.webp";
import chiaviImg from "../../assets/images/home/chiavi.webp";
import lauraImg from "../../assets/images/home/laura.webp";

import logoImg from "../../assets/images/home/LogoImmobiliaris.webp";
import palazzoImg from "../../assets/images/home/PalazzoModerno.webp";

const Home = () => {
  // Ref per la griglia dei testimonial
  const testimonialGridRef = useRef(null);

  // SEO: Imposta meta tag e structured data al caricamento
  useEffect(() => {
    // Meta tag per SEO e Open Graph
    setPageMeta({
      title: 'Immobiliaris - Valutazione e Vendita Immobili | Agenzia Immobiliare',
      description: 'Vendi o affitta casa con Immobiliaris. Valutazione gratuita in 72 ore, consulenza personalizzata e vendita in esclusiva. Affidati ai nostri esperti immobiliari per vendere al giusto valore.',
      url: window.location.href,
      image: logoImg,
      type: 'website'
    });

    // Structured Data (JSON-LD) per migliorare la visibilità nei motori di ricerca
    setStructuredData({
      "@context": "https://schema.org",
      "@type": "RealEstateAgent",
      "name": "Immobiliaris",
      "description": "Agenzia immobiliare specializzata in vendita e affitto di immobili con valutazione gratuita in 72 ore",
      "url": window.location.origin,
      "logo": logoImg,
      "priceRange": "$$",
      "address": {
        "@type": "PostalAddress",
        "addressCountry": "IT"
      },
      "aggregateRating": {
        "@type": "AggregateRating",
        "ratingValue": "4.8",
        "reviewCount": "127"
      },
      "sameAs": [
        // Aggiungi qui i link ai social media quando disponibili
      ],
      "hasOfferCatalog": {
        "@type": "OfferCatalog",
        "name": "Servizi Immobiliari",
        "itemListElement": [
          {
            "@type": "Offer",
            "itemOffered": {
              "@type": "Service",
              "name": "Valutazione Immobile Gratuita",
              "description": "Valutazione professionale del tuo immobile in 72 ore"
            }
          },
          {
            "@type": "Offer",
            "itemOffered": {
              "@type": "Service",
              "name": "Vendita Immobile in Esclusiva",
              "description": "Servizio completo di vendita con strategie mirate e trasparenza"
            }
          },
          {
            "@type": "Offer",
            "itemOffered": {
              "@type": "Service",
              "name": "Gestione Affitti",
              "description": "Gestione completa di inquilini, contratti e pratiche per affitto"
            }
          }
        ]
      }
    });
  }, []);

  // Funzione per scroll fluido
  const smoothScroll = (direction) => {
    if (!testimonialGridRef.current) return;

    const grid = testimonialGridRef.current;
    const card = grid.querySelector(".testimonial-card");
    if (!card) return;

    const cardWidth = card.offsetWidth + 32; // 32 = gap tra card in px
    const newScroll = grid.scrollLeft + direction * cardWidth;

    grid.scrollTo({
      left: newScroll,
      behavior: "smooth",
    });
  };

  // Array dinamico dei testimonial
  const testimonials = [
    {
      text: "Esperienza fantastica, servizio impeccabile e staff super disponibile. Consigliatissimo!",
      img: lauraImg,
      name: "Marco Rossi",
    },
    {
      text:
        "Mi sono sentita seguita passo dopo passo. Hanno reso tutto semplice e veloce, anche nei momenti più stressanti.",
      img: lauraImg,
      name: "Giulia Verdi",
    },
    {
      text:
        "Servizio eccellente! Il team è stato professionale e attento ai dettagli. Tornerò sicuramente per il prossimo progetto!",
      img: lauraImg,
      name: "Luca Bianchi",
    },
    {
      text:
        "Professionalità e cortesia ineguagliabili. Consiglio vivamente a chiunque voglia vendere o affittare.",
      img: lauraImg,
      name: "Anna Neri",
    },
    {
      text:
        "Ottima esperienza, tutto semplice e veloce grazie al team. Tornerò sicuramente.",
      img: lauraImg,
      name: "Marco L.",
    },
  ];

  const handleNewsletter = async (e) => {
  e.preventDefault();

  const formData = new FormData(e.target);
  const email = formData.get("email");

  if (!email) return;

  try {
    const res = await subscribeNewsletter(email);
    alert("Iscrizione completata! 🎉");
    e.target.reset();
  } catch (err) {
    console.error(err);
    alert("Errore nell'iscrizione alla newsletter.");
  }
};


  return (
    <div className="home">
      {/* ---------------- HERO ---------------- */}
      <section className="hero" aria-label="Sezione principale">
        {/* Immagini di sfondo ora in HTML, non più nel CSS */}
        <img
          src={logoImg}
          alt="Logo Immobiliaris - Agenzia Immobiliare"
          className="hero-bg-logo-img"
          loading="eager"
        />
        <img
          src={palazzoImg}
          alt="Palazzo moderno - Immobili di prestigio"
          className="hero-bg-palazzo-img"
          loading="eager"
        />

        <div className="hero-content">
          <div className="hero-text">
            <h1><span>Immobiliaris</span></h1>
            <p>
              Vendere casa è un percorso importante. Noi lo rendiamo più
              semplice. Affidati ai nostri esperti per una valutazione accurata
              e un supporto concreto, dal primo passo alla firma.
            </p>
            <div className="hero-buttons">
              <Link 
                className="btn primary" 
                to="/valutazione"
                aria-label="Richiedi valutazione gratuita del tuo immobile"
              >
                Ottieni valutazione
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* ---------------- INTRO ---------------- */}
      <section className="intro-home" aria-labelledby="servizi-heading">
        <h2 id="servizi-heading">I nostri servizi</h2>
        <p className="subtitle-home">
          Ti aiutiamo a valorizzare la tua casa e a venderla al giusto valore.
        </p>

        <div className="intro-cards-home">
          <article className="card-home">
            <img src={finestraImg} alt="Interno casa elegante con finestra" loading="lazy" />
            <div className="card-content-home">
              <h3>Per venderlo</h3>
              <p>
                Ti guidiamo nella vendita con metodo, trasparenza e attenzione
                al valore.
              </p>
              <Link 
                className="btn primary" 
                to="/valutazione"
                aria-label="Valuta il tuo immobile per la vendita"
              >
                Valuta
              </Link>
            </div>
          </article>

          <article className="card-home">
            <img src={lettoImg} alt="Camera da letto moderna" loading="lazy" />
            <div className="card-content-home">
              <h3>Per affittarlo</h3>
              <p>
                Gestiamo tutto noi: inquilini, contratti e sicurezza del
                tuo immobile.
              </p>
              <Link 
                className="btn primary" 
                to="/valutazione"
                aria-label="Valuta il tuo immobile per l'affitto"
              >
                Valuta
              </Link>
            </div>
          </article>
        </div>
      </section>

      {/* ---------------- USP ---------------- */}
      <section className="usp-section" aria-labelledby="vantaggi-heading">
        <h2 id="vantaggi-heading">Perché scegliere noi</h2>
        <div className="usp-grid">
          <article className="usp-item">
            <div className="usp-text">
              <h3 className="highlight1">Valutazione in 72 ore</h3>
              <p>
                <strong>Rapida, accurata, umana.</strong> Ti offriamo una
                valutazione <strong>entro 72 ore</strong>, verificata dai nostri
                esperti e basata su <strong>dati reali</strong>.
              </p>
            </div>
            <div className="usp-img">
              <img src={edificioImg} alt="Edificio moderno - valutazione professionale" loading="lazy" />
            </div>
          </article>

          <article className="usp-item reverse">
            <div className="usp-img">
              <img src={chiaviImg} alt="Chiavi di casa - vendita sicura" loading="lazy" />
            </div>
            <div className="usp-text">
              <h3 className="highlight1">Metodo user-centred</h3>
              <p>
                <strong>Sarai al centro di ogni scelta.</strong> Verrai
                affiancato con competenza per valorizzare al meglio il tuo
                immobile.
              </p>
            </div>
          </article>

          <article className="usp-item-centered">
            <div className="usp-text-centered">
              <h3 className="highlight">Vendita in esclusiva</h3>
              <p>
                Un impegno reciproco per risultati concreti. Ci dedichiamo al
                tuo immobile con <strong>strategie mirate</strong> e{" "}
                <strong>trasparenza</strong>, per vendere al giusto valore.
              </p>
            </div>
          </article>
        </div>
      </section>

      {/* ---------------- TESTIMONIALS ---------------- */}
      <section className="testimonials" aria-labelledby="recensioni-heading">
        <h2 id="recensioni-heading">Cosa dicono di noi</h2>
        <p className="subtitle">
          La fiducia dei nostri clienti, raccontata da loro. Le loro parole
          parlano di ascolto, professionalità e risultati concreti.
        </p>

        <div className="testimonial-container">
          <button
            className="scroll-btn left"
            type="button"
            onClick={() => smoothScroll(-1)}
            aria-label="Scorri recensioni precedenti"
          >
            ←
          </button>

          <div className="testimonial-grid" ref={testimonialGridRef}>
            {testimonials.map((t, index) => (
              <article key={index} className="testimonial-card" itemScope itemType="https://schema.org/Review">
                <p itemProp="reviewBody">{t.text}</p>
                <img src={t.img} alt={`Foto profilo di ${t.name}`} loading="lazy" />
                <h5 itemProp="author" itemScope itemType="https://schema.org/Person">
                  - <span itemProp="name">{t.name}</span>
                </h5>
                <meta itemProp="reviewRating" content="5" />
              </article>
            ))}
          </div>

          <button
            className="scroll-btn right"
            type="button"
            onClick={() => smoothScroll(1)}
            aria-label="Scorri recensioni successive"
          >
            →
          </button>
        </div>
      </section>

      {/* ---------------- FAQ ---------------- */}
      <section className="faq" aria-labelledby="faq-heading" itemScope itemType="https://schema.org/FAQPage">
        <div className="faq-left">
          <h2 id="faq-heading" className="faq-title">
            Qui per rispondere a tutte le tue domande
          </h2>
          <div className="faq-text-home">
            <p>
              Abbiamo raccolto le domande più frequenti dei nostri clienti, con
              risposte semplici e trasparenti per aiutarti a trovare subito le
              informazioni che cerchi.
            </p>
          </div>
          <Link 
            className="btn primary" 
            to="/faq"
            aria-label="Visualizza tutte le domande frequenti"
          >
            Trova Risposta
          </Link>
        </div>

        <div className="faq-boxes">
          <details className="faq-item" itemScope itemProp="mainEntity" itemType="https://schema.org/Question">
            <summary>
              <span itemProp="name">Posso affittare casa anche se vivo in un'altra città?</span>
            </summary>
            <div itemScope itemProp="acceptedAnswer" itemType="https://schema.org/Answer">
              <p itemProp="text">
                Certo. I nostri consulenti si occupano di tutto: selezione degli
                inquilini, contratti e gestione delle pratiche. Così puoi
                affittare in tranquillità, ovunque tu sia.
              </p>
            </div>
          </details>

          <details className="faq-item" itemScope itemProp="mainEntity" itemType="https://schema.org/Question">
            <summary>
              <span itemProp="name">Devo pagare per la valutazione dell'immobile?</span>
            </summary>
            <div itemScope itemProp="acceptedAnswer" itemType="https://schema.org/Answer">
              <p itemProp="text">
                No, la valutazione del tuo immobile, tramite il nostro form, è
                completamente gratuita e senza impegno.
              </p>
            </div>
          </details>

          <details className="faq-item" itemScope itemProp="mainEntity" itemType="https://schema.org/Question">
            <summary>
              <span itemProp="name">Gestite anche contratti di affitto a lungo termine?</span>
            </summary>
            <div itemScope itemProp="acceptedAnswer" itemType="https://schema.org/Answer">
              <p itemProp="text">
                Sì, gestiamo anche contratti di affitto a lungo termine, offrendo
                assistenza completa in ogni fase.
              </p>
            </div>
          </details>
        </div>
      </section>

      {/* ---------------- NEWSLETTER ---------------- */}
      <section className="newsletter" aria-labelledby="newsletter-heading">
        <h2 id="newsletter-heading">Iscriviti alla newsletter</h2>
        <p>
          Resta aggiornato con consigli e approfondimenti dai nostri esperti per{" "}
        </p>
        <p>vendere o affittare in modo consapevole.</p>
        <form className="newsletter-form" onSubmit={handleNewsletter} aria-label="Form iscrizione newsletter">
          <input 
            type="email" 
            placeholder="Inserisci la tua email" 
            required
            aria-label="Indirizzo email per newsletter"
            name="email"
          />
          <button className="btn primary" type="submit" aria-label="Iscriviti alla newsletter">
            Iscriviti
          </button>
        </form>
      </section>
    </div>
  );

};

export default Home;

/**
 * Home Page Component for Immobiliaris Real Estate Agency.
 *
 * This component renders the main landing page, including:
 * - Hero section with branding and call-to-action
 * - Introduction to services (selling and renting properties)
 * - Unique Selling Propositions (USP) grid
 * - Testimonials carousel with smooth scrolling
 * - Frequently Asked Questions (FAQ) section
 * - Newsletter subscription form
 *
 * SEO meta tags and structured data are set on mount for better search engine visibility.
 *
 * @component
 * @returns {JSX.Element} The rendered Home page
 *
 * @example
 * <Home />
 *
 * @see src/utils/seo.js for SEO helpers
 * @see src/pages/Home/home.css for styles
 *
 * @typedef {Object} Testimonial
 * @property {string} text - The testimonial text
 * @property {string} img - Path to the testimonial image
 * @property {string} name - Name of the testimonial author
 *
 * @function smoothScroll
 * @description Smoothly scrolls the testimonial grid left or right by one card width
 * @param {number} direction - Direction to scroll: -1 (left), 1 (right)
 *
 * @function setPageMeta
 * @description Sets SEO meta tags for the page
 * @param {Object} meta - Meta tag properties
 *
 * @function setStructuredData
 * @description Injects JSON-LD structured data for search engines
 * @param {Object} data - Structured data object
 */