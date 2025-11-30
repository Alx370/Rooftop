import React, { useEffect, useState } from "react";
import "./agente.css";

import { getClienti, getImmobili, getMeAgente } from "../../api/agenteApi";

const Agente = () => {
  const [user, setUser] = useState(null);
  const [clienti, setClienti] = useState([]);
  const [immobili, setImmobili] = useState([]);
  const [appuntamenti] = useState([]); // Da definire
  const [loading, setLoading] = useState(true);

  const [selectedDate, setSelectedDate] = useState(18);
  const [selectedTime, setSelectedTime] = useState(null);

  const orari = ["9:00", "10:00", "11:00", "12:00", "15:00", "16:00", "17:00", "18:00"];

  useEffect(() => {
    const loadData = async () => {
      try {
        // 1. Dati agente
        const me = await getMeAgente();
        setUser(me);

        // 2. Clienti
        const clientiData = await getClienti();
        setClienti(clientiData);

        // 3. Immobili
        const immobiliData = await getImmobili();
        setImmobili(immobiliData);

      } catch (err) {
        console.error("Errore caricamento dati agente:", err);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  if (loading) {
    return <div className="agente-loading">Caricamento dati...</div>;
  }

  return (
    <div className="agente-page">
      <section className="hero-section">
        
        <h1>Benvenut{user?.sesso === "F" ? "a" : "o"}, {user?.nome}!</h1>

        {/* AREA CLIENTI */}
        <div className="area-clienti">
          <h2>Area clienti:</h2>

          <table className="clienti-table">
            <thead>
              <tr>
                <th>Nome</th>
                <th>Tipologia</th>
                <th>Contatto</th>
                <th>Stato</th>
                <th></th>
              </tr>
            </thead>

            <tbody>
              {clienti.map((cliente) => (
                <tr key={cliente.id}>
                  <td>{cliente.nome} {cliente.cognome}</td>
                  <td>Cliente</td>
                  <td><a href={`tel:${cliente.telefono}`}>{cliente.telefono}</a></td>
                  <td>{cliente.stato || "—"}</td>
                  <td>
                    <button className="btn-icon">Elimina</button>
                    <button className="btn-icon">Modifica</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          <div className="bottoni-area">
            <button className="btn-arancione">Vedi tutti</button>
            <button className="btn-bianco">Aggiungi nuovo</button>
          </div>
        </div>

        {/* ELENCO IMMOBILI */}
        <div className="elenco-immobili">
          <h2>Elenco immobili:</h2>

          <div className="immobili-grid">
            {immobili.map((immobile) => (
              <div key={immobile.id} className="immobile-card">
                
                <span className="badge badge-affitto">
                  {immobile.tipologia}
                </span>

                <h2>{immobile.titolo}</h2>
                <p className="indirizzo">
                  {immobile.indirizzo}, {immobile.citta}
                </p>

                <div className="valutazione-card">
                  {immobile.stato_annuncio}
                </div>

                <div className="immobile-azioni">
                  <button className="btn-icon">Elimina</button>
                  <button className="btn-icon">Modifica</button>
                </div>
              </div>
            ))}
          </div>

          <div className="bottoni-area">
            <button className="btn-arancione">Vedi tutte</button>
            <button className="btn-bianco">Aggiungi nuova</button>
          </div>
        </div>

        {/* APPUNTAMENTI (placeholder finché non definiamo API reali) */}
        <div className="appuntamenti-section">
          <h2>Appuntamenti:</h2>

          <div className="appuntamenti-container">
            <div className="appuntamenti-lista">
              {appuntamenti.length === 0 ? (
                <p className="vuoto">Nessun appuntamento registrato.</p>
              ) : (
                appuntamenti.map((app, index) => (
                  <div key={index} className="appuntamento-item">
                    <h3>{app.data}</h3>
                    <p>{app.descrizione}</p>
                  </div>
                ))
              )}
            </div>

            <div className="calendario-orario">
              <div className="calendario">
                <h3>Calendario</h3>

                <div className="calendario-grid">
                  <div className="giorni-settimana">
                    <span>L</span><span>M</span><span>M</span><span>G</span><span>V</span><span>S</span><span>D</span>
                  </div>

                  <div className="date-grid">
                    {[...Array(30)].map((_, i) => {
                      const day = i + 1;
                      return (
                        <button
                          key={day}
                          className={`day ${day === selectedDate ? "selected" : ""}`}
                          onClick={() => setSelectedDate(day)}
                        >
                          {day}
                        </button>
                      );
                    })}
                  </div>
                </div>
              </div>

              <div className="orario">
                <h3>Orario</h3>

                <div className="orario-lista">
                  {orari.map((ora, index) => (
                    <button
                      key={index}
                      className={`orario-slot ${selectedTime === ora ? "selected" : ""}`}
                      onClick={() => setSelectedTime(ora)}
                    >
                      {ora}
                    </button>
                  ))}
                </div>
              </div>
            </div>

          </div>

          <button className="btn-arancione btn-gestisci">Gestisci visite</button>
        </div>

      </section>
    </div>
  );
};

export default Agente;
