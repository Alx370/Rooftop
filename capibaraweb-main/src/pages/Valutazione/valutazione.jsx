import React, { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { valuationService } from '../../services/valuationService';
import './valutazione.css';

const Valutazione = () => {
  const { isAuthenticated, getToken } = useAuth();
  const [formData, setFormData] = useState({
    titolo: '',
    descrizione: '',
    indirizzo: '',
    civico: '',
    citta: '',
    provincia: '',
    cap: '',
    quartiere: '',
    tipologia: 'APPARTAMENTO',
    metri_quadri: '',
    piano: '',
    anno_costruzione: new Date().getFullYear(),
    prezzo_richiesto: '',
    stato_immobile: 'BUONO',
    stato_annuncio: 'VALUTAZIONE',
  });

  const [errors, setErrors] = useState([]);
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const [estimatedPrice, setEstimatedPrice] = useState(null);
  const [step, setStep] = useState(1); // 1 = form, 2 = riepilogo

  const propertyTypes = valuationService.getPropertyTypes();
  const propertyStates = valuationService.getPropertyStates();

  const handleChange = (e) => {
    const { name, value } = e.target;
    const updatedData = { ...formData, [name]: value };
    setFormData(updatedData);

    // Calcola stima automaticamente quando cambiano parametri chiave
    if (['tipologia', 'metro_quadri', 'stato_immobile'].includes(name)) {
      try {
        const estimated = valuationService.estimatePrice(updatedData);
        setEstimatedPrice(estimated);
      } catch (err) {
        console.error('Errore nel calcolo stima:', err);
      }
    }

    // Pulisci errori quando utente inizia a scrivere
    if (errors.length > 0) {
      setErrors([]);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Valida i dati
    const validation = valuationService.validatePropertyData(formData);
    if (!validation.valid) {
      setErrors(validation.errors);
      return;
    }

    if (!isAuthenticated) {
      setErrors(['Devi accedere per inviare una valutazione']);
      return;
    }

    setLoading(true);
    setErrors([]);
    setSuccess('');

    try {
      const token = getToken();
      await valuationService.createProperty(formData, token);
      setSuccess('Valutazione inviata con successo! Un agente ti contatterà presto.');
      setFormData({
        titolo: '',
        descrizione: '',
        indirizzo: '',
        civico: '',
        citta: '',
        provincia: '',
        cap: '',
        quartiere: '',
        tipologia: 'APPARTAMENTO',
        metri_quadri: '',
        piano: '',
        anno_costruzione: new Date().getFullYear(),
        prezzo_richiesto: '',
        stato_immobile: 'BUONO',
        stato_annuncio: 'VALUTAZIONE',
      });
      setEstimatedPrice(null);
      setStep(1);
    } catch (error) {
      setErrors([error.message || 'Errore nell\'invio della valutazione']);
    } finally {
      setLoading(false);
    }
  };

  const calculateEstimate = () => {
    if (formData.metri_quadri && formData.tipologia && formData.stato_immobile) {
      const estimated = valuationService.estimatePrice(formData);
      setEstimatedPrice(estimated);
    }
  };

  return (
    <div className="valutazione-container">
      <div className="valutazione-header">
        <h1>Valuta il tuo Immobile</h1>
        <p>Ottieni una stima accurata del valore della tua proprietà</p>
      </div>

      {success && (
        <div className="alert alert-success">
          <p>{success}</p>
        </div>
      )}

      {errors.length > 0 && (
        <div className="alert alert-error">
          <p>Errori nella compilazione:</p>
          <ul>
            {errors.map((error, idx) => (
              <li key={idx}>{error}</li>
            ))}
          </ul>
        </div>
      )}

      <form className="valutazione-form" onSubmit={handleSubmit}>
        {/* STEP 1: DATI GENERALI */}
        {step === 1 && (
          <div className="form-section">
            <h2>Informazioni Generali</h2>

            <div className="form-group">
              <label>Titolo Annuncio</label>
              <input
                type="text"
                name="titolo"
                placeholder="es. Appartamento moderno centro città"
                value={formData.titolo}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label>Descrizione</label>
              <textarea
                name="descrizione"
                placeholder="Descrivi le caratteristiche principali dell'immobile"
                value={formData.descrizione}
                onChange={handleChange}
                rows="4"
              />
            </div>

            <h3>Localizzazione</h3>

            <div className="form-row">
              <div className="form-group">
                <label>Indirizzo</label>
                <input
                  type="text"
                  name="indirizzo"
                  placeholder="Via/Piazza"
                  value={formData.indirizzo}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-group">
                <label>Civico</label>
                <input
                  type="text"
                  name="civico"
                  placeholder="N°"
                  value={formData.civico}
                  onChange={handleChange}
                />
              </div>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label>Città</label>
                <input
                  type="text"
                  name="citta"
                  placeholder="es. Milano"
                  value={formData.citta}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-group">
                <label>Provincia</label>
                <input
                  type="text"
                  name="provincia"
                  placeholder="es. MI"
                  value={formData.provincia}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-group">
                <label>CAP</label>
                <input
                  type="text"
                  name="cap"
                  placeholder="es. 20100"
                  value={formData.cap}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <div className="form-group">
              <label>Quartiere (opzionale)</label>
              <input
                type="text"
                name="quartiere"
                placeholder="es. Centro Storico"
                value={formData.quartiere}
                onChange={handleChange}
              />
            </div>

            <div className="form-actions">
              <button
                type="button"
                className="btn btn-next"
                onClick={() => setStep(2)}
              >
                Continua
              </button>
            </div>
          </div>
        )}

        {/* STEP 2: CARATTERISTICHE */}
        {step === 2 && (
          <div className="form-section">
            <h2>Caratteristiche Immobile</h2>

            <div className="form-row">
              <div className="form-group">
                <label>Tipologia</label>
                <select
                  name="tipologia"
                  value={formData.tipologia}
                  onChange={handleChange}
                  required
                >
                  {propertyTypes.map((type) => (
                    <option key={type} value={type}>
                      {type.replace(/_/g, ' ')}
                    </option>
                  ))}
                </select>
              </div>

              <div className="form-group">
                <label>Stato Immobile</label>
                <select
                  name="stato_immobile"
                  value={formData.stato_immobile}
                  onChange={handleChange}
                  required
                >
                  {propertyStates.map((state) => (
                    <option key={state} value={state}>
                      {state.replace(/_/g, ' ')}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label>Metri Quadri</label>
                <input
                  type="number"
                  name="metri_quadri"
                  placeholder="es. 85.50"
                  step="0.1"
                  min="0"
                  value={formData.metri_quadri}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Piano</label>
                <input
                  type="text"
                  name="piano"
                  placeholder="es. 3"
                  value={formData.piano}
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>Anno Costruzione</label>
                <input
                  type="number"
                  name="anno_costruzione"
                  value={formData.anno_costruzione}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <div className="form-group">
              <label>Prezzo Richiesto (€)</label>
              <input
                type="number"
                name="prezzo_richiesto"
                placeholder="es. 250000"
                step="1000"
                min="0"
                value={formData.prezzo_richiesto}
                onChange={handleChange}
                required
              />
            </div>

            <div className="estimate-section">
              <h3>Stima Automatica</h3>
              <button
                type="button"
                className="btn btn-estimate"
                onClick={calculateEstimate}
              >
                Calcola Stima
              </button>
              {estimatedPrice && (
                <p className="estimated-price">
                  Stima: <strong>{valuationService.formatPrice(estimatedPrice)}</strong>
                </p>
              )}
            </div>

            <div className="form-actions">
              <button
                type="button"
                className="btn btn-back"
                onClick={() => setStep(1)}
              >
                Indietro
              </button>
              <button type="submit" className="btn btn-submit" disabled={loading}>
                {loading ? 'Invio in corso...' : 'Invia Valutazione'}
              </button>
            </div>
          </div>
        )}
      </form>
    </div>
  );
};

export default Valutazione;
