# Projecte AppEcityclic
Aquest projecte és una aplicació composada per dos components principals:
1. **Bakcend:** Un servidor creat amb Spring Boot que gestiona la lògica de negoci i la base de dades
2. **Frontend:** Una aplicació Angular que proporciona una interfície d'usuari.

## Requisits prèvis
- **Java 21** o superior per a executar el Backend amb Spring Boot
- **Node.js 20** o superior, juntament amb  **Angular CLI**, per a executar el frontend

## Instruccions per a l'execució
### Executar el Backend (Spring Boot)
1. Navegar fins a la carpeta `Backend`
```bash
cd AppEcityclic/AppEcityclicBack
```
2. Compilar i executar el projecte
```bash
./mvnw spring-boot:run
```
Això iniciarà el servidor en `http://localhost:8080`.
### Executar el Frontend (Angular)
1. Navegar fins a la carpeta `Frontend`
```bash
cd AppEcityclic/AppEcityclicFront
```
2. Instalar les dependències
```bash
npm install
```
3. Executar el projecte
```bash
ng serve -o
```
Això iniciarà l'aplicació Angular en `http://localhost:4200`.
