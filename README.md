# Gruppo 3 – Rooftop  

---

## Team & Ruoli

### Software Developer
- Alessandro Formicola – [@Alx370](https://github.com/Alx370)  
- Alessandro De Filippis – [@Aledefy](https://github.com/Aledefy)  
- Andrea Alume – [@AndreaAlume](https://github.com/AndreaAlume)

### Web Developer
- Kuhharonak Stanislau – [@stani-kukha](https://github.com/stani-kukha)  
- Gualtiero Begalla – [@gualty160104](https://github.com/gualty160104)  
- Noemi Borra – [@noemiborra](https://github.com/noemiborra)

### Digital Strategist
- Marta Pastore – [@martapastore-svg](https://github.com/martapastore-svg)  
- Matteo Bianco – [@matteobianco583](https://github.com/matteobianco583)  
- Chiara Guercio – [@chiaraguercio-tech](https://github.com/chiaraguercio-tech)  
- Antonio Petroiae – [@antonioapetroaie-cmd](https://github.com/antonioapetroaie-cmd)

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
- **Frontend**: React + Vite, HTML5, CSS, TypeScript/JavaScript  
- **Backend**: Java 21, Spring Boot  
- **Database**: MySQL  
- **Containerizzazione**: Docker + Docker Compose

---

## Requisiti per Avviare il Progetto
- Node.js v18+
- JDK 21  
- Docker Desktop + Docker Compose  
- Porte disponibili: **5173**, **8080/8081**, **3307/3308**

## Avvio del progetto (Docker)
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

--- 

## Struttura delle Cartelle
Rooftop/
├── capibaraweb-main/        # Frontend
│   ├── Dockerfile
│   ├── src/
│   └── vite.config.js
│
├── Progetto_Rooftop/        # Backend
│   ├── Dockerfile
│   ├── src/main/java/
│   └── application.properties
│
├── Database/                # Inizializzazione database
│   └── init.sql
│
├── docker-compose.yml
└── docker-compose.dev.yml

---

## Documentazione API 
La documentazione delle API è disponibile all'indirizzo:
[@API](Progetto_Rooftop/API_doc.md)
Contiene:
- Endpoints pubblici e privati
- Metodi, parametri e body richiesti
- Risposte e codici HTTP

---