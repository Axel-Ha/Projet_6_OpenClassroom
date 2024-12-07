#  image officielle Maven 
FROM maven:3.8.6-openjdk-17 AS build

# Définir le répertoire de travail dans le conteneur pour le code source
WORKDIR /app

# Copier projet dans le répertoire de travail
COPY . .

# construire le fichier JAR
#-DskipTests ignore les tests 
RUN mvn clean package -DskipTests

# image légère d'OpenJDK 17 pour exécuter l'application
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR généré depuis la première étape dans l'image finale
COPY --from=build /app/target/*.jar app.jar

# Exposer le port 8080 
EXPOSE 8080

# Définir la commande de démarrage pour exécuter le fichier JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
