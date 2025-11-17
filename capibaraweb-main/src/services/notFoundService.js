import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";
const REQUEST_TIMEOUT = import.meta.env.VITE_API_TIMEOUT || 10000;

/**
 * Not Found Service
 * Fornisce metodi per gestire errori 404 e logging
 */

/**
 * Log errore 404 nel backend
 * @param {Object} errorData - Dati dell'errore { url, userAgent, timestamp, userId }
 * @returns {Promise<Object>}
 */
export const logNotFoundError = async (errorData) => {
  try {
    const response = await axios.post(
      `${API_BASE_URL}/errors/log-404`,
      errorData,
      {
        timeout: REQUEST_TIMEOUT,
      }
    );
    return response.data;
  } catch (error) {
    console.error("Errore nel logging 404:", error);
    // Non throw perché il servizio è opzionale
    return null;
  }
};

/**
 * Suggerimenti di navigazione - Simula ricerca pagine simili
 * @param {string} attemptedPath - Percorso tentato
 * @returns {Array} Array di suggerimenti
 */
export const getSuggestions = (attemptedPath) => {
  const suggestions = [
    { title: "Home", path: "/", icon: "🏠" },
    { title: "Chi Siamo", path: "/chi-siamo", icon: "ℹ️" },
    { title: "FAQ", path: "/faq", icon: "❓" },
    { title: "Valutazione", path: "/valutazione", icon: "💎" },
    { title: "Login", path: "/login", icon: "🔓" },
    { title: "Registrati", path: "/registrati", icon: "📝" },
  ];

  // Se il percorso contiene parole chiave, filtra i suggerimenti
  const lowerPath = attemptedPath.toLowerCase();
  
  if (
    lowerPath.includes("admin") ||
    lowerPath.includes("dashboard") ||
    lowerPath.includes("panel")
  ) {
    return [
      { title: "Home", path: "/", icon: "🏠" },
      { title: "Contattaci", path: "/contact", icon: "📧" },
    ];
  }

  return suggestions;
};

/**
 * Ottiene messaggi errore personalizzati per diverse situazioni
 * @param {number} statusCode - Codice HTTP
 * @returns {Object} { title, message, icon }
 */
export const getErrorMessage = (statusCode = 404) => {
  const messages = {
    404: {
      title: "Pagina Non Trovata",
      message: "La pagina che stai cercando non esiste o è stata spostata.",
      icon: "🔍",
    },
    403: {
      title: "Accesso Negato",
      message: "Non hai i permessi per accedere a questa pagina.",
      icon: "🚫",
    },
    500: {
      title: "Errore Server",
      message: "Si è verificato un errore sul server. Riproviamo più tardi.",
      icon: "⚠️",
    },
    503: {
      title: "Servizio Indisponibile",
      message: "Il servizio è temporaneamente indisponibile.",
      icon: "🔧",
    },
  };

  return messages[statusCode] || messages[404];
};

/**
 * Formatta URL per visualizzazione
 * @param {string} url - URL da formattare
 * @returns {string} URL formattato
 */
export const formatUrl = (url) => {
  if (!url) return "/";
  return url.startsWith("/") ? url : "/" + url;
};

/**
 * Genera rapporto errore per supporto
 * @param {Object} errorInfo - Informazioni errore
 * @returns {string} Testo rapporto
 */
export const generateErrorReport = (errorInfo) => {
  const {
    statusCode = 404,
    attemptedPath = "/",
    userAgent = navigator.userAgent,
    timestamp = new Date().toISOString(),
  } = errorInfo;

  return `
RAPPORTO ERRORE ${statusCode}
===============================
URL: ${attemptedPath}
Data: ${timestamp}
Browser: ${userAgent}
Risoluzione: ${window.innerWidth}x${window.innerHeight}
Lingua: ${navigator.language}
===============================
`;
};

/**
 * Invia feedback errore
 * @param {Object} feedbackData - Dati feedback { email, message, errorPath }
 * @returns {Promise<Object>}
 */
export const sendErrorFeedback = async (feedbackData) => {
  try {
    const response = await axios.post(
      `${API_BASE_URL}/errors/feedback`,
      feedbackData,
      {
        timeout: REQUEST_TIMEOUT,
      }
    );
    return response.data;
  } catch (error) {
    console.error("Errore nell'invio del feedback:", error);
    throw new Error(
      error.response?.data?.message || "Errore nell'invio del feedback"
    );
  }
};

/**
 * Ricerca pagine comuni
 * @returns {Array} Array di pagine comuni
 */
export const getCommonPages = () => {
  return [
    { name: "Home", path: "/", icon: "🏠" },
    { name: "Chi Siamo", path: "/chi-siamo", icon: "ℹ️" },
    { name: "FAQ", path: "/faq", icon: "❓" },
    { name: "Valutazione Immobile", path: "/valutazione", icon: "💎" },
    { name: "Accedi", path: "/login", icon: "🔓" },
    { name: "Registrati", path: "/registrati", icon: "📝" },
  ];
};

/**
 * Ricerca pagina per fuzzy matching
 * @param {string} query - Query di ricerca
 * @param {Array} pages - Array di pagine dove cercare
 * @returns {Array} Risultati ricerca
 */
export const searchPages = (query, pages = getCommonPages()) => {
  if (!query || query.trim() === "") {
    return pages;
  }

  const lower = query.toLowerCase();
  return pages.filter(
    (page) =>
      page.name.toLowerCase().includes(lower) ||
      page.path.toLowerCase().includes(lower)
  );
};

/**
 * Formatta tempo per visualizzazione
 * @param {number} seconds - Secondi
 * @returns {string} Tempo formattato
 */
export const formatTime = (seconds) => {
  if (seconds < 60) return `${seconds}s`;
  return `${Math.floor(seconds / 60)}m ${seconds % 60}s`;
};

/**
 * Verifica se URL è valido
 * @param {string} url - URL da verificare
 * @returns {boolean} True se valido
 */
export const isValidUrl = (url) => {
  try {
    new URL(url, window.location.origin);
    return true;
  } catch {
    return false;
  }
};
