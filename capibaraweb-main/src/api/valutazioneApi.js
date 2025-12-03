
const BASE_URL = "/api/valutazione";

/**
 * Effettua la valutazione automatica di un immobile
 * @param {Object} datiValutazione - Oggetto RichiestaValutazioneDTO
 * @returns {Promise<Object>} - Oggetto risposta valutazione
 */
export async function valutaImmobileAutomatico(datiValutazione) {
  try {
    const payload = { ...datiValutazione };

    // Normalizza tipologia (es: 'appartamento' -> 'APPARTAMENTO')
    if (payload.tipologia) payload.tipologia = String(payload.tipologia).toUpperCase();

    // Mappa i valori della condizione immobile (serviceType) ai token enum attesi dal backend
    // valori UI possibili: 'new', 'good', 'renovated', 'renovate'
    const statoMap = {
      new: "NUOVO",
      good: "BUONO",
      renovated: "OTTIMO",
      renovate: "DA_RISTRUTTURARE",
    };

    if (payload.statoImmobile) {
      const raw = String(payload.statoImmobile);
      const lower = raw.toLowerCase();
      if (statoMap[lower]) payload.statoImmobile = statoMap[lower];
      else payload.statoImmobile = raw.toUpperCase();
    }

    const response = await fetch(`${BASE_URL}/automatica`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(payload)
    });

    // Se la risposta NON è ok → gestione errori come lato backend
    if (!response.ok) {
      const errorBody = await response.json().catch(() => ({}));

      throw {
        timestamp: new Date().toISOString(),
        status: response.status,
        error: response.statusText,
        message: errorBody.message || "Errore durante la valutazione",
        path: "/api/valutazione/automatica"
      };
    }

    // Risposta OK
    return await response.json();

  } catch (err) {
    console.error("Errore valutazione:", err);
    throw err;
  }
}
