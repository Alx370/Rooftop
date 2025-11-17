const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

// Servizio per le API di valutazione immobili
export const valuationService = {
  // Recupera tutti gli immobili
  async getAllProperties() {
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

      return await response.json();
    } catch (error) {
      console.error('Errore nel recupero immobili:', error);
      throw error;
    }
  },

  // Recupera un immobile per ID
  async getPropertyById(id) {
    try {
      const response = await fetch(`${API_BASE_URL}/immobili/${id}`, {
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
      console.error('Errore nel recupero immobile:', error);
      throw error;
    }
  },

  // Crea un nuovo immobile (richiede autenticazione)
  async createProperty(propertyData, token) {
    try {
      const response = await fetch(`${API_BASE_URL}/immobili`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(propertyData),
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(
          errorData?.message || errorData?.error || `Errore: ${response.status}`
        );
      }

      return await response.json();
    } catch (error) {
      console.error('Errore nella creazione immobile:', error);
      throw error;
    }
  },

  // Aggiorna un immobile (richiede autenticazione)
  async updateProperty(id, propertyData, token) {
    try {
      const response = await fetch(`${API_BASE_URL}/immobili/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(propertyData),
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(
          errorData?.message || errorData?.error || `Errore: ${response.status}`
        );
      }

      return await response.json();
    } catch (error) {
      console.error('Errore nell\'aggiornamento immobile:', error);
      throw error;
    }
  },

  // Elimina un immobile (richiede autenticazione AMMINISTRATORE)
  async deleteProperty(id, token) {
    try {
      const response = await fetch(`${API_BASE_URL}/immobili/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error(`Errore: ${response.status}`);
      }

      return { success: true, message: 'Immobile eliminato' };
    } catch (error) {
      console.error('Errore nell\'eliminazione immobile:', error);
      throw error;
    }
  },

  // Valida i dati dell'immobile
  validatePropertyData(data) {
    const errors = [];

    if (!data.titolo || data.titolo.trim() === '') {
      errors.push('Il titolo è obbligatorio');
    }

    if (!data.indirizzo || data.indirizzo.trim() === '') {
      errors.push('L\'indirizzo è obbligatorio');
    }

    if (!data.citta || data.citta.trim() === '') {
      errors.push('La città è obbligatoria');
    }

    if (!data.provincia || data.provincia.trim() === '') {
      errors.push('La provincia è obbligatoria');
    }

    if (!data.cap || data.cap.trim() === '') {
      errors.push('Il CAP è obbligatorio');
    }

    if (!data.tipologia) {
      errors.push('La tipologia è obbligatoria');
    }

    if (!data.metri_quadri || data.metri_quadri <= 0) {
      errors.push('I metri quadri devono essere maggiori di 0');
    }

    if (!data.anno_costruzione || data.anno_costruzione < 1900) {
      errors.push('L\'anno di costruzione non è valido');
    }

    if (!data.prezzo_richiesto || data.prezzo_richiesto <= 0) {
      errors.push('Il prezzo richiesto deve essere maggiore di 0');
    }

    if (!data.stato_immobile) {
      errors.push('Lo stato dell\'immobile è obbligatorio');
    }

    return {
      valid: errors.length === 0,
      errors,
    };
  },

  // Ottiene le tipologie disponibili
  getPropertyTypes() {
    return [
      'APPARTAMENTO',
      'VILLA',
      'CASA_INDIPENDENTE',
      'ATTICO',
      'LOFT',
      'MANSARDA',
      'RUSTICO',
      'CASALE',
    ];
  },

  // Ottiene gli stati disponibili
  getPropertyStates() {
    return ['OTTIMO', 'BUONO', 'DA_RISTRUTTURARE', 'NUOVO'];
  },

  // Ottiene gli stati annuncio disponibili
  getAnnouncementStates() {
    return ['VALUTAZIONE', 'PUBBLICATO', 'VENDUTO', 'RITIRATO', 'SOSPESO'];
  },

  // Calcola una stima del prezzo in base a parametri
  estimatePrice(propertyData) {
    let basePrice = 1000; // €/m²

    // Aggiusta il prezzo in base allo stato
    const stateMultipliers = {
      NUOVO: 1.3,
      OTTIMO: 1.2,
      BUONO: 1.0,
      DA_RISTRUTTURARE: 0.6,
    };
    basePrice *= stateMultipliers[propertyData.stato_immobile] || 1.0;

    // Aggiusta il prezzo in base alla tipologia
    const typeMultipliers = {
      VILLA: 1.4,
      ATTICO: 1.3,
      APPARTAMENTO: 1.0,
      CASA_INDIPENDENTE: 1.2,
      LOFT: 1.1,
      MANSARDA: 0.8,
      RUSTICO: 0.5,
      CASALE: 0.7,
    };
    basePrice *= typeMultipliers[propertyData.tipologia] || 1.0;

    // Calcola il prezzo totale
    const estimatedPrice = basePrice * propertyData.metri_quadri;

    return Math.round(estimatedPrice);
  },

  // Formatta il prezzo in valuta
  formatPrice(price) {
    return new Intl.NumberFormat('it-IT', {
      style: 'currency',
      currency: 'EUR',
    }).format(price);
  },
};
