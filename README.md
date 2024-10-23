# Application MDD App

## Installations
Pour le bon fonctionnement du projet, vous aurez besoin de plusieurs éléments.
   ### WampServer
   - Télécharger et installer WampServer
   - > https://wampserver.aviatechno.net/index.php?affiche=install&lang=fr.
   
   ### Apache Maven
   - Télécharger et installer Apache Maven
   - > https://maven.apache.org/download.cgi
   
   ### Java Developpment Kit
   - Télécharger et installer Java Developpment Kit
   - > https://www.oracle.com/java/technologies/downloads/

   ### Node Module
   - Rendez-vous dans le dossier `/chemin/vers/votre/projet/Front-End/` et utilisez la commande `npm install` dans l'invité de commandesmv

## Database Configuration

1. **MySQL Configuration:**

   - L'utilisateur par default est `root`, il n'y a pas de mot de passe.
   - Créer votre base de donnée `votre_base_de_donnée`.

2. **Création Base de données in PhpMyAdminer**

   - Rendez vous sur l'adresse suivante : `localhost`
   - Acceder maintenant a `PhpMyAdmin` et connectez vous.
   - Créez votre base de données
   - Depuis l'onglet import, importer le script pour la création de la database qui se trouve :
     > /chemin/vers/votre/projet/Front-End/ressources/sql/script.sql

3. **Création clé secrete**  
   Pour créer votre clé secrète, vous pouvez en générer une à partir de ce site `https://asecuritysite.com/encryption/plain`.  
   Choisissez la clé `256-bit` et cliquez sur `determine`  
   `Hex key` sera votre `clé secrète`  

4. **Configuration de la base de données dans Spring Boot:** 
   - Mise a jour de application properties `/chemin/vers/votre/projet/Back-End/SpringSecurityConfig/src/main/resources/application.properties` :
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
     spring.datasource.username=root
     spring.datasource.password=
     spring.jpa.hibernate.ddl-auto=update
     pictures.path=/chemin/pour/entroposer/vos/images/
     jwt.secret=votre_clé_secrète
     ```

## Lancement du projet

- Pour lancer le projet, vous devrez tout d'abord créer les variables d'environnement pour Java et Maven.  
- Pour lancer le serveur Java, dans un terminal placez vous dans le dossier `/Back-End/SpringSecurityConfig` et entrez la commande `mvn spring-boot:run`.  
- Pour lancer le serveur Angular, dans un terminal placez vous dans le dossier `/Front-end` et entrez la commande `npm run start`.


## Acceder à l'application

- Pour acceder à l'application, rendez-vous sur : `localhost:4200`

## Documentation Swagger de l'API

- Pour acceder à la documentation , rendez-vous sur : `http://localhost:3001/swagger-ui/index.html#/`
