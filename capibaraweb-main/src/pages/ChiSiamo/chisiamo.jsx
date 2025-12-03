import { useState, useEffect } from "react";
import "./chisiamo.css";
import { setPageMeta, setStructuredData } from "../../utils/seo";

// Helper to resolve assets via Vite. Use like: asset('images/chisiamo/Torino/agente1.webp')
const asset = (p) => new URL(`/src/assets/${p}`, import.meta.url).href;

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
      img: asset('images/chisiamo/Torino/agente1.webp'),
    },
    {
      id: 2,
      nome: "Pino Bianco",
      descrizione: "Ho fiuto per le occasioni da batticuore.",
      presentazione:"Credo che trovare casa significhi trovare il proprio equilibrio quotidiano ti accompagno con professionalità trasparenza e impegno perché ogni scelta merita attenzione e rispetto.",
      provincia: "Torino",
      tel: "+391234567890",
      mail: "pino.bianco@immobiliaris.com",
      img: asset('images/chisiamo/Torino/agente2.webp'),
    },
    {
      id: 3,
      nome: "Serena Fiocchi",
      descrizione: "Accompagno nella scelta della casa giusta.",
      presentazione: "Non solo vendite: creo connessioni. Con oltre 100 clienti soddisfatti e 10 anni di esperienza, ti accompagno nella scelta della casa giusta, con ascolto, trasparenza e passione.",
      provincia: "Torino",
      tel: "+391234567890",
      mail: "serena.fiocchi@immobiliaris.com",
      img: asset('images/chisiamo/Torino/agente3.webp'),
    },
    {
      id: 4,
      nome: "Alma Cosi",
      descrizione: "Creo connessioni tra spazi e persone.",
      presentazione: "Ti accompagnerò nella scelta della casa giusta con ascolto, empatia e visione. Ogni spazio ha un potenziale: il mio lavoro è aiutarti a riconoscerlo.",
      provincia: "Torino",
      tel: "+391234567890",
      mail: "alma.cosi@immobiliaris.com",
      img: asset('images/chisiamo/Torino/agente4.webp'),
    },

    // Agenti di Cuneo
    {
    id: 5,
    nome: "Luca Ferrero",
    descrizione: "Trasformo il desiderio di casa in un percorso sereno.",
    presentazione: "Ti guiderò con dedizione e ascolto per aiutarti a riconoscere il valore di ogni ambiente.",
    provincia: "Cuneo",
    tel: "+393441112233",
    mail: "luca.ferrero@immobiliaris.com",
    img: asset('images/chisiamo/Torino/agente1.webp'),
  },
  {
    id: 6,
    nome: "Sara Montesi",
    descrizione: "Credo che ogni casa racconti una storia unica.",
    presentazione: "Ti accompagnerò con sensibilità e chiarezza per trovare lo spazio che rispecchia davvero chi sei.",
    provincia: "Cuneo",
    tel: "+393478889900",
    mail: "sara.montesi@immobiliaris.com",
    img: asset('images/chisiamo/Torino/agente1.webp'),
  },
  {
    id: 7,
    nome: "Marco Galletto",
    descrizione: "Ogni scelta immobiliare merita cura e attenzione.",
    presentazione: "Sarò al tuo fianco con competenza e trasparenza per aiutarti a immaginare e costruire il tuo futuro.",
    provincia: "Cuneo",
    tel: "+393493225588",
    mail: "marco.galletto@immobiliaris.com",
    img: asset('images/chisiamo/Torino/agente1.webp'),
  },
  {
    id: 8,
    nome: "Elena Bessone",
    descrizione: "Connetto persone e luoghi con equilibrio e visione.",
    presentazione: "Ti accompagnerò con professionalità e ascolto per trovare la casa che possa diventare il tuo punto di partenza.",
    provincia: "Cuneo",
    tel: "+393402556677",
    mail: "elena.bessone@immobiliaris.com",
    img: asset('images/chisiamo/Torino/agente1.webp'),
  },

  // Agenti di Alessandria
{
  id: 9,
  nome: "Giulia Roversi",
  descrizione: "Creo percorsi immobiliari basati su fiducia e trasparenza.",
  presentazione: "Ti accompagnerò con attenzione e competenza per trovare l'ambiente che possa davvero migliorare il tuo quotidiano.",
  provincia: "Alessandria",
  tel: "+393401234567",
  mail: "giulia.roversi@immobiliaris.com",
  img: asset('images/chisiamo/Torino/agente1.webp'),
},
{
  id: 10,
  nome: "Matteo Cavazza",
  descrizione: "Rendo ogni trattativa un'esperienza semplice e positiva.",
  presentazione: "Con ascolto e professionalità ti guiderò verso la scelta della casa che rispecchia davvero le tue aspettative.",
  provincia: "Alessandria",
  tel: "+393474563210",
  mail: "matteo.cavazza@immobiliaris.com",
  img: asset('images/chisiamo/Torino/agente1.webp'),
},
{
  id: 11,
  nome: "Irene Marchisio",
  descrizione: "Ogni casa è un'opportunità per costruire nuovi inizi.",
  presentazione: "Ti supporterò con chiarezza e dedizione per aiutarti a riconoscere il potenziale di ogni immobile.",
  provincia: "Alessandria",
  tel: "+393491122334",
  mail: "irene.marchisio@immobiliaris.com",
  img: asset('images/chisiamo/Torino/agente1.webp'),
},
{
  id: 12,
  nome: "Federico Dalmonte",
  descrizione: "Metto al centro le tue esigenze per offrirti un percorso valido.",
  presentazione: "Sarò al tuo fianco per guidarti verso una scelta immobiliare consapevole e duratura.",
  provincia: "Alessandria",
  tel: "+393456778899",
  mail: "federico.dalmonte@immobiliaris.com",
  img: asset('images/chisiamo/Torino/agente1.webp'),
},

// Agenti di Asti
{
  id: 13,
  nome: "Chiara Venturi",
  descrizione: "Trasformo le esigenze abitative in soluzioni concrete e su misura.",
  presentazione: "Ti accompagnerò con cura e disponibilità nella scelta dell'immobile che possa davvero rappresentarti.",
  provincia: "Asti",
  tel: "+393401987654",
  mail: "chiara.venturi@immobiliaris.com",
  img: asset('images/chisiamo/Torino/agente1.webp'),
},
{
  id: 14,
  nome: "Lorenzo Malfatti",
  descrizione: "Metto competenza e ascolto al servizio di ogni progetto.",
  presentazione: "Con professionalità e trasparenza ti guiderò nel riconoscere il valore reale degli spazi che visiteremo.",
  provincia: "Asti",
  tel: "+393472223344",
  mail: "lorenzo.malfatti@immobiliaris.com",
  img: asset('images/chisiamo/Torino/agente1.webp'),
},
{
  id: 15,
  nome: "Sofia Bellandi",
  descrizione: "Credo nel potere degli ambienti di migliorare il benessere di chi li vive.",
  presentazione: "Sarò al tuo fianco per aiutarti a scegliere la casa più adatta ai tuoi ritmi, ai tuoi sogni e alle tue priorità.",
  provincia: "Asti",
  tel: "+393498800112",
  mail: "sofia.bellandi@immobiliaris.com",
  img: asset('images/chisiamo/Torino/agente1.webp'),
},
{
  id: 16,
  nome: "Davide Rossetti",
  descrizione: "Rendo ogni percorso immobiliare semplice, chiaro e sereno.",
  presentazione: "Ti supporterò con concretezza ed esperienza per accompagnarti verso una scelta sicura e senza stress.",
  provincia: "Asti",
  tel: "+393402225566",
  mail: "davide.rossetti@immobiliaris.com",
  img: asset('images/chisiamo/Torino/agente1.webp'),
}
  ];

  const filteredAgents =
    selectedProvince === "Tutti"
      ? agentsData
      : agentsData.filter((a) => a.provincia === selectedProvince);

  const phoneNumber = "+391234567890";
  const emailAddress = "info@immobiliaris.com";

  useEffect(() => {
    setPageMeta({
      title: 'Chi Siamo - Team di Agenti Immobiliari Esperti | Immobiliaris',
      description:
        'Scopri il team di Immobiliaris: oltre 56 agenti immobiliari esperti a Torino, Cuneo, Alessandria e Asti. 15 anni di esperienza, 462+ case vendute e consulenza personalizzata.',
      url: window.location.href,
      image: asset('images/chisiamo/Torino/agente1.webp'),
      type: 'website'
    });

    setStructuredData({
      "@context": "https://schema.org",
      "@type": "RealEstateAgent",
      "name": "Immobiliaris",
      "description": "Agenzia immobiliare con oltre 56 agenti esperti operanti in Piemonte. Fondata nel 2005, offriamo consulenza personalizzata per vendita e affitto immobili.",
      "url": window.location.href,
      "foundingDate": "2005",
      "address": {
        "@type": "PostalAddress",
        "streetAddress": "Via Margherite 56",
        "addressLocality": "Torino",
        "postalCode": "10059",
        "addressCountry": "IT"
      },
      "areaServed": [
        {
          "@type": "City",
          "name": "Torino"
        },
        {
          "@type": "City",
          "name": "Cuneo"
        },
        {
          "@type": "City",
          "name": "Alessandria"
        },
        {
          "@type": "City",
          "name": "Asti"
        }
      ],
      "telephone": "+391234567890",
      "email": "info@immobiliaris.com",
      "numberOfEmployees": {
        "@type": "QuantitativeValue",
        "value": "56"
      },
      "aggregateRating": {
        "@type": "AggregateRating",
        "ratingValue": "4.9",
        "reviewCount": "462"
      }
    });
  }, []);


  {/* -------------------------------- */ }
  {/* INIZIO HTML */ }
  {/* -------------------------------- */ }

  return (
    <div className="chisiamo">

      {/* -------------------------------- */}
      {/* HERO */}
      {/* -------------------------------- */}
      <section className="hero-chisiamo">
        <div className="hero-bg-logo-chisiamo"></div>
        <div className="hero-bg-palazzo-chisiamo"></div>

        <div className="hero-content-chisiamo">
          <div className="hero-text-chisiamo">
            <h1>Chi siamo</h1>
            <p>
              <strong>Immobiliaris</strong> è l’agenzia immobiliare di riferimento a <strong>Torino</strong>, specializzata nella vendita di case con <strong>consulenza su misura</strong> e <strong>profonda conoscenza</strong> del territorio.
            </p>
          </div>
        </div>
      </section>

      <section className="hero-title-chisiamo">
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
            <a href="https://www.google.com/maps/search/?api=1&query=Piazza+San+Carlo+Torino" target="_blank" rel="noopener noreferrer">
            <img src={asset('images/chisiamo/mappa.webp')} alt="Mappa Google" style={{ cursor: 'pointer' }} />
          </a>
        </div>
      </section>


      {/* -------------------------------- */}
      {/* BOTTONI RECAPITI */}
      {/* -------------------------------- */}
      <section className="bottone-recapiti-section">
        <button onClick={() => window.location.href = `tel:${phoneNumber}`} className="recapiti-btn" >
          <img src={asset('icons/phone-call.png')} alt="Phone icon" style={{ width: '24px', height: '24px', marginRight: '10px', verticalAlign: 'middle' }} />
          +39 123 456 7890
        </button>
      </section>

      <section className="bottone-recapiti-section">
        <button onClick={() => window.location.href = `mailTo:${emailAddress}`} className="recapiti-btn" > 
          <img src={asset('icons/email.png')} alt="Email icon" style={{ width: '24px', height: '24px', marginRight: '10px', verticalAlign: 'middle' }} />
          info@immobiliaris.com </button>
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
          <img src={asset('images/chisiamo/panorama.webp')} alt="Foto Panorama Torino" />
        </div>

      </section>


      {/* -------------------------------- */}
      {/* I DATI */}
      {/* -------------------------------- */}
      <section className="dati-section">
        <h2>Cosa abbiamo costruito insieme</h2>
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

/**
 * @fileoverview About Us page component showcasing the real estate agency team
 * 
 * @description
 * This component displays comprehensive information about Immobiliaris real estate agency,
 * including a filterable list of agents across different provinces, company history,
 * location information, and key statistics. It features interactive agent cards with
 * detailed modals, province filtering, and SEO optimization with structured data.
 * 
 * @component ChiSiamo
 * @returns {JSX.Element} A functional React component that renders the About Us page
 * 
 * @typedef {Object} Agent
 * @property {number} id - Unique agent identifier
 * @property {string} nome - Agent's full name
 * @property {string} descrizione - Short description/tagline
 * @property {string} presentazione - Detailed agent presentation
 * @property {string} provincia - Province where the agent operates (Torino, Cuneo, Alessandria, Asti)
 * @property {string} tel - Agent's phone number
 * @property {string} mail - Agent's email address
 * @property {string} img - Agent's profile image URL
 * 
 * @state {boolean} openMenu - Controls the visibility of the province filter modal
 * @state {string} selectedProvince - Currently selected province filter ("Torino", "Cuneo", "Alessandria", "Asti", "Tutti")
 * @state {Agent|null} selectedAgent - Currently selected agent for detailed modal view
 * 
 * @constant {Array<string>} provinces - Available province options for filtering
 * @constant {Array<Agent>} agentsData - Complete dataset of all agents across provinces
 * @constant {Array<Agent>} filteredAgents - Agents filtered by selected province
 * @constant {string} phoneNumber - Agency contact phone number
 * @constant {string} emailAddress - Agency contact email address
 * 
 * @function asset
 * @description Helper function to resolve asset paths via Vite for development and production
 * @param {string} p - Relative path to asset from /src/assets/
 * @returns {string} Fully resolved asset URL
 * @example
 * asset('images/chisiamo/Torino/agente1.webp')
 * // Returns: resolved URL to the asset
 * 
 * @hook useEffect
 * @description Sets up SEO metadata and structured data for the page on component mount
 * @sideEffects
 * - Calls setPageMeta with page title, description, URL, image, and type
 * - Calls setStructuredData with Schema.org RealEstateAgent structured data
 * 
 * @dependencies
 * - react: Core React library with hooks (useState, useEffect)
 * - ../../utils/seo: SEO utilities (setPageMeta, setStructuredData)
 * 
 * @styling
 * - ./chisiamo.css: Contains all component-specific styles
 * 
 * @seoFeatures
 * - Dynamic page meta tags (title, description, Open Graph)
 * - Schema.org structured data for real estate agency
 * - Includes company information, contact details, service areas, and ratings
 * 
 * @sections
 * 1. Hero Section - Main introduction with background images
 * 2. Province Filter - Modal-based filter to select agent province
 * 3. Agent Cards Grid - Filterable grid of agent cards with images
 * 4. Agent Detail Modal - Detailed view with contact information
 * 5. Location Map - Interactive map link to Google Maps
 * 6. Contact Buttons - Quick call and email buttons
 * 7. Company History - Story and background of the agency
 * 8. Statistics - Key metrics (houses sold, agents, consultations)
 * 
 * @interactiveFeatures
 * - Province dropdown filter with modal overlay
 * - Agent card click to view detailed information
 * - Modal with agent presentation and contact details
 * - Clickable map linking to Google Maps
 * - Phone and email quick action buttons
 * 
 * @assets
 * - Agent profile images for all provinces
 * - Background images for hero section
 * - Map image with link to Google Maps
 * - Icons for phone and email buttons
 * - Panorama image for company history section
 * 
 * @dataStructure
 * The component manages 16 agents across 4 provinces:
 * - Torino: 4 agents (ids 1-4)
 * - Cuneo: 4 agents (ids 5-8)
 * - Alessandria: 4 agents (ids 9-12)
 * - Asti: 4 agents (ids 13-16)
 * 
 * @example
 * // Basic usage
 * import ChiSiamo from './pages/ChiSiamo/chisiamo';
 * 
 * function App() {
 *   return (
 *     <div>
 *       <ChiSiamo />
 *     </div>
 *   );
 * }
 * 
 * @author Rooftop Development Team
 * @version 1.0.0
 */
