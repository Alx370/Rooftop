const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

// Servizio per le API di autenticazione
export const authService = {
  // Login utente
  async login(email, password) {
    try {
      const response = await fetch(`${API_BASE_URL}/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(
          errorData?.message || errorData?.error || 'Credenziali non valide'
        );
      }

      return await response.json();
    } catch (error) {
      console.error('Errore nel login:', error);
      throw error;
    }
  },

  // Logout utente
  async logout() {
    try {
      const token = this.getToken();
      const response = await fetch(`${API_BASE_URL}/auth/logout`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token ? `Bearer ${token}` : '',
        },
      });

      if (!response.ok) {
        throw new Error('Errore nel logout');
      }

      // Rimuovi il token dal localStorage/sessionStorage
      localStorage.removeItem('auth_token');
      sessionStorage.removeItem('auth_token');
      localStorage.removeItem('auth_user');
      sessionStorage.removeItem('auth_user');

      return await response.json();
    } catch (error) {
      console.error('Errore nel logout:', error);
      // Anche se l'API fallisce, rimuovi il token localmente
      localStorage.removeItem('auth_token');
      sessionStorage.removeItem('auth_token');
      localStorage.removeItem('auth_user');
      sessionStorage.removeItem('auth_user');
      throw error;
    }
  },

  // Recupera info dell'utente autenticato
  async getCurrentUser() {
    try {
      const token = this.getToken();
      if (!token) {
        return null;
      }

      const response = await fetch(`${API_BASE_URL}/auth/me`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error('Errore nel recupero utente');
      }

      const data = await response.json();
      return data.authenticated ? data : null;
    } catch (error) {
      console.error('Errore nel recupero utente:', error);
      return null;
    }
  },

  // Recupera il token dal storage
  getToken() {
    return localStorage.getItem('auth_token') || sessionStorage.getItem('auth_token');
  },

  // Controlla se l'utente è autenticato
  isAuthenticated() {
    return !!this.getToken();
  },

  // Salva il token nel storage
  saveToken(token, remember = false) {
    const storage = remember ? localStorage : sessionStorage;
    storage.setItem('auth_token', token);
  },

  // Salva i dati dell'utente nel storage
  saveUser(user, remember = false) {
    const storage = remember ? localStorage : sessionStorage;
    storage.setItem('auth_user', JSON.stringify(user));
  },

  // Recupera i dati dell'utente dal storage
  getUser() {
    const user = localStorage.getItem('auth_user') || sessionStorage.getItem('auth_user');
    return user ? JSON.parse(user) : null;
  },

  // Verifica il token (richiama l'endpoint /me per validare)
  async verifyToken() {
    const user = await this.getCurrentUser();
    return user ? true : false;
  },
};
