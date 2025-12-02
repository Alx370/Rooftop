import React, { useState } from "react";

import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./agente.css";

const Agente = () => {

    const navigate = useNavigate();

    useEffect(() => {
    const logged = localStorage.getItem("adminLogged");

    if (!logged) {
      navigate("/login-admin"); // reindirizza chi non è loggato
    }
  }, []);

  const [clienti] = useState([
    { nome: "Vanessa Olmi", tipologia: "Venditore", contatto: "+39 235 534 2344", stato: "Nuova richiesta" },
    { nome: "Giovanna Scudi", tipologia: "Venditore", contatto: "+39 565 584 2322", stato: "Contatto avviato" },
    { nome: "Andrea Passi", tipologia: "Locatore", contatto: "+39 665 534 2316", stato: "Proposta ricevuta" },
    { nome: "Fausto Allietta", tipologia: "Venditore", contatto: "+39 489 267 4316", stato: "Contratto firmato" }
  ]);

  const [immobili, setImmobili] = useState([
    { 
      id: 1,
      nome: "Casa Sfarfalli",
      stato: "Affitto",
    //   immagine: "/src/assets/images/casa1.jpg",
      indirizzo: "Torino, Via ferrucci 56",
      valutazione: "In valutazione"
    },
    { 
      id: 2,
      nome: "Casa Viti",
      stato: "Vendita",
    //   immagine: "/src/assets/images/casa2.jpg",
      indirizzo: "Alba, Via giovanni VI 21",
      valutazione: "Venduto"
    },
    { 
      id: 3,
      nome: "Casa Monti",
      stato: "Vendita",
    //   immagine: "/src/assets/images/casa3.jpg",
      indirizzo: "Meana (TO), Via fiori 6",
      valutazione: "Trattativa in corso"
    }
  ]);

  const statiValutazione = ["In valutazione", "Venduto", "Trattativa in corso", "Valutazione completata", "Non disponibile"];

  // Funzione per cambiare lo stato della valutazione
  const handleChangeValutazione = (immobileId, nuovoStato) => {
    setImmobili(immobili.map(immobile => {
      if (immobile.id === immobileId) {
        // Determina il nuovo stato (Affitto/Vendita) in base alla valutazione
        let nuovoStatoImmobile = immobile.stato;
        if (nuovoStato === "Venduto") {
          nuovoStatoImmobile = "Vendita";
        } else if (nuovoStato === "In valutazione" || nuovoStato === "Trattativa in corso") {
          // Mantieni lo stato attuale o imposta un default
          nuovoStatoImmobile = immobile.stato;
        }
        
        return { ...immobile, valutazione: nuovoStato, stato: nuovoStatoImmobile };
      }
      return immobile;
    }));
  };

  const [appuntamenti] = useState([
    { data: "12 nov - 10:00", descrizione: "Call Cliente - Paola Cimi" },
    { data: "18 nov - 14:30", descrizione: "Visita immobile - Via Napoleone 2" },
    { data: "21 nov - 16:30", descrizione: "Proposta cliente - Corso Unione 4" }
  ]);

  const [selectedDate, setSelectedDate] = useState(18);
  const [selectedTime, setSelectedTime] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [clientEmail, setClientEmail] = useState("");
  const [emailError, setEmailError] = useState("");

  const orari = ["9:00", "10:00", "11:00", "12:00", "15:00", "16:00", "17:00", "18:00"];

  // Funzione per gestire la selezione di ora
  const handleTimeSelection = (ora) => {
    setSelectedTime(ora);
    if (selectedDate && ora) {
      setShowModal(true);
    }
  };

  // Funzione per validare l'email
  const validateEmail = (email) => {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
  };

  // Funzione per inviare la richiesta di appuntamento
  const handleSendAppointment = () => {
    if (!clientEmail) {
      setEmailError("Inserisci un'email");
      return;
    }
    if (!validateEmail(clientEmail)) {
      setEmailError("Inserisci un'email valida");
      return;
    }

    // TODO: Qui potrai fare la chiamata API quando il backend sarà pronto
    console.log("Invio richiesta appuntamento:", {
      data: `${selectedDate} novembre`,
      ora: selectedTime,
      emailCliente: clientEmail
    });

    alert(`Richiesta di appuntamento inviata a ${clientEmail} per il ${selectedDate} novembre alle ${selectedTime}`);

    // Reset e chiudi modale
    setShowModal(false);
    setClientEmail("");
    setEmailError("");
    setSelectedTime(null);
  };

  // Funzione per chiudere la modale
  const handleCloseModal = () => {
    setShowModal(false);
    setClientEmail("");
    setEmailError("");
    setSelectedTime(null);
  };


{/* Inizio HTML */}
  return (
    <div className="agente-page"> 
        <section className="hero-section-agente">
            <h1>Benvenuta, Alma!</h1>

            {/* Area clienti */}
            <div className="area-clienti-agente">
                <h2>Area clienti:</h2>
                <table className="clienti-table-agente">
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Tipologia</th>
                            <th>Contatto</th>
                            <th>Stato trattativa</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {clienti.map((cliente, index) => (
                            <tr key={index}>
                                <td data-label="Nome">{cliente.nome}</td>
                                <td data-label="Tipologia">{cliente.tipologia}</td>
                                <td data-label="Contatto"><a href={`tel:${cliente.contatto}`}>{cliente.contatto}</a></td>
                                <td data-label="Stato trattativa" className={cliente.stato === "Nuova richiesta" ? "stato-nuovo" : ""}>
                                    {cliente.stato}
                                </td>
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

            {/* Elenco immobili */}
            <div className="elenco-immobili">
                <h2>Elenco immobili:</h2>
                <div className="immobili-grid">
                    {immobili.map((immobile) => (
                        <div key={immobile.id} className="immobile-card">
                            <span className={`badge ${immobile.stato === "Affitto" ? "badge-affitto" : "badge-vendita"}`}>{immobile.stato}</span>
                            <h2>{immobile.nome}</h2>
                            <p className="indirizzo">{immobile.indirizzo}</p>
                            <select 
                                className="valutazione-select"
                                value={immobile.valutazione}
                                onChange={(e) => handleChangeValutazione(immobile.id, e.target.value)}
                            >
                                {statiValutazione.map((stato, index) => (
                                    <option key={index} value={stato}>{stato}</option>
                                ))}
                            </select>
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

            {/* Appuntamenti */}
            <div className="appuntamenti-section">
                <h2>Appuntamenti:</h2>
                <div className="appuntamenti-container">
                    <div className="appuntamenti-lista">
                        {appuntamenti.map((app, index) => (
                            <div key={index} className="appuntamento-item">
                                <h3>{app.data}</h3>
                                <p>{app.descrizione}</p>    
                            </div>
                        ))}
                    </div>
                    
                    <div className="calendario-orario">
                        <div className="calendario">
                            <h3>Calendario</h3>
                            <div className="calendario-grid">
                                <div className="giorni-settimana">
                                    <span>L</span><span>M</span><span>M</span><span>G</span><span>V</span><span>S</span><span>D</span>
                                </div>
                                <div className="date-grid">
                                    {[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30].map(day => (
                                        <button 
                                            key={day}
                                            className={`day ${day === selectedDate ? 'selected' : ''} ${[12,18,21].includes(day) ? 'has-event' : ''}`}
                                            onClick={() => setSelectedDate(day)}
                                        >
                                            {day}
                                        </button>
                                    ))}
                                </div>
                            </div>
                        </div>
                        
                        <div className="orario">
                            <h3>Orario</h3>
                            <div className="orario-lista">
                                {orari.map((ora, index) => (
                                    <button 
                                        key={index}
                                        className={`orario-slot ${selectedTime === ora ? 'selected' : ''}`}
                                        onClick={() => handleTimeSelection(ora)}
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

        {/* Modale per inserimento email */}
        {showModal && (
            <div className="modal-overlay" onClick={handleCloseModal}>
                <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                    <button className="modal-close" onClick={handleCloseModal}>&times;</button>
                    <h2>Richiesta Appuntamento</h2>
                    <p className="modal-info">
                        <strong>Data:</strong> {selectedDate} novembre<br/>
                        <strong>Orario:</strong> {selectedTime}
                    </p>
                    <div className="modal-form">
                        <label htmlFor="client-email">Email del cliente:</label>
                        <input 
                            type="email" 
                            id="client-email"
                            placeholder="cliente@esempio.com"
                            value={clientEmail}
                            onChange={(e) => {
                                setClientEmail(e.target.value);
                                setEmailError("");
                            }}
                            className={emailError ? "input-error" : ""}
                        />
                        {emailError && <span className="error-message">{emailError}</span>}
                    </div>
                    <div className="modal-actions">
                        <button className="btn-bianco" onClick={handleCloseModal}>Annulla</button>
                        <button className="btn-arancione" onClick={handleSendAppointment}>Invia Richiesta</button>
                    </div>
                </div>
            </div>
        )}
    </div>
  );
}

export default Agente;
