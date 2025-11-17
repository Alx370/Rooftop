import React, { createContext, useContext, useState, useEffect } from 'react';
import { authService } from '../services/authService';

// Crea il context
const AuthContext = createContext();

// Provider component
export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);

  // Verifica l'autenticazione al caricamento
  useEffect(() => {
    const checkAuth = async () => {
      try {
        const token = authService.getToken();
        if (token) {
          const currentUser = await authService.getCurrentUser();
          if (currentUser && currentUser.authenticated) {
            setUser(currentUser);
            setIsAuthenticated(true);
          } else {
            // Token non valido
            authService.logout();
            setUser(null);
            setIsAuthenticated(false);
          }
        }
      } catch (error) {
        console.error('Errore nella verifica autenticazione:', error);
        setUser(null);
        setIsAuthenticated(false);
      } finally {
        setLoading(false);
      }
    };

    checkAuth();
  }, []);

  // Funzione di login
  const login = async (email, password, remember = false) => {
    try {
      setLoading(true);
      const response = await authService.login(email, password);
      authService.saveToken(response.token, remember);

      const currentUser = await authService.getCurrentUser();
      if (currentUser && currentUser.authenticated) {
        authService.saveUser(currentUser, remember);
        setUser(currentUser);
        setIsAuthenticated(true);
        return { success: true };
      }
    } catch (error) {
      console.error('Errore nel login:', error);
      return { success: false, error: error.message };
    } finally {
      setLoading(false);
    }
  };

  // Funzione di logout
  const logout = async () => {
    try {
      setLoading(true);
      await authService.logout();
      setUser(null);
      setIsAuthenticated(false);
      return { success: true };
    } catch (error) {
      console.error('Errore nel logout:', error);
      // Comunque rimuovi lo stato locale
      setUser(null);
      setIsAuthenticated(false);
      return { success: false, error: error.message };
    } finally {
      setLoading(false);
    }
  };

  // Funzione per aggiornare i dati dell'utente
  const refreshUser = async () => {
    try {
      const currentUser = await authService.getCurrentUser();
      if (currentUser && currentUser.authenticated) {
        setUser(currentUser);
        return currentUser;
      } else {
        await logout();
        return null;
      }
    } catch (error) {
      console.error('Errore nell\'aggiornamento utente:', error);
      return null;
    }
  };

  const value = {
    user,
    isAuthenticated,
    loading,
    login,
    logout,
    refreshUser,
    getToken: authService.getToken,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

// Hook personalizzato per usare il context
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth deve essere usato dentro AuthProvider');
  }
  return context;
};
