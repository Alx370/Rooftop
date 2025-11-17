const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

// Servizio per le API riguardanti "Chi Siamo"
export const chiSiamoService = {
  // Recupera informazioni su "Chi Siamo"
  async getChiSiamo() {
    try {
      const response = await fetch(`${API_BASE_URL}/chi-siamo`, {
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
      console.error('Errore nel recupero Chi Siamo:', error);
      throw error;
    }
  },

  // Recupera i dati del team
  async getTeam() {
    try {
      const response = await fetch(`${API_BASE_URL}/chi-siamo/team`, {
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
      console.error('Errore nel recupero team:', error);
      throw error;
    }
  },

  // Recupera i dati della missione
  async getMissione() {
    try {
      const response = await fetch(`${API_BASE_URL}/chi-siamo/missione`, {
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
      console.error('Errore nel recupero missione:', error);
      throw error;
    }
  },

  // Recupera i valori aziendali
  async getValori() {
    try {
      const response = await fetch(`${API_BASE_URL}/chi-siamo/valori`, {
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
      console.error('Errore nel recupero valori:', error);
      throw error;
    }
  },
};
