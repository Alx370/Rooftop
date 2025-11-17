const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

// Servizio per le API di registrazione
export const registrationService = {
  // Registra un nuovo proprietario
  async registerProprietario(userData) {
    try {
      const response = await fetch(`${API_BASE_URL}/utenti/registrati`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          nome: userData.nome,
          cognome: userData.cognome,
          email: userData.email,
          password: userData.password,
          telefono: userData.telefono || null,
        }),
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(
          errorData?.message || errorData?.error || 'Errore nella registrazione'
        );
      }

      return await response.json();
    } catch (error) {
      console.error('Errore nella registrazione:', error);
      throw error;
    }
  },

  // Registra un nuovo cliente
  async registerCliente(userData) {
    try {
      const response = await fetch(`${API_BASE_URL}/clienti/registrati`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          nome: userData.nome,
          cognome: userData.cognome,
          email: userData.email,
          telefono: userData.telefono || null,
          indirizzo: userData.indirizzo || null,
        }),
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(
          errorData?.message || errorData?.error || 'Errore nella registrazione cliente'
        );
      }

      return await response.json();
    } catch (error) {
      console.error('Errore nella registrazione cliente:', error);
      throw error;
    }
  },

  // Valida email (check se esiste)
  async validateEmail(email) {
    try {
      const response = await fetch(`${API_BASE_URL}/utenti/email/${email}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      // Se ritorna 404, l'email non esiste (è libera)
      if (response.status === 404) {
        return { available: true };
      }

      // Se ritorna 200, l'email esiste già
      if (response.ok) {
        return { available: false };
      }

      return { available: false };
    } catch (error) {
      console.error('Errore nella validazione email:', error);
      // Per sicurezza, considera l'email come non disponibile in caso di errore
      return { available: false };
    }
  },

  // Valida password (regole di complessità)
  validatePassword(password) {
    const errors = [];

    if (password.length < 8) {
      errors.push('La password deve contenere almeno 8 caratteri');
    }
    if (!/[A-Z]/.test(password)) {
      errors.push('La password deve contenere almeno una lettera maiuscola');
    }
    if (!/[a-z]/.test(password)) {
      errors.push('La password deve contenere almeno una lettera minuscola');
    }
    if (!/[0-9]/.test(password)) {
      errors.push('La password deve contenere almeno un numero');
    }
    if (!/[!@#$%^&*]/.test(password)) {
      errors.push('La password deve contenere almeno un carattere speciale (!@#$%^&*)');
    }

    return {
      valid: errors.length === 0,
      errors,
    };
  },

  // Valida email (formato)
  validateEmailFormat(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  },

  // Valida telefono (formato)
  validatePhoneFormat(phone) {
    if (!phone) return true; // Campo opzionale
    const phoneRegex = /^[\d\s\-+()]+$/;
    return phoneRegex.test(phone) && phone.replace(/\D/g, '').length >= 10;
  },
};
