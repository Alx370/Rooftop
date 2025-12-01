import { useState } from "react";
import "./utente.css";

export default function Utente() {
  const [selectedTime, setSelectedTime] = useState("09:30");

  // Dati utente
  const user = {
    nome: "Elisa",
    sesso: "F",
    agent: {
      nome: "Alma Cosi",
      descrizione: "Esperta in immobili residenziali",
      esperienza: "+8 anni di esperienza",
      foto: "../../src/assets/images/chisiamo/Torino/agente4.png"
    }
  };

  // Timeline steps
  const steps = [
    { nome: "Consulenza", completed: true },
    { nome: "Ricerca attiva", completed: true },
    { nome: "Visita", completed: true },
    { nome: "Trattativa", completed: false },
    { nome: "Chiusura", completed: false }
  ];

  // Panoramica profilo
  const stats = [
    { icon: "📤", numero: "12", label: "Proposte ricevute", subtitle: "+2 questa settimana" },
    { icon: "📅", numero: "8", label: "Visite effettuate", subtitle: "+3 in programma" },
    { icon: "❤️", numero: "5", label: "Salvati nei preferiti", subtitle: "+2% match" }
  ];

  // Visite programmate
  const visite = [
    {
      id: 1,
      data: "12 nov",
      ora: "10:00",
      tipo: "Proposta accettata",
      indirizzo: "Via Cibario 10",
      status: "accepted"
    },
    {
      id: 2,
      data: "18 nov",
      ora: "14:30",
      tipo: "Proposta accettata",
      indirizzo: "Via Napoleone 2",
      status: "accepted"
    },
    {
      id: 3,
      data: "21 nov",
      ora: "16:30",
      tipo: "Proposta accettata",
      indirizzo: "Corso Unione 4",
      status: "accepted"
    },
    {
      id: 4,
      data: "19 nov",
      ora: "9:30",
      tipo: "Proposta in sospeso",
      indirizzo: "Via Roma 20/2",
      status: "pending"
    },
    {
      id: 5,
      data: "28 nov",
      ora: "11:00",
      tipo: "Proposta in sospeso",
      indirizzo: "Via G. Cesare 21",
      status: "pending"
    }
  ];

  // Attività recenti
  const attivita = [
    { data: "07 novembre", titolo: "Nuova proposta condivisa dal tuo agente", icon: "🏠" },
    { data: "05 novembre", titolo: "Visita effettuata - Appartamento Via Roma", icon: "📋" },
    { data: "02 novembre", titolo: "Documenti caricati correttamente", icon: "📄" }
  ];

  const orari = ["09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30"];

  return (
    <div className="utente">
      {/* BENVENUTO */}
      <section className="benvenuto-section-utente">
        <h1>Benvenut{user.sesso === "F" ? "a" : "o"}, {user.nome}!</h1>
        <p>Tutto ciò che ti serve, raccolto in un unico spazio.</p>
      </section>

      {/* CARD AGENTE */}
      <section className="agente-section-utente">
        <div className="agente-card-utente">
          <img src={user.agent.foto} alt={user.agent.nome} className="agente-foto-utente" />
          <div className="agente-info-utente">
            <p className="agente-label-utente">Il tuo agente personale</p>
            <h2 className="agente-nome-utente">{user.agent.nome}</h2>
            <p className="agente-descr-utente">{user.agent.descrizione}</p>
            <p className="agente-exp-utente">{user.agent.esperienza}</p>
          </div>
          <div className="agente-buttons-utente">
            <button className="btn-secondary-utente">Vedi profilo</button>
            <button className="btn-primary-utente">Contatta</button>
          </div>
        </div>
      </section>

      {/* PERCORSO ATTUALE */}
      <section className="percorso-section-utente">
        <h2>Il tuo percorso attuale</h2>
        <div className="timeline-utente">
          {steps.map((step, idx) => (
            <div key={idx} className="timeline-item-utente">
              <div className={`timeline-dot-utente ${step.completed ? 'completed' : ''}`}>
                {step.completed && '✓'}
              </div>
              {idx < steps.length - 1 && <div className={`timeline-line-utente ${step.completed ? 'completed' : ''}`}></div>}
              <p className="timeline-label-utente">{step.nome}</p>
            </div>
          ))}
        </div>
      </section>

      {/* PANORAMICA PROFILO */}
      <section className="panoramica-section-utente">
        <h2>Panoramica del tuo profilo</h2>
        <div className="stats-grid-utente">
          {stats.map((stat, idx) => (
            <div key={idx} className="stat-card-utente">
              <p className="stat-icon-utente">{stat.icon}</p>
              <h3 className="stat-numero-utente">{stat.numero}</h3>
              <p className="stat-label-utente">{stat.label}</p>
              <p className="stat-subtitle-utente">{stat.subtitle}</p>
            </div>
          ))}
        </div>
      </section>

      {/* VISITE PROGRAMMATE */}
      <section className="visite-section-utente">
        <h2>Visite programmate</h2>
        <div className="visite-container-utente">
          <div className="visite-list-utente">
            {visite.map((visita) => (
              <div key={visita.id} className={`visita-item-utente ${visita.status}`}>
                <div className="visita-left-utente">
                  <span className="visita-data-utente">{visita.data}</span>
                  <span className="visita-ora-utente">{visita.ora}</span>
                </div>
                <div className="visita-details-utente">
                  <p className="visita-tipo-utente">{visita.tipo}</p>
                  <p className="visita-indirizzo-utente">{visita.indirizzo}</p>
                </div>
              </div>
            ))}
          </div>

          <div className="calendario-orari-utente">
            <div className="calendario-header-utente">Calendario</div>
            <div className="calendario-grid-utente">
              {['L', 'M', 'M', 'G', 'V', 'S', 'D'].map((day, idx) => (
                <div key={idx} className="calendario-day-header-utente">{day}</div>
              ))}
              {Array.from({ length: 30 }, (_, i) => {
                const day = i + 1;
                const isHighlighted = [12, 18, 21, 19, 28].includes(day);
                return (
                  <div key={day} className={`calendario-day-utente ${isHighlighted ? 'highlighted' : ''}`}>
                    {day}
                  </div>
                );
              })}
            </div>

            <div className="orari-container-utente">
              <p className="orari-title-utente">Orario</p>
              <div className="orari-grid-utente">
                {orari.map((ora) => (
                  <div
                    key={ora}
                    className={`orario-slot-utente ${selectedTime === ora ? 'selected' : ''}`}
                    onClick={() => setSelectedTime(ora)}
                  >
                    {ora}
                  </div>
                ))}
              </div>
            </div>

            <button className="btn-primary-utente btn-full-utente">Gestisci visite</button>
          </div>
        </div>
      </section>

      {/* ATTIVITÀ RECENTI */}
      <section className="attivita-section-utente">
        <h2>Attività recenti</h2>
        <div className="attivita-list-utente">
          {attivita.map((att, idx) => (
            <div key={idx} className="attivita-item-utente">
              <div className="attivita-icon-utente">{att.icon}</div>
              <div className="attivita-content-utente">
                <p className="attivita-data-utente">{att.data}</p>
                <p className="attivita-titolo-utente">{att.titolo}</p>
              </div>
            </div>
          ))}
        </div>
        <button className="btn-link-utente">Vedi tutte le attività</button>
      </section>
    </div>
  );
}