# Dockerizzare il tutto 
il nostro obbietivo e dockerizzare il progetto backend e frontend in modo tale da poterlo eseguire su qualsiasi macchina senza dover installare nulla.
Per fare cio dobbiamo creare due file Dockerfile uno per il backend e uno per il frontend.
- dockerfile backend
- docker-compose_backend_env.yml
- dockerfile frontend
- docker-compose_frontend_env.yml

nei file di progetto ci sara un docker compose per gli env di sviluppo e uno per la produzione.

# Configurazione Database
Per la configurazione del database utilizziamo mysql come database relazionale.


# Autenticazione Spring Security
Quelli di frontend dovranno implementare l'autenticazione tramite JWT token, tramite questo noi di backend vedremo se l'utente e autenticato o meno e in base a questo gli permetteremo di accedere alle risorse richieste.

# Suddividiamo le responsabilita:
Formicola - Dockerizzazione, utenti e autenticazione
Alume - tutto collegato agli Immobili
De filippis - Il resto delle funzionalita


bevete tanta acqua
fate stretching ogni tanto
divertitevi
