# Intégration de Keycloak avec une application Spring Boot et Thymeleaf
Application de démonstration pour l'intégration de Keycloak comme fournisseur d'identité (IdP) avec Spring Security.

## 🎯 Objectifs
- Authentifier les utilisateurs via Keycloak
- Contrôler l'accès aux pages selon les rôles
- Tester le flux OIDC complet (Authorization Code Flow)

## Prérequis
Avant de commencer, assurez-vous d'avoir les éléments suivants installés :
- **Java 17**
- **Maven**
- **Keycloak** installé et configuré via Docker
- **Spring Boot 3.4.3** avec Thymeleaf

## Ajout des dépendances Keycloak
Ajoutez les dépendances suivantes dans votre fichier `pom.xml` :

```xml
<dependencies>
 
    <!-- Client OAuth2 -->
 <!--l'intégration avec Keycloak via le protocole OpenID Connect (OIDC)-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
 
 <dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
 </dependency>
</dependencies>
```

## Configuration de Keycloak

### Points Clés de la Configuration
Dans `application.properties`, configurez Keycloak avec les paramètres suivants :

```properties
keycloak.realm=intelcom_platform
keycloak.auth-server-url=http://localhost:8080
keycloak.resource=client1
keycloak.logout-url=http://localhost:8080/realms/intelcom_platform/protocol/openid-connect/logout

spring.security.oauth2.client.registration.keycloak.client-id=client1
spring.security.oauth2.client.registration.keycloak.client-secret=<SECRET>
spring.security.oauth2.client.registration.keycloak.scope=openid
spring.security.oauth2.client.provider.keycloak.issuer-uri=http://localhost:8080/realms/intelcom_platform
```

## Architecture du Projet

```
│   Keycloak-practical-app-main.java
│
├───controller
│       RedirectController.java
│
├───security
│       KeycloakRoleConverter.java
│       SecurityConfig.java
│
└───resources
│   application.properties
│
├───static
└───templates
    ├── admin.html
    ├── home.html
    └── client.html
```

## Démarrage du Projet

### 1. Démarrer Keycloak
Lancez Keycloak via Docker :

```sh
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.5 start-dev
```

### 2. Configurer Keycloak
1. **Créez un realm** : `intelcom_platform`
2. **Ajoutez un client** : `client1`
   - Client Type : OpenID Connect
   - Valid Redirect URIs : `http://localhost:8081/*`
3. Créer des rôles : `Admin`, `User`
4. Créer un utilisateur test (`laila` avec mot de passe) et lui attribuer des rôles
5. **Créer scope** :
  - Client > `Client Scopes` > `Create`
  - Nom : `roles`
  - Type : `Default`
  - Dans l'onglet `Mappers` :
  - Créez un mapper de type **"User Realm Role"** ou **"User Client Role"**
  - Nom : `Role mapper`
  - Token Claim Name : `roles`
  - Claim JSON Type : `String`
  - Activez `Add to ID token` et `Add to access token`

### 3. Démarrer l'Application Spring Boot
Exécutez la commande suivante :

```sh
mvn spring-boot:run
```

### 4. Accéder à l'Application
Ouvrez votre navigateur et accédez à :

```
http://localhost:8081
```

Connectez-vous avec un utilisateur Keycloak pour accéder à l'application.

## Points Clés
- **Authentification centralisée** : Keycloak gère les utilisateurs et les rôles.
- **Gestion flexible des rôles et permissions** : Administrable depuis Keycloak.

## Conclusion
Cette intégration permet une gestion centralisée des utilisateurs et des accès via Keycloak, simplifiant la sécurisation des applications Spring Boot tout en garantissant une expérience utilisateur fluide.

