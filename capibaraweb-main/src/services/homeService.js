const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

// Servizio per le API della Home page
export const homeService = {
  // Recupera tutti gli immobili in primo piano
  async getFeaturedProperties() {
    try {
      const response = await fetch(`${API_BASE_URL}/immobili`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error(`Errore: ${response.status}`);
      }

      const data = await response.json();
      // Ritorna i primi 6 immobili
      return Array.isArray(data) ? data.slice(0, 6) : [];
    } catch (error) {
      console.error('Errore nel recupero immobili:', error);
      throw error;
    }
  },

  // Recupera le recensioni verificate
  async getReviews() {
    try {
      const response = await fetch(`${API_BASE_URL}/recensioni`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error(`Errore: ${response.status}`);
      }

      const data = await response.json();
      // Filtra solo le recensioni verificate e ritorna le prime 5
      return Array.isArray(data)
        ? data
            .filter((r) => r.verificata === true)
            .slice(0, 5)
        : [];
    } catch (error) {
      console.error('Errore nel recupero recensioni:', error);
      throw error;
    }
  },

  // Recupera statistiche della piattaforma
  async getStats() {
    try {
      const response = await fetch(`${API_BASE_URL}/home/stats`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error(`Errore: ${response.status}`);
      }

      return await response.json();
    } catch (error) {
      console.error('Errore nel recupero statistiche:', error);
      throw error;
    }
  },

  // Recupera i servizi offerti
  async getServices() {
    try {
      const response = await fetch(`${API_BASE_URL}/home/services`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error(`Errore: ${response.status}`);
      }

      return await response.json();
    } catch (error) {
      console.error('Errore nel recupero servizi:', error);
      throw error;
    }
  },

  // Iscrivi un utente alla newsletter
  async subscribeNewsletter(email) {
    try {
      const response = await fetch(`${API_BASE_URL}/home/newsletter/subscribe`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email }),
      });

      if (!response.ok) {
        throw new Error(`Errore: ${response.status}`);
      }

      return await response.json();
    } catch (error) {
      console.error('Errore nella sottoscrizione newsletter:', error);
      throw error;
    }
  },
};
