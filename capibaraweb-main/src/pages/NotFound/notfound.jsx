import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { notFoundService } from "../../services/notFoundService";
import { useAuth } from "../../context/AuthContext";
import "./notfound.css";

const NotFound = ({ statusCode = 404 }) => {
  const navigate = useNavigate();
  const location = useLocation();
  const { user, getToken } = useAuth();
  
  const [suggestions, setSuggestions] = useState([]);
  const [commonPages, setCommonPages] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [filteredPages, setFilteredPages] = useState([]);
  const [countdown, setCountdown] = useState(10);
  const [showFeedback, setShowFeedback] = useState(false);
  const [feedbackData, setFeedbackData] = useState({ email: "", message: "" });
  const [feedbackLoading, setFeedbackLoading] = useState(false);
  const [feedbackSuccess, setFeedbackSuccess] = useState(false);

  // Effetto countdown reindirizzamento automatico
  useEffect(() => {
    const timer = setInterval(() => {
      setCountdown((prev) => {
        if (prev <= 1) {
          clearInterval(timer);
          navigate("/");
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(timer);
  }, [navigate]);

  // Effetto caricamento dati
  useEffect(() => {
    const loadData = async () => {
      // Carica suggerimenti basati su URL tentato
      const sug = notFoundService.getSuggestions(location.pathname);
      setSuggestions(sug);

      // Carica pagine comuni
      const pages = notFoundService.getCommonPages();
      setCommonPages(pages);
      setFilteredPages(pages);

      // Log errore 404
      try {
        await notFoundService.logNotFoundError({
          url: location.pathname,
          userAgent: navigator.userAgent,
          timestamp: new Date().toISOString(),
          userId: user?.id_utente || "guest",
        });
      } catch (error) {
        console.log("Errore logging (non critico):", error);
      }
    };

    loadData();
  }, [location.pathname, user?.id_utente]);

  // Effetto filtro ricerca
  useEffect(() => {
    if (searchQuery.trim()) {
      const filtered = notFoundService.searchPages(searchQuery, commonPages);
      setFilteredPages(filtered);
    } else {
      setFilteredPages(commonPages);
    }
  }, [searchQuery, commonPages]);

  const errorInfo = notFoundService.getErrorMessage(statusCode);

  const handleFeedbackSubmit = async (e) => {
    e.preventDefault();
    
    if (!feedbackData.email || !feedbackData.message) {
      alert("Compila tutti i campi");
      return;
    }

    try {
      setFeedbackLoading(true);
      await notFoundService.sendErrorFeedback({
        email: feedbackData.email,
        message: feedbackData.message,
        errorPath: location.pathname,
      });
      
      setFeedbackSuccess(true);
      setFeedbackData({ email: "", message: "" });
      setTimeout(() => setShowFeedback(false), 3000);
    } catch (error) {
      alert("Errore nell'invio del feedback: " + error.message);
    } finally {
      setFeedbackLoading(false);
    }
  };

  return (
    <div className="notfound-page">
      {/* Hero Section */}
      <section className="notfound-hero">
        <div className="hero-content">
          <div className="error-icon">{errorInfo.icon}</div>
          <h1 className="error-code">{statusCode}</h1>
          <h2 className="error-title">{errorInfo.title}</h2>
          <p className="error-message">{errorInfo.message}</p>
          
          <p className="redirect-notice">
            Verrai reindirizzato alla home tra <span className="countdown">{countdown}</span>s
          </p>
        </div>
      </section>

      {/* Search Section */}
      <section className="notfound-search">
        <div className="search-container">
          <h3>Stai cercando qualcosa?</h3>
          <div className="search-box">
            <input
              type="text"
              placeholder="Cerca una pagina..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="search-input"
            />
          </div>

          {/* Search Results */}
          <div className="search-results">
            {filteredPages.length > 0 ? (
              <div className="pages-grid">
                {filteredPages.map((page) => (
                  <button
                    key={page.path}
                    className="page-card"
                    onClick={() => navigate(page.path)}
                  >
                    <span className="page-icon">{page.icon}</span>
                    <span className="page-name">{page.name}</span>
                  </button>
                ))}
              </div>
            ) : (
              <p className="no-results">Nessuna pagina trovata</p>
            )}
          </div>
        </div>
      </section>

      {/* Quick Links */}
      <section className="notfound-quick">
        <h3>Navigazione Rapida</h3>
        <div className="quick-links">
          {suggestions.map((link) => (
            <button
              key={link.path}
              className="quick-link"
              onClick={() => navigate(link.path)}
            >
              {link.icon} {link.title}
            </button>
          ))}
        </div>
      </section>

      {/* Actions */}
      <section className="notfound-actions">
        <button
          className="btn btn-primary"
          onClick={() => navigate("/")}
        >
          🏠 Torna alla Home
        </button>
        <button
          className="btn btn-secondary"
          onClick={() => navigate(-1)}
        >
          ← Pagina Precedente
        </button>
        <button
          className="btn btn-outline"
          onClick={() => setShowFeedback(!showFeedback)}
        >
          📧 Segnala il problema
        </button>
      </section>

      {/* Feedback Section */}
      {showFeedback && (
        <section className="notfound-feedback">
          <div className="feedback-container">
            <h3>Segnala il Problema</h3>
            
            {feedbackSuccess ? (
              <div className="feedback-success alert alert-success">
                ✅ Grazie! Abbiamo ricevuto il tuo segnale.
              </div>
            ) : (
              <form onSubmit={handleFeedbackSubmit} className="feedback-form">
                <div className="form-group">
                  <label>Email:</label>
                  <input
                    type="email"
                    value={feedbackData.email}
                    onChange={(e) =>
                      setFeedbackData({ ...feedbackData, email: e.target.value })
                    }
                    placeholder="tua@email.com"
                    required
                  />
                </div>

                <div className="form-group">
                  <label>Messaggio:</label>
                  <textarea
                    value={feedbackData.message}
                    onChange={(e) =>
                      setFeedbackData({ ...feedbackData, message: e.target.value })
                    }
                    placeholder="Descrivi il problema..."
                    rows="4"
                    required
                  />
                </div>

                <div className="form-info">
                  <small>
                    URL: <code>{location.pathname}</code>
                  </small>
                </div>

                <button
                  type="submit"
                  className="btn btn-primary"
                  disabled={feedbackLoading}
                >
                  {feedbackLoading ? "Invio..." : "Invia Segnalazione"}
                </button>
              </form>
            )}
          </div>
        </section>
      )}

      {/* Info Section */}
      <section className="notfound-info">
        <div className="info-box">
          <h4>💡 Hai bisogno di aiuto?</h4>
          <ul>
            <li>Visita la pagina <strong>FAQ</strong> per domande comuni</li>
            <li>Consulta la sezione <strong>Chi Siamo</strong> per informazioni</li>
            <li>Valuta il tuo immobile nella sezione <strong>Valutazione</strong></li>
            {!user && <li>Accedi o registrati per continuare</li>}
          </ul>
        </div>
      </section>
    </div>
  );
};

export default NotFound;
