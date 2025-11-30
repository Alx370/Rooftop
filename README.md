# Gruppo numero 3 - Rooftop
## Membri del Gruppo
- Alessandro Formicola (https://github.com/Alx370)
- Alessandro De Filippis (https://github.com/Aledefy)
- Andrea Alume (https://github.com/AndreaAlume)
- Kuhharonak Stanislau (https://github.com/stani-kukha)
- Gualtiero Begalla (https://github.com/gualty160104)
- Noemi Borra (https://github.com/noemiborra)
- Marta Pastore (https://github.com/martapastore-svg)
- Matteo Bianco (https://github.com/matteobianco583)
- Chiara Guercio (https://github.com/chiaraguercio-tech)
- Antonio Petroiae (https://github.com/antonioapetroaie-cmd)

---

# Progetto Immobiliaris

## Descrizione del progetto
Immobiliaris è un’agenzia immobiliare attiva nel territorio piemontese, parte del gruppo Indomus.
Il suo core business è la compravendita di immobili, con particolare attenzione all’acquisizione di
nuove proprietà da vendere. Attualmente, la clientela è composta prevalentemente da over 55,
acquisita tramite canali tradizionali (passaparola, volantini, sponsorizzazione di piccoli eventi
locali). L’obiettivo è modernizzare l’approccio per attrarre un target più giovane (35–55 anni),
attraverso la creazione di un nuovo portale digitale e una strategia di comunicazione integrata.

## Obiettivo del Portale

Il portale digitale mira a supportare Immobiliaris nel raggiungimento dei seguenti obiettivi:
- Acquisire nuovi immobili tramite un percorso guidato per il proprietario.
- Generare lead qualificati attraverso campagne digitali.
- Offrire una valutazione immobiliare entro 72 ore.
- Gestire richieste e flussi interni tramite un backoffice dedicato.
- Supportare l’agenzia nell’ampliamento della propria presenza online.
Il tutto con un approccio ispirato ai modelli già presenti sul mercato (es. Gromia), ma adattato alle necessità e al posizionamento di Immobiliaris.

## Aree geografiche coperte
Il portale è pensato per le principali città piemontesi:
- Torino
- Cuneo
- Alessandria
- Asti

---

## Tecnologie Utilizzate
- **Frontend**: React + Vite, HTML5, CSS, JavaScript/TypeScript
- **Backend**: Java con Spring Boot
- **Database**: MySQL
- **Containerizzazione**: Docker e Docker Compose

---

## Requisiti per Avviare il Progetto
- Node.js (versione 18 o superiore)
- Java Development Kit (JDK) (versione 21)
- Docker Desktop e Docker Compose

## Avvio del progetto
Assicurarsi di avere Docker in esecuzione prima di procedere e di non avere porte occupate e in caso chiudere servizi in conflitto su `5173`, `8080/8081`, `3307/3308`.
Infine clonare il repository principale che contiene sia il frontend che il backend.
```bash
git clone https://github.com/Alx370/Rooftop
cd Rooftop
```
### Avvio del progetto (Produzione)
1. Avviare i servizi con Docker Compose:
   ```bash
   docker-compose up --build
   ```
4. Accedere al frontend su: `http://localhost:5173`

### Sviluppo
1. Utilizzare il file `docker-compose.dev.yml` per porte e impostazioni di sviluppo:
   ```powershell
   docker-compose -f docker-compose.dev.yml up --build
   ```
2. URL di sviluppo:
   - Frontend: `http://localhost:5173`
   - Backend: `http://localhost:8081`
   - MySQL: `localhost:3307`

---

## Struttura delle Cartelle
- **Frontend**: `capibaraweb-main/`
  - Contiene `Dockerfile`, `vite.config.js`, e la directory `src/`.
- **Backend**: `Progetto_Rooftop/`
  - Contiene `Dockerfile`, `src/main/java`, e `application.properties`.
- **Database**: `Database/`
  - Contiene `init.sql` per l’inizializzazione del database.

---

## Avvio Manuale (Senza Docker)
In caso di problemi con Docker, è possibile avviare manualmente frontend e backend.

### Frontend
1. Spostarsi nella directory del frontend:
   ```powershell
   cd capibaraweb-main
   npm install
   npm run dev
   ```

### Backend
1. Spostarsi nella directory del backend:
   ```powershell
   cd Progetto_Rooftop
   ./mvnw spring-boot:run
   ```

Assicurarsi che MySQL sia avviato e configurato correttamente.

## Troubleshooting
- **Immagini non visibili nel frontend**: Importare gli asset in JSX con `import img from "...";`.
- **CSS non applicati**: Verificare gli import corretti, es. `import "./Header.css";`.
- **Porte occupate**: Chiudere servizi in conflitto su `5173`, `8080/8081`, `3307/3308`.
- **Errori TypeScript sugli import immagini**: Aggiungere `src/images.d.ts` con `declare module '*.png';` (già presente).



