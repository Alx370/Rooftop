import React, { useState } from "react";
import "./agente.css";

const Agente = () => {
  const [clienti] = useState([
    { nome: "Vanessa Olmi", tipologia: "Venditore", contatto: "+39 235 534 2344", stato: "Nuova richiesta" },
    { nome: "Giovanna Scudi", tipologia: "Venditore", contatto: "+39 565 584 2322", stato: "Contatto avviato" },
    { nome: "Andrea Passi", tipologia: "Locatore", contatto: "+39 665 534 2316", stato: "Proposta ricevuta" },
    { nome: "Fausto Allietta", tipologia: "Venditore", contatto: "+39 489 267 4316", stato: "Contratto firmato" }
  ]);

  const [immobili] = useState([
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

  const [appuntamenti] = useState([
    { data: "12 nov - 10:00", descrizione: "Call Cliente - Paola Cimi" },
    { data: "18 nov - 14:30", descrizione: "Visita immobile - Via Napoleone 2" },
    { data: "21 nov - 16:30", descrizione: "Proposta cliente - Corso Unione 4" }
  ]);

  const [selectedDate, setSelectedDate] = useState(18);
  const [selectedTime, setSelectedTime] = useState(null);

  const orari = ["9:00", "10:00", "11:00", "12:00", "15:00", "16:00", "17:00", "18:00"];


{/* Inizio HTML */}
  return (
    <div className="agente-page"> 
        <section className="hero-section">
            <h1>Benvenuta, Alma!</h1>

            {/* Area clienti */}
            <div className="area-clienti">
                <h2>Area clienti:</h2>
                <table className="clienti-table">
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
                                <td>{cliente.nome}</td>
                                <td>{cliente.tipologia}</td>
                                <td><a href={`tel:${cliente.contatto}`}>{cliente.contatto}</a></td>
                                <td className={cliente.stato === "Nuova richiesta" ? "stato-nuovo" : ""}>
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
                            <div className="valutazione-card">
                                {immobile.valutazione}
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
}

export default Agente;