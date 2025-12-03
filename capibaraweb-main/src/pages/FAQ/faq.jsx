import React from "react";
import "./faq.css";
import { sendContactRequest } from "../../api/contattoApi.js";


// immagini
import CasaFaq from "../../assets/images/casafaq.png";
import GardenFaq from "../../assets/images/gardenfaq.png";

const Faq = () => {

  const handleFaqSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData(e.target);

    // Nome completo → separiamo nome + cognome
    const fullName = formData.get("nome");
    const email = formData.get("email");
    const messaggio = formData.get("messaggio");

    if (!fullName || !email || !messaggio) {
      alert("Compila tutti i campi.");
      return;
    }

    // Divido automaticamente Nome e Cognome
    const [nome, ...rest] = fullName.trim().split(" ");
    const cognome = rest.join(" ") || " ";

    try {
      await sendContactRequest({
        nome,
        cognome,
        email,
        telefono: null, // non presente nel form
        messaggio,
      });

      alert("Richiesta inviata correttamente! Ti risponderemo presto.");

      e.target.reset();

    } catch (err) {
      console.error("Errore invio richiesta FAQ:", err);
      alert("Errore durante l’invio. Riprova tra qualche istante.");
    }
  };

  return (
    <div className="faq-page">

      {/* --- FAQ INTRO --- */}
      <section className="faq-section">
        <div className="faq-text">
          <h2>Qui per rispondere a tutte<br />le tue domande</h2>
          <p>
            Non hai trovato la risposta che cercavi? Nessun problema.
            Ogni situazione è unica, e siamo qui proprio per ascoltarti.
          </p>
        </div>

        {/* FAQ ITEMS */}
        <div className="faq-boxes">

          <details className="faq-item">
            <summary><span>Come selezionate gli inquilini?</span></summary>
            <p>
              La nostra agenzia immobiliare a Torino seleziona gli inquilini <br />
              verificando <b>documenti, reddito</b> e <b>referenze</b>, così da garantire che <br />
              ogni <b>immobile in affitto</b> sia affidato a persone affidabili e in linea con
              le caratteristiche dell'<b>appartamento</b> e del <b>contratto di locazione.</b>
            </p>
          </details>

          <details className="faq-item">
            <summary><span>Quali documenti servono per la valutazione?</span></summary>
            <p>
              Per effettuare una corretta valutazione in <b>seconda fase </b>dopo aver <br />
              compilato il form, sono necessari alcuni documenti: il <b>titolo di proprietà</b>, la
              <b>visura catastale</b> e la <b>planimetria catastale. </b> <br />

              È inoltre obbligatorio presentare l’APE – <b>Attestato di Prestazione <br />
                Energetica</b>, insieme al <b>certificato di agibilità</b> e, se disponibili, le<br />
              certificazioni di conformità degli impianti.<br />

              <b>Documenti aggiuntivi</b> come regolamento e spese condominiali,<br />
              planimetrie dettagliate o permessi edilizi possono rendere la stima del
              valore dell’immobile in affitto più <b>precisa</b> e<b> affidabile.</b>
            </p>
          </details>

          <details className="faq-item">
            <summary><span>Offrite supporto anche dopo la firma?</span></summary>
            <p>
              Sì, il nostro servizio non si conclude con la firma del <b>contratto di locazione</b>
              o di <b>compravendita immobiliare.</b><br />

              Offriamo un <b>supporto continuativo</b> che include assistenza nella gestione
              delle pratiche burocratiche, chiarimenti su spese condominiali e
              adempimenti fiscali, oltre a un contatto diretto con i nostri consulenti
              immobiliari per eventuali <b> esigenze future.</b><br />

              Garantiamo una <b>relazione trasparente</b> e <b>duratura</b>, accompagnando
              proprietari e inquilini anche nelle fasi successive alla stipula.
            </p>
          </details>

          <details className="faq-item">
            <summary><span>Quanto dura in media una trattativa?</span></summary>
            <p>
              La durata di una trattativa immobiliare <b>può variare</b> in base a diversi fattori,
              come la tipologia di immobile in vendita o in affitto, la domanda nella zona
              e la disponibilità delle parti.<br />

              In media, una trattativa per la compravendita di una casa <b>può richiedere
                da poche settimane a diversi mesi,</b> mentre per la locazione di un
              appartamento i tempi sono generalmente più brevi, spesso compresi tra <b>15 e 30 giorni. </b>
            </p>
          </details>

          <details className="faq-item">
            <summary><span>Posso affidare la vendita a distanza?</span></summary>
            <p>
              Sì, oggi <b>è possibile gestire la vendita </b> di un immobile a <b>distanza</b> grazie a
              strumenti digitali e procedure <b>sicure</b>. Attraverso consulenze online, la
              condivisione di documentazione immobiliare in formato digitale e l’utilizzo
              della firma elettronica, il proprietario può seguire ogni fase <b>senza essere
                fisicamente presente. <br /></b>

              Le visite vengono organizzate con appuntamenti gestiti dall’agenzia,
              mentre la trattativa e la stipula del contratto di compravendita avvengono
              in totale trasparenza e conformità normativa. In questo modo la <b>vendita
                casa online </b>diventa pratica, veloce e affidabile.
            </p>
          </details>

        </div>
      </section>

      {/* --- FORM + IMMAGINE --- */}
      <section className="faq-contact">
        <div className="contact-column">
          <h2>Non trovi la risposta alla tua domanda?</h2>
          <p>
            Scrivici e un nostro consulente ti risponderà <b>personalmente</b> con la soluzione più adatta<br />
            alle <b>tue esigenze.</b>
          </p>
        </div>

        <div className="contact-row">
          <form className="faq-form" onSubmit={handleFaqSubmit}>
            <div className="input-wrap">
              <input type="text" placeholder="Nome Cognome" />
            </div>
            <div className="input-wrap">
              <input type="email" placeholder="Mail" />
            </div>
            <div className="input-wrap">
              <textarea placeholder="Messaggio..." />
            </div>

            <button type="submit" className="btn-primary">Invia</button>
          </form>

          <div className="faq-contact-image">
            <img src={CasaFaq} alt="Casa FAQ" className="faq-image-main" />
            <img src={GardenFaq} alt="Garden FAQ" className="faq-image-bg" />
          </div>
        </div>
      </section>

    </div>
  );
};

export default Faq;
