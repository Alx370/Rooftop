# Docker Setup - Rooftop Immobiliare

## 📋 Prerequisiti

- Docker Desktop installato e in esecuzione
- Docker Compose v3.9+
- Porte libere: 3308, 8080, 3000 (prod) / 3307, 8081, 5173 (dev)

## Avvio

### Produzione

```bash
# Build e avvio di tutti i servizi
docker-compose up -d

# Verifica lo stato
docker-compose ps

# Visualizza i log
docker-compose logs -f

# Accedi all'applicazione
# Frontend: http://localhost:5173
# Backend API: http://localhost:8080
# MySQL: localhost:3308
```

### Sviluppo

```bash
# Build e avvio ambiente development
docker-compose -f docker-compose.dev.yml up -d

# Accedi all'applicazione
# Frontend: http://localhost:5173
# Backend API: http://localhost:8081
# MySQL: localhost:3307
```

## 🛠️ Comandi Utili

### Gestione Container

```bash
# Stop di tutti i servizi
docker-compose down

# Stop e rimozione volumi (⚠️ cancella il database!)
docker-compose down -v

# Riavvio singolo servizio
docker-compose restart backend

# Rebuild senza cache
docker-compose build --no-cache

# Visualizza log di un servizio specifico
docker-compose logs -f backend
```

### Database

```bash
# Accesso MySQL container
docker exec -it rooftop_mysql_prod mysql -u rooftop -pback3nd rooftop_immobiliare

# Backup database
docker exec rooftop_mysql_prod mysqldump -u rooftop -pback3nd rooftop_immobiliare > backup.sql

# Restore database
docker exec -i rooftop_mysql_prod mysql -u rooftop -pback3nd rooftop_immobiliare < backup.sql
```

### Debugging

```bash
# Shell interattivo nel backend container
docker exec -it rooftop_backend_prod sh

# Shell interattivo nel frontend container
docker exec -it rooftop_frontend_prod sh

# Visualizza risorse utilizzate
docker stats
```

## Architettura

```
┌─────────────────┐
│   Frontend      │  React + Vite + Nginx
│   :5173 / :5173 │  (Alpine Linux)
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   Backend       │  Spring Boot + Java 21
│   :8080 / :8081 │  (Alpine Linux)
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   MySQL         │  MySQL 8.0.40
│   :3308 / :3307 │
└─────────────────┘
```

## Servizi

### MySQL 
- **Image**: mysql:8.0.40
- **Porta Produzione**: 3308 
- **Porta Sviluppo**: 3307
- **Database**: rooftop_immobiliare
- **User**: rooftop / back3nd
- **Dati iniziali**: `Database/init.sql` (caricato automaticamente)

### Backend
- **Build**: Maven 3.9.9 + Eclipse Temurin 21
- **Runtime**: Eclipse Temurin 21-jre-alpine
- **Porta Produzione**: 8080
- **Porta Sviluppo**: 8081
- **Healthcheck**: Attende MySQL pronto

### Frontend
- **Build**: Node 18-alpine + Vite
- **Runtime**: Nginx Alpine
- **Porta Produzione**: 5173 (mappa nginx :80)
- **Porta Sviluppo**: 5173 (mappa nginx :80)
- **SPA Routing**: Gestito da nginx.conf

## Variabili d'Ambiente

### Backend (application.properties override)

```env
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/rooftop_immobiliare?useSSL=false&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME=rooftop
SPRING_DATASOURCE_PASSWORD=back3nd
SPRING_JPA_HIBERNATE_DDL_AUTO=validate  # 'update' in dev
SPRING_JPA_SHOW_SQL=false               # 'true' in dev
```

## Note Importanti

1. **Primo avvio**: L'inizializzazione del database può richiedere 20-30 secondi
2. **Healthcheck**: Il backend attende che MySQL sia completamente pronto
3. **Volumi persistenti**: I dati MySQL sono salvati nei volumi Docker
4. **Network**: Tutti i container sono nella stessa rete Docker (comunicazione interna)

## Troubleshooting

### Il backend non si connette al database
```bash
# Verifica che MySQL sia healthy
docker-compose ps
# Se non è healthy, controlla i log
docker-compose logs mysql
```

### Errore di build del frontend
```bash
# Pulisci node_modules e rebuild
cd capibaraweb-main
rm -rf node_modules dist
docker-compose build --no-cache frontend
```

### Porta già in uso
```bash
# Verifica processi in ascolto
netstat -ano | findstr :3306
netstat -ano | findstr :8080
# Termina il processo o cambia porta in docker-compose.yml
```

## Pulizia Completa

```bash
# Stop e rimozione di tutto (container, network, volumi)
docker-compose down -v

# Rimozione immagini
docker rmi $(docker images -q rooftop*)

# Pulizia sistema Docker
docker system prune -a --volumes
```
