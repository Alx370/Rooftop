import React, { useState, useEffect } from "react";
import "./Faq.css";
import Postino from "../../assets/images/Postino.png";
import CasaHero from "../../assets/images/CasaHero.png";
import { faqService } from "../../services/faqService";
import { useAuth } from "../../context/AuthContext";

const Faq = () => {
  const { user, getToken } = useAuth();
  const [faqs, setFaqs] = useState([]);
  const [filteredFaqs, setFilteredFaqs] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("TUTTI");
  const [searchText, setSearchText] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [expandedFaqId, setExpandedFaqId] = useState(null);

  // Carica FAQ all'avvio
  useEffect(() => {
    loadFaqs();
  }, []);

  // Filtra FAQ quando cambiano search, categoria o faqs
  useEffect(() => {
    filterFaqs();
  }, [faqs, selectedCategory, searchText]);

  const loadFaqs = async () => {
    try {
      setLoading(true);
      setError(null);

      // Carica FAQ
      const faqsData = await faqService.getAllFaqs();
      const sortedFaqs = faqService.sortFaqs(faqsData);
      setFaqs(sortedFaqs);

      // Carica categorie
      const categoriesData = await faqService.getCategories();
      setCategories(categoriesData);
    } catch (err) {
      console.error("Errore nel caricamento delle FAQ:", err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const filterFaqs = () => {
    let filtered = [...faqs];

    // Filtra per categoria
    if (selectedCategory !== "TUTTI") {
      filtered = filtered.filter((faq) => faq.categoria === selectedCategory);
    }

    // Filtra per ricerca
    filtered = faqService.filterFaqsBySearch(filtered, searchText);

    setFilteredFaqs(filtered);
  };

  const handleCategoryChange = (category) => {
    setSelectedCategory(category);
    setExpandedFaqId(null);
  };

  const handleSearchChange = (e) => {
    setSearchText(e.target.value);
    setExpandedFaqId(null);
  };

  const handleToggleFaq = (id) => {
    setExpandedFaqId(expandedFaqId === id ? null : id);
  };

  return (
    <div className="faq-page">
      <section className="faq-hero">
        <div className="container hero-container">
          <div className="hero-text">
            <h1>Domande più frequenti</h1>
            <p className="hero-sub">Trova le risposte a tutte le tue domande sulla nostra piattaforma</p>
          </div>

          <div className="hero-image">
            <img src={CasaHero} alt="Casa Hero" />
          </div>
        </div>
      </section>

      <section className="faq-section">
        <div className="faq-text">
          <h2>Qui per rispondere a tutte le tue domande</h2>
          <p>
            Abbiamo raccolto le domande più frequenti dai nostri clienti e preparato risposte dettagliate per aiutarti.
          </p>
        </div>

        {/* Filtri */}
        <div className="faq-filters">
          <div className="search-box">
            <input
              type="text"
              placeholder="Cerca una domanda..."
              value={searchText}
              onChange={handleSearchChange}
              className="search-input"
            />
          </div>

          <div className="category-filters">
            <button
              className={`category-btn ${selectedCategory === "TUTTI" ? "active" : ""}`}
              onClick={() => handleCategoryChange("TUTTI")}
            >
              Tutti
            </button>
            {categories.map((category) => (
              <button
                key={category}
                className={`category-btn ${selectedCategory === category ? "active" : ""}`}
                onClick={() => handleCategoryChange(category)}
              >
                {category}
              </button>
            ))}
          </div>
        </div>

        {/* Loading State */}
        {loading && (
          <div className="faq-loading">
            <p>Caricamento FAQ in corso...</p>
          </div>
        )}

        {/* Error State */}
        {error && !loading && (
          <div className="faq-error alert alert-error">
            <p>⚠️ Errore nel caricamento delle FAQ: {error}</p>
            <button onClick={loadFaqs} className="btn-retry">Riprova</button>
          </div>
        )}

        {/* Empty State */}
        {!loading && !error && filteredFaqs.length === 0 && (
          <div className="faq-empty">
            <p>Nessuna FAQ trovata. Prova a modificare i filtri di ricerca.</p>
          </div>
        )}

        {/* FAQ List */}
        {!loading && !error && filteredFaqs.length > 0 && (
          <div className="faq-boxes">
            {filteredFaqs.map((faq) => (
              <div
                key={faq.id_faq}
                className={`faq-item ${expandedFaqId === faq.id_faq ? "expanded" : ""}`}
              >
                <button
                  className="faq-question"
                  onClick={() => handleToggleFaq(faq.id_faq)}
                >
                  <span className="question-text">{faq.domanda}</span>
                  <span className="faq-icon">{expandedFaqId === faq.id_faq ? "−" : "+"}</span>
                </button>
                {expandedFaqId === faq.id_faq && (
                  <div className="faq-answer">
                    <p>{faq.risposta}</p>
                    <span className="faq-category-tag">{faq.categoria}</span>
                  </div>
                )}
              </div>
            ))}
          </div>
        )}

        {/* FAQ Counter */}
        {!loading && !error && (
          <div className="faq-counter">
            {filteredFaqs.length} domanda{filteredFaqs.length !== 1 ? "e" : ""} trovata{filteredFaqs.length !== 1 ? "e" : ""}
          </div>
        )}
      </section>

      {/* ---- FORM CON IMMAGINE ---- */}
      <section className="faq-contact">
        <div className="contact-column">
          <h2>Non trovi la risposta alla tua domanda?</h2>
          <p>
            Lorem ipsum dolor sit amet consectetur. Sit senectus amet nunc
            viverra. Est diam nulla risus nam vitae quis.
          </p>
        </div>

        <div className="contact-row">
          <div className="faq-contact-text">
            <form className="faq-form">
              <div className="input-wrap">
                <input type="text" placeholder="Nome e cognome" />
              </div>
              <div className="input-wrap">
                <input type="email" placeholder="Mail" />
              </div>
              <div className="input-wrap">
                <textarea placeholder="Messaggio..." />
              </div>
              <button type="submit" className="btn-primary">Invia</button>
            </form>
          </div>
          <div className="faq-contact-image">
            <img src={Postino} alt="Postino" />
          </div>
        </div>
      </section>

      <section className="faq-reviews">
        <div className="container">
          <h2 className="reviews-title">Cosa dicono i nostri clienti</h2>
          <div className="reviews-grid">
            <div className="review-card">
              <p className="review-text">
                “Servizio impeccabile! Gli agenti sono stati sempre disponibili e hanno trovato la casa perfetta per noi.”
              </p>
              <p className="review-author">— Martina B.</p>
            </div>

            <div className="review-card">
              <p className="review-text">
                “Esperienza molto positiva. Piattaforma chiara e contatti rapidi con i consulenti immobiliari.”
              </p>
              <p className="review-author">— Luca F.</p>
            </div>

            <div className="review-card">
              <p className="review-text">
                “Ho venduto il mio appartamento in due settimane. Consigliatissimo per la professionalità.”
              </p>
              <p className="review-author">— Giulia M.</p>
            </div>
          </div>
        </div>
      </section>

    </div>
  );
};

export default Faq;
