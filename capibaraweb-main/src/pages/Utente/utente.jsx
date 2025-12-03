import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./utente.css";
import utenteHomeImg from "../../assets/images/utente/casa.png";
import CalendarioImg from "../../assets/images/utente/calendario.png";
import DocumentoImg from "../../assets/images/utente/documento.png";
import agente4Img from "../../assets/images/chisiamo/Torino/agente4.webp";
import { getMe } from "../../api/authApi";

export default function Utente() {
  const navigate = useNavigate();
  const [selectedTime, setSelectedTime] = useState("09:30");
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  // Appuntamenti locali per questa pagina
  const [appuntamenti, setAppuntamenti] = useState([]);
  const [selectedDate, setSelectedDate] = useState(18);

  // Dati / stati copia della sezione appuntamenti dell'agente
  const [agenteAppuntamenti] = useState([]);
  const [agenteSelectedDate, setAgenteSelectedDate] = useState(18);
  const [agenteSelectedTime, setAgenteSelectedTime] = useState(null);

  // Recupera dati utente dal backend
  useEffect(() => {

    const fetchUserData = async () => {
      try {
        setLoading(true);
        const data = await getMe();
        setUserData(data);
        setError(null);
      } catch (err) {
        console.error("Errore nel recupero dati utente:", err);
        setError("Impossibile caricare i dati utente");
        // Se non autenticato, reindirizza al login
        if (err.message.includes("401")) {
          navigate("/login");
        }
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();

  }, [navigate]);

  // Gestione click "Vedi profilo" con dati statici
  const handleVediProfilo = () => {
    const almaCosi = {
      nome: "Alma Cosi",
      descrizione: "Esperta in immobili residenziali",
      esperienza: "+8 anni di esperienza",
      foto: agente4Img
    };
    navigate("/chi-siamo", { state: { selectedAgent: almaCosi } });
  };

  // Dati utente (ora dinamici dal backend o placeholder durante il caricamento)
  const user = userData ? {
    nome: userData.nome || "Utente",
    cognome: userData.cognome || "",
    sesso: "F", // Nota: il backend non fornisce questo campo, andrà gestito diversamente
    email: userData.email || "",
    telefono: userData.telefono || "",
    agent: {
      nome: "Alma Cosi",
      descrizione: "Esperta in immobili residenziali",
      esperienza: "+8 anni di esperienza",
      foto: agente4Img
    }
  } : {
    nome: "Caricamento...",
    sesso: "F",
    agent: {
      nome: "",
      descrizione: "",
      esperienza: "",
      foto: ""
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
    { numero: "12", label: "Proposte ricevute", subtitle: "+2 questa settimana" },
    { numero: "8", label: "Visite effettuate", subtitle: "+3 in programma" },
    { numero: "5", label: "Salvati nei preferiti", subtitle: "+2% match" }
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
  const agenteOrari = ["9:00", "10:00", "11:00", "12:00", "15:00", "16:00", "17:00", "18:00"];

  // Gestione stati di caricamento ed errore
  if (loading) {
    return (
      <div className="utente">
        <div style={{ textAlign: "center", padding: "2rem" }}>
          <p>Caricamento dati utente...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="utente">
        <div style={{ textAlign: "center", padding: "2rem", color: "red" }}>
          <p>{error}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="utente">
      {/* BENVENUTO */}
      <section className="benvenuto-section-utente">
        <h1>Benvenuto/a, {user.nome}!</h1>
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
            <button className="btn-primary-utente" onClick={handleVediProfilo}>Vedi profilo</button>
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
              <p className="stat-label-utente">{stat.label}</p>
              <h3 className="stat-numero-utente">{stat.numero}</h3>
              <p className="stat-subtitle-utente">{stat.subtitle}</p>
            </div>
          ))}
        </div>
      </section>

      {/* SEZIONE APPUNTAMENTI */}
      <div className="appuntamenti-section-utente">
          <h2>Visite programmate</h2>

          <div className="appuntamenti-container-utente">
            <div className="appuntamenti-lista-utente">
              {appuntamenti.length === 0 ? (
                <p className="vuoto-utente">Nessun appuntamento registrato.</p>
              ) : (
                appuntamenti.map((app, index) => (
                  <div key={index} className="appuntamento-item-utente">
                    <h3>{app.data}</h3>
                    <p>{app.descrizione}</p>
                  </div>
                ))
              )}
            </div>

            <div className="calendario-orario-utente">
              <div className="calendario-utente">
                <h3>Calendario</h3>

                <div className="calendario-grid-utente">
                  <div className="giorni-settimana-utente">
                    <span>L</span><span>M</span><span>M</span><span>G</span><span>V</span><span>S</span><span>D</span>
                  </div>

                  <div className="date-grid-utente">
                    {[...Array(30)].map((_, i) => {
                      const day = i + 1;
                      return (
                        <button
                          key={day}
                          className={`day-utente ${day === selectedDate ? "selected" : ""}`}
                          onClick={() => setSelectedDate(day)}
                        >
                          {day}
                        </button>
                      );
                    })}
                  </div>
                </div>
              </div>

              <div className="orario-utente">
                <h3>Orario</h3>

                <div className="orario-lista-utente">
                  {orari.map((ora, index) => (
                    <button
                      key={index}
                      className={`orario-slot-utente ${selectedTime === ora ? "selected" : ""}`}
                      onClick={() => setSelectedTime(ora)}
                    >
                      {ora}
                    </button>
                  ))}
                </div>
              </div>
            </div>

          </div>

          <button className="btn-arancione btn-gestisci-utente">Gestisci visite</button>
        </div>

        {/* SEZIONE ATTIVITA' RECENTI */}
      <section className="attivita-section-utente">
        <h2>Attività recenti</h2>
        <div className="attivita-list-utente">
          {attivita.map((att, idx) => (
            <div key={idx} className="attivita-card-utente">
              <div className="attivita-content-utente">
                <p className="attivita-data-utente">{att.data}</p>
                <h3 className="attivita-titolo-utente">{att.titolo}</h3>
              </div>
              <div className="attivita-icon-utente">
                {idx === 0 && <img src={utenteHomeImg} alt="Icona casa" className="attivita-img-utente" />}
                {idx === 1 && <img src={CalendarioImg} alt="Icona calenda" className="attivita-img-utente" />}
                {idx === 2 && <img src={DocumentoImg} alt="Attività" className="attivita-img-utente" />}
              </div>
            </div>
          ))}
        </div>
      </section>

    </div>
  );
}