import { useState } from "react";
import "./ChiSiamo.css";

export default function ChiSiamo() {
  const [openMenu, setOpenMenu] = useState(false);
  const [selectedProvince, setSelectedProvince] = useState("Torino");
  const [selectedAgent, setSelectedAgent] = useState(null);

  const provinces = ["Torino", "Cuneo", "Alessandria", "Asti", "Tutti"];

  const agentsData = [
    {
      id: 1,
      nome: "Marco Rossi",
      descrizione: "Trasformo le case nei sogni di chi le cerca.",
      presentazione:"Da oltre 15 anni accompagno persone nella scelta della casa giusta. Più di 289 clienti soddisfatti mi hanno scelto per la mia trasparenza, competenza e attenzione ai dettagli. Il mio obiettivo? Far sentire ogni cliente davvero a casa.",
      provincia: "Torino",
      tel: "+391234567890",
      mail: "mario.rossi@immobiliaris.com",
      img: "../../src/assets/images/chisiamo/Torino/agente1.png",
    },
    {
      id: 2,
      nome: "Pino Bianco",
      descrizione: "Ho fiuto per le occasioni da batticuore.",
      presentazione:"Credo che trovare casa significhi trovare il proprio equilibrio quotidiano ti accompagno con professionalità trasparenza e impegno perché ogni scelta merita attenzione e rispetto.",
      provincia: "Torino",
      tel: "+391234567890",
      mail: "pino.bianco@immobiliaris.com",
      img: "../../src/assets/images/chisiamo/Torino/agente2.png",
    },
    {
      id: 3,
      nome: "Serena Fiocchi",
      descrizione: "Accompagno nella scelta della casa giusta.",
      presentazione: "Non solo vendite: creo connessioni. Con oltre 100 clienti soddisfatti e 10 anni di esperienza, ti accompagno nella scelta della casa giusta, con ascolto, trasparenza e passione.",
      provincia: "Torino",
      tel: "+391234567890",
      mail: "serena.fiocchi@immobiliaris.com",
      img: "../../src/assets/images/chisiamo/Torino/agente3.png",
    },
    {
      id: 4,
      nome: "Alma Cosi",
      descrizione: "Creo connessioni tra spazi e persone.",
      presentazione: "Ti accompagnerò nella scelta della casa giusta con ascolto, empatia e visione. Ogni spazio ha un potenziale: il mio lavoro è aiutarti a riconoscerlo.",
      provincia: "Torino",
      tel: "+391234567890",
      mail: "alma.cosi@immobiliaris.com",
      img: "../../src/assets/images/chisiamo/Torino/agente4.png",
    },

    // Agenti di un'altra provincia (da finire di modificare)
    {
    id: 5,
    nome: "Luca Ferrero",
    descrizione: "Trasformo il desiderio di casa in un percorso sereno.",
    presentazione: "Ti guiderò con dedizione e ascolto per aiutarti a riconoscere il valore di ogni ambiente.",
    provincia: "Cuneo",
    tel: "+393441112233",
    mail: "luca.ferrero@immobiliaris.com",
    img: "../../src/assets/images/chisiamo/Torino/agente1.png",
  },
  {
    id: 6,
    nome: "Sara Montesi",
    descrizione: "Credo che ogni casa racconti una storia unica.",
    presentazione: "Ti accompagnerò con sensibilità e chiarezza per trovare lo spazio che rispecchia davvero chi sei.",
    provincia: "Cuneo",
    tel: "+393478889900",
    mail: "sara.montesi@immobiliaris.com",
    img: "../../src/assets/images/chisiamo/Torino/agente1.png",
  },
  {
    id: 7,
    nome: "Marco Galletto",
    descrizione: "Ogni scelta immobiliare merita cura e attenzione.",
    presentazione: "Sarò al tuo fianco con competenza e trasparenza per aiutarti a immaginare e costruire il tuo futuro.",
    provincia: "Cuneo",
    tel: "+393493225588",
    mail: "marco.galletto@immobiliaris.com",
    img: "../../src/assets/images/chisiamo/Torino/agente1.png",
  },
  {
    id: 8,
    nome: "Elena Bessone",
    descrizione: "Connetto persone e luoghi con equilibrio e visione.",
    presentazione: "Ti accompagnerò con professionalità e ascolto per trovare la casa che possa diventare il tuo punto di partenza.",
    provincia: "Cuneo",
    tel: "+393402556677",
    mail: "elena.bessone@immobiliaris.com",
    img: "../../src/assets/images/chisiamo/Torino/agente1.png",
  }
  ];

  const filteredAgents =
    selectedProvince === "Tutti"
      ? agentsData
      : agentsData.filter((a) => a.provincia === selectedProvince);

  const phoneNumber = "+391234567890";
  const emailAddress = "info@immobiliaris.com";


  {/* -------------------------------- */ }
  {/* INIZIO HTML */ }
  {/* -------------------------------- */ }

  return (
    <div className="chisiamo">

      {/* -------------------------------- */}
      {/* HERO */}
      {/* -------------------------------- */}
      <section className="hero">
        <div className="hero-content">

          <div className="hero-text">
            <h1>Chi siamo</h1>
            <p>
              <strong>Immobiliaris</strong> è l'agenzia immobiliare di riferimento a{" "}
              <strong>Torino</strong>, specializzata nella vendita di case con{" "}
              <strong>consulenza su misura</strong> e{" "}
              <strong>profonda conoscenza</strong> del territorio.
            </p>
          </div>

          <div className="hero-image">
            <img
              src="src/pages/Home/img/PalazzoModerno.png"
              alt="Palazzo moderno"
            />
          </div>

        </div>
      </section>

      <section className="hero-title">
        <h1>Scopri tutti i nostri agenti</h1>
        <p>
          <strong>Immobiliaris</strong> è l'agenzia immobiliare di riferimento a{" "}
          <strong>Torino</strong>, specializzata nella vendita di case con{" "}
          <strong>consulenza su misura</strong> e{" "}
          <strong>profonda conoscenza</strong> del territorio.
        </p>
      </section>

      {/* -------------------------------- */}
      {/* FILTRO PROVINCIA */}
      {/* -------------------------------- */}
      <section className="filtro-provincia-section">
        <button
          className="provincia-btn"
          onClick={() => setOpenMenu(!openMenu)}
        >
          Scegli la tua provincia
          <span className="arrow">▾</span>
        </button>

        {openMenu && (
          <div className="modal-overlay" onClick={() => setOpenMenu(false)}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
              <h3>Seleziona la tua provincia</h3>
              {provinces.map((prov) => (
                <div
                  key={prov}
                  className="dropdown-item"
                  onClick={() => {
                    setSelectedProvince(prov);
                    setOpenMenu(false);
                  }}
                >
                  {prov}
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Titolo provincia selezionata */}
        <h2 className="provincia-selezionata-title">
          {selectedProvince === "Tutti" 
            ? "Tutti gli agenti" 
            : `Agenti di ${selectedProvince}`}
        </h2>
      </section>



      {/* -------------------------------- */}
      {/* CARD AGENTI */}
      {/* -------------------------------- */}
      <section className="card-agenti-section">
        <div className="cards-container">
          {filteredAgents.map((agent) => (
            <div className="card-agente" key={agent.id}>
              <h3>{agent.nome}</h3>
              <p className="descr">{agent.descrizione}</p>

              <div className="foto-box">
                <img src={agent.img} alt={agent.nome} />
                <div 
                  className="arrow-card"
                  onClick={() => setSelectedAgent(agent)}
                  style={{ cursor: 'pointer' }}
                >
                  ›
                </div>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* -------------------------------- */}
      {/* MODALE DI DETTAGLIO AGENTE */}
      {/* -------------------------------- */}
      {selectedAgent && (
        <div className="agent-modal-overlay" onClick={() => setSelectedAgent(null)}>
          <div className="agent-modal-content" onClick={(e) => e.stopPropagation()}>
            <button 
              className="close-modal-btn" 
              onClick={() => setSelectedAgent(null)}
            >
              ✕
            </button>
            
            <div className="modal-left">
              <img src={selectedAgent.img} alt={selectedAgent.nome} />
            </div>
            
            <div className="modal-right">
              <h2>{selectedAgent.nome}</h2>
              <p className="modal-provincia">{selectedAgent.provincia}</p>
              <p className="modal-presentazione">{selectedAgent.presentazione}</p>

              <div className="modal-contact">
                <h3><strong>Contattami per una consulenza</strong></h3>
                <p>Telefono: <a href={`tel:${selectedAgent.tel}`}>{selectedAgent.tel}</a></p>
                <p>Email: <a href={`mailTo:${selectedAgent.mail}`}>{selectedAgent.mail}</a></p>
              </div>
            </div>
          </div>
        </div>
      )}


      {/* -------------------------------- */}
      {/* MAPPA */}
      {/* -------------------------------- */}
      <section className="dove-trovarci-section">
        <h2>Dove trovarci:</h2>
        <p>Via Margherite 56 (TO) CAP 10059</p>

        <div className="map-box">
          <img src="src/assets/images/chisiamo/mappa.png" alt="Mappa Google" />
        </div>
      </section>


      {/* -------------------------------- */}
      {/* BOTTONI RECAPITI */}
      {/* -------------------------------- */}
      <section className="bottone-recapiti-section">
        <button onClick={() => window.location.href = `tel:${phoneNumber}`} className="recapiti-btn" > +39 123 456 7890 </button>
      </section>

      <section className="bottone-recapiti-section">
        <button onClick={() => window.location.href = `mailTo:${emailAddress}`} className="recapiti-btn" > info@immobiliaris.com </button>
      </section>


      {/* -------------------------------- */}
      {/* LA NOSTRA STORIA */}
      {/* -------------------------------- */}
      <section className="storia-container">

        <div className="storia-section">
          <h2 className="title-storia">La nostra storia:</h2>

          <p>
            Fondata nel <strong>cuore di Torino</strong> nel 2005, la nostra agenzia immobiliare nasce <br />
            dalla <strong>passione</strong> per il mercato immobiliare residenziale e dalla volontà di  <br />
            offrire un servizio <strong>trasparente, professionale e su misura.</strong>
          </p>

          <p>
            Oggi siamo un <strong>punto di riferimento</strong> per chi cerca appartamenti in vendita a Torino, <br />
            immobili di pregio, case indipendenti e <strong>investimenti immobiliari.</strong>
          </p>
        </div>

        <div className="map-box-storia">
          <img src="src/assets/images/chisiamo/panorama.png" alt="Foto Panorama Torino" />
        </div>

      </section>


      {/* -------------------------------- */}
      {/* I DATI */}
      {/* -------------------------------- */}
      <section className="dati-section">
        <h1>Cosa abbiamo costruito insieme</h1>
        <p>
          In oltre 15 anni di attività, abbiamo aiutato centinaia di clienti a vendere casa, 
          valutare immobili e trovare la soluzione abitativa ideale, costruendo relazioni basate 
          sulla fiducia e sull'ascolto.
        </p>

        <div className="dati-cards-container">
          <div className="dati-card">
            <h3 className="title-card">+462</h3>
            <p>Case Vendute</p>
          </div>

          <div className="dati-card">
            <h3 className="title-card">+56</h3>
            <p>Agenti a disposizione</p>
          </div>

          <div className="dati-card">
            <h3 className="title-card">+513</h3>
            <p>Consulenze Fatte</p>
          </div>
        </div>
        
      </section>
      
    </div>
  );
}
