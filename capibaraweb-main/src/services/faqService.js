import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";
const FAQ_ENDPOINT = `${API_BASE_URL}/faq`;

// Timeout per le richieste
const REQUEST_TIMEOUT = import.meta.env.VITE_API_TIMEOUT || 10000;

/**
 * FAQ Service
 * Fornisce metodi per gestire le FAQ dal backend
 */

/**
 * Recupera tutte le FAQ ordinate per ordine
 * @returns {Promise<Array>} Array di FAQ
 */
export const getAllFaqs = async () => {
  try {
    const response = await axios.get(FAQ_ENDPOINT, {
      timeout: REQUEST_TIMEOUT,
    });
    return response.data || [];
  } catch (error) {
    console.error("Errore nel recupero delle FAQ:", error);
    throw new Error(
      error.response?.data?.message || "Errore nel recupero delle FAQ"
    );
  }
};

/**
 * Recupera una FAQ per ID
 * @param {number} id - ID della FAQ
 * @returns {Promise<Object>} FAQ trovata
 */
export const getFaqById = async (id) => {
  try {
    const response = await axios.get(`${FAQ_ENDPOINT}/${id}`, {
      timeout: REQUEST_TIMEOUT,
    });
    return response.data;
  } catch (error) {
    console.error(`Errore nel recupero della FAQ ${id}:`, error);
    throw new Error(
      error.response?.data?.message || "FAQ non trovata"
    );
  }
};

/**
 * Recupera le categorie disponibili
 * @returns {Promise<Array>} Array di categorie
 */
export const getCategories = async () => {
  try {
    const response = await axios.get(`${FAQ_ENDPOINT}/categorie`, {
      timeout: REQUEST_TIMEOUT,
    });
    return response.data || [];
  } catch (error) {
    console.error("Errore nel recupero delle categorie:", error);
    throw new Error(
      error.response?.data?.message || "Errore nel recupero delle categorie"
    );
  }
};

/**
 * Recupera FAQ per categoria
 * @param {string} categoria - Nome della categoria (es. VENDITA, AFFITTO)
 * @returns {Promise<Array>} Array di FAQ della categoria
 */
export const getFaqsByCategory = async (categoria) => {
  try {
    const response = await axios.get(
      `${FAQ_ENDPOINT}/categoria/${categoria}`,
      {
        timeout: REQUEST_TIMEOUT,
      }
    );
    return response.data || [];
  } catch (error) {
    console.error(
      `Errore nel recupero delle FAQ per categoria ${categoria}:`,
      error
    );
    throw new Error(
      error.response?.data?.message ||
        `Errore nel recupero delle FAQ per categoria ${categoria}`
    );
  }
};

/**
 * Crea una nuova FAQ (richiede autenticazione)
 * @param {Object} faqData - Dati della FAQ { domanda, risposta, categoria, ordine }
 * @param {string} token - Token JWT
 * @returns {Promise<Object>} FAQ creata
 */
export const createFaq = async (faqData, token) => {
  try {
    if (!token) {
      throw new Error("Token non disponibile");
    }

    const response = await axios.post(FAQ_ENDPOINT, faqData, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      timeout: REQUEST_TIMEOUT,
    });
    return response.data;
  } catch (error) {
    console.error("Errore nella creazione della FAQ:", error);
    throw new Error(
      error.response?.data?.message || "Errore nella creazione della FAQ"
    );
  }
};

/**
 * Aggiorna una FAQ (richiede autenticazione)
 * @param {number} id - ID della FAQ da aggiornare
 * @param {Object} faqData - Dati aggiornati { domanda, risposta, categoria, ordine }
 * @param {string} token - Token JWT
 * @returns {Promise<Object>} FAQ aggiornata
 */
export const updateFaq = async (id, faqData, token) => {
  try {
    if (!token) {
      throw new Error("Token non disponibile");
    }

    const response = await axios.put(
      `${FAQ_ENDPOINT}/${id}`,
      faqData,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        timeout: REQUEST_TIMEOUT,
      }
    );
    return response.data;
  } catch (error) {
    console.error(`Errore nell'aggiornamento della FAQ ${id}:`, error);
    throw new Error(
      error.response?.data?.message || "Errore nell'aggiornamento della FAQ"
    );
  }
};

/**
 * Elimina una FAQ (richiede autenticazione admin)
 * @param {number} id - ID della FAQ da eliminare
 * @param {string} token - Token JWT
 * @returns {Promise<void>}
 */
export const deleteFaq = async (id, token) => {
  try {
    if (!token) {
      throw new Error("Token non disponibile");
    }

    await axios.delete(`${FAQ_ENDPOINT}/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      timeout: REQUEST_TIMEOUT,
    });
  } catch (error) {
    console.error(`Errore nell'eliminazione della FAQ ${id}:`, error);
    throw new Error(
      error.response?.data?.message || "Errore nell'eliminazione della FAQ"
    );
  }
};

/**
 * Valida i dati di una FAQ
 * @param {Object} faqData - Dati da validare
 * @returns {Object} { valid: boolean, errors: Array }
 */
export const validateFaqData = (faqData) => {
  const errors = [];

  if (!faqData.domanda || faqData.domanda.trim() === "") {
    errors.push("La domanda è obbligatoria");
  } else if (faqData.domanda.length < 5) {
    errors.push("La domanda deve avere almeno 5 caratteri");
  }

  if (!faqData.risposta || faqData.risposta.trim() === "") {
    errors.push("La risposta è obbligatoria");
  } else if (faqData.risposta.length < 10) {
    errors.push("La risposta deve avere almeno 10 caratteri");
  }

  if (!faqData.categoria || faqData.categoria.trim() === "") {
    errors.push("La categoria è obbligatoria");
  }

  if (faqData.ordine !== undefined && faqData.ordine !== null) {
    if (isNaN(faqData.ordine) || faqData.ordine < 0) {
      errors.push("L'ordine deve essere un numero non negativo");
    }
  }

  return {
    valid: errors.length === 0,
    errors,
  };
};

/**
 * Formatta una FAQ per la visualizzazione
 * @param {Object} faq - Oggetto FAQ
 * @returns {Object} FAQ formattata
 */
export const formatFaq = (faq) => {
  return {
    ...faq,
    categoria: faq.categoria || "GENERALE",
    ordine: faq.ordine || 0,
  };
};

/**
 * Ordina le FAQ per campo specificato
 * @param {Array} faqs - Array di FAQ
 * @param {string} field - Campo per ordinare (default: ordine)
 * @param {string} direction - Direzione (asc|desc) default: asc
 * @returns {Array} FAQ ordinate
 */
export const sortFaqs = (faqs, field = "ordine", direction = "asc") => {
  return [...faqs].sort((a, b) => {
    const aVal = a[field];
    const bVal = b[field];

    if (aVal < bVal) return direction === "asc" ? -1 : 1;
    if (aVal > bVal) return direction === "asc" ? 1 : -1;
    return 0;
  });
};

/**
 * Filtra FAQ per testo di ricerca
 * @param {Array} faqs - Array di FAQ
 * @param {string} searchText - Testo di ricerca
 * @returns {Array} FAQ filtrate
 */
export const filterFaqsBySearch = (faqs, searchText) => {
  if (!searchText || searchText.trim() === "") {
    return faqs;
  }

  const lowerText = searchText.toLowerCase();
  return faqs.filter(
    (faq) =>
      faq.domanda.toLowerCase().includes(lowerText) ||
      faq.risposta.toLowerCase().includes(lowerText)
  );
};

/**
 * Raggruppa FAQ per categoria
 * @param {Array} faqs - Array di FAQ
 * @returns {Object} FAQ raggruppate per categoria
 */
export const groupFaqsByCategory = (faqs) => {
  return faqs.reduce((acc, faq) => {
    const category = faq.categoria || "GENERALE";
    if (!acc[category]) {
      acc[category] = [];
    }
    acc[category].push(faq);
    return acc;
  }, {});
};
