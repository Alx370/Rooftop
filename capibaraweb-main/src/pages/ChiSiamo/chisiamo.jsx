import { useState } from "react";
import "./ChiSiamo.css";

export default function ChiSiamo() {
  const [openMenu, setOpenMenu] = useState(false);
  const [selectedProvince, setSelectedProvince] = useState("Torino");

  const provinces = ["Torino", "Cuneo", "Alessandria", "Asti", "Tutti"];

  const agentsData = [
    {
      id: 1,
      nome: "Marco Rossi",
      descrizione: "Trasformo le case nei sogni di chi le cerca.",
      provincia: "Torino",
      img: "../../src/assets/images/chisiamo/Torino/agente1.png",
    },
    {
      id: 2,
      nome: "Pino Bianco",
      descrizione: "Ho fiuto per le occasioni da batticuore.",
      provincia: "Torino",
      img: "../../src/assets/images/chisiamo/Torino/agente2.png",
    },
    {
      id: 3,
      nome: "Serena Fiocchi",
      descrizione: "Accompagno nella scelta della casa giusta.",
      provincia: "Torino",
      img: "../../src/assets/images/chisiamo/Torino/agente3.png",
    },
    {
      id: 4,
      nome: "Alma Cosi",
      descrizione: "Creo connessioni tra spazi e persone.",
      provincia: "Torino",
      img: "../../src/assets/images/chisiamo/Torino/agente4.png",
    },

    // Agenti di un'altra provincia (da finire di modificare)
    {
      id: 5,
      nome: "Nicola Verdi",
      descrizione: "Trasformo le case nei sogni di chi le cerca.",
      provincia: "Cuneo",
      img: "../../src/assets/images/chisiamo/Cuneo/",
    },
    {
      id: 6,
      nome: "Paolo Neri",
      descrizione: "Ho fiuto per le occasioni da batticuore.",
      provincia: "Cuneo",
      img: "../../src/assets/images/chisiamo/Cuneo/agente2.png",
    },
    {
      id: 7,
      nome: "Francesca Bianchi",
      descrizione: "Creo connessioni tra spazi e persone.",
      provincia: "Cuneo",
      img: "../../src/assets/images/chisiamo/Cuneo/agente3.png",
    },
    {
      id: 8,
      nome: "Tea Russo",
      descrizione: "Accompagno nella scelta della casa giusta.",
      provincia: "Cuneo",
      img: "../../src/assets/images/chisiamo/Cuneo/agente4.png",
    },
  ];

  const filteredAgents =
    selectedProvince === "Tutti"
      ? agentsData
      : agentsData.filter((a) => a.provincia === selectedProvince);

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
                <div className="arrow-card">›</div>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* -------------------------------- */}
      {/* MAPPA */}
      {/* -------------------------------- */}
      <section className="dove-trovarci-section">
        <h2>Dove trovarci:</h2>
        <p>Via Margherite 56 (TO) CAP 10059</p>

        <div className="map-box">
          <img src="/imgs/mappa.png" alt="Mappa Google" />
        </div>
      </section>

    </div>
  );
}
