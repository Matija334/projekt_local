# Simple Contact Manager - MMA

<p align="center">
  <img alt="SCM Logo" width="400" src="https://github.com/mihaprah/projekt/assets/116807398/1d171956-57b7-49b9-93d9-3525148c70d7" >
</p>

## Uvod

Ta dokument opisuje korake za zagon backenda in podatkovne baze z uporabo Docker Compose ter frontenda.

## Koraki za zagon

### 1. Kloniranje repozitorija

Najprej klonirajte repozitorij na vašo lokalno napravo.

### 2. Pridobitev firebase serviceAccountKey.json datoteke za backend

Za konfiguracijo Firebase je potrebno pridobiti serviceAccountKey.json 

1. Pojdite na Firebase Console.
2. Izberite svoj projekt.
3. V stranski vrstici kliknite na Project Settings (Nastavitve projekta).
4. Izberite zavihek Service accounts (Storitveni računi).
5. Izberite Java in kliknite na gumb Generate new private key (Ustvari nov zasebni ključ). To bo preneslo datoteko serviceAccountKey.json na vaš računalnik.

Ustvarjeno datoteko kopirajte v **projekt_local/backend/scm/src/main/resources**

### 3. Konfiguracija docker compose

V datoteki docker-compose.yml, ki se nahaja v projekt_local/ uredite naslednjo vrstico:
SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/*************your database name goes here************* tukaj navedite poljubno ime za podatkovno bazo

### 4. Zagon docker compose

Postavite se v mapo projekt_local, kjer se nahaja datoteka docker-compose/yml in izvedite ukaz **docker-compose up --build**

### 5. Pridobitev firebase SDK za frontend

1. Pojdite na Firebase Console.
2. Izberite svoj projekt.
3. V stranski vrstici kliknite na Project Settings (Nastavitve projekta).
4. Izberite zavihek General.

Generirane vrednosti prekopirajte v **projekt_local/frontend/scm/.env**

### 6. Zagon frontenda

Postavite se v mapo projekt_local/frontend/scm in izvedite naslednje ukaze:
- npm install
- npm run build
- npm run start

