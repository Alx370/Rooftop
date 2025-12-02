import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import "./agente.css";

// API REALI
import { getMeAgente } from "../../api/utentiApi";
import { getClienti } from "../../api/clientiApi";
import { getImmobili } from "../../api/immobiliApi";
// import { getAppuntamenti } from "../../api/appuntamentiApi";  // se la creiamo

export default function Agente() {
  const navigate = useNavigate();

  const [loading, setLoading] = useState(true);
  const [me, setMe] = useState(null);

  const [clienti, setClienti] = useState([]);
  const [immobili, setImmobili] = useState([]);
  const [appuntamenti, setAppuntamenti] = useState([]); // quando mi dai API reali

  // 🔐 CONTROLLO AUTENTICAZIONE E RUOLO
  useEffect(() => {
    const loadData = async () => {
      try {
        const user = await getMeAgente();
        setMe(user);

        const ruolo = user?.authorities?.[0];

        if (ruolo !== "ROLE_AGENTE") {
          navigate("/utente");
          return;
        }

        // 📥 CARICO I CLIENTI DAL DATABASE
        const clientiRes = await getClienti();
        setClienti(clientiRes);

        // 📥 CARICO GLI IMMOBILI DAL DATABASE
        const immobiliRes = await getImmobili();
        setImmobili(immobiliRes);

        // 📥 CARICO GLI APPUNTAMENTI (quando hai API li agganciamo)
        // const appRes = await getAppuntamenti(user.id_utente);
        // setAppuntamenti(appRes);

      } catch (err) {
        console.error("Non autenticato → redirect:", err);
        navigate("/login");
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  if (loading) return <p className="loading">Caricamento...</p>;

  return (
    <div className="agente-page">
      <section className="hero-section">
        <h1>Benvenuto, {me?.nome}!</h1>

        {/* ============================
                AREA CLIENTI DINAMICA
            ============================ */}
        <div className="area-clienti">
          <h2>Area clienti:</h2>

          <table className="clienti-table">
            <thead>
              <tr>
                <th>Nome</th>
                <th>Tipologia</th>
                <th>Contatto</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {clienti.map((c) => (
                <tr key={c.id_cliente}>
                  <td>{c.nome} {c.cognome}</td>
                  <td>Cliente</td>
                  <td><a href={`tel:${c.telefono}`}>{c.telefono || "-"}</a></td>
                  <td className="azioni">
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

        {/* ============================
                ELENCO IMMOBILI DINAMICO
            ============================ */}
        <div className="elenco-immobili">
          <h2>Elenco immobili:</h2>
          <div className="immobili-grid">
            {immobili.map((im) => (
              <div key={im.id_immobile} className="immobile-card">

                <span
                  className={`badge ${
                    im.stato_annuncio === "VALUTAZIONE"
                      ? "badge-affitto"
                      : "badge-vendita"
                  }`}
                >
                  {im.stato_annuncio}
                </span>

                <h2>{im.titolo}</h2>
                <p className="indirizzo">
                  {im.indirizzo}, {im.citta} ({im.provincia})
                </p>

                <div className="valutazione-card">
                  {im.stato_annuncio}
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

        {/* ============================
                APPUNTAMENTI DINAMICI
             (placeholder finché non ho API)
            ============================ */}
        <div className="appuntamenti-section">
          <h2>Appuntamenti:</h2>

          <div className="appuntamenti-container">
            <div className="appuntamenti-lista">
              {appuntamenti.length === 0 && (
                <p>Nessun appuntamento programmato.</p>
              )}

              {appuntamenti.map((a) => (
                <div key={a.id_appuntamento} className="appuntamento-item">
                  <h3>{a.data_ora_inizio}</h3>
                  <p>{a.tipo} - Cliente {a.id_cliente}</p>
                </div>
              ))}
            </div>

            <button className="btn-arancione btn-gestisci">
              Gestisci visite
            </button>
          </div>
        </div>
      </section>
    </div>
  );
}
