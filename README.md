---

# 🎯 **Intégration de Keycloak avec une Application Spring Boot et Thymeleaf**

Ce projet de démonstration montre comment intégrer **Keycloak** en tant que fournisseur d'identité (IdP) avec **Spring Security**, permettant l'authentification via **OIDC (OpenID Connect)** et la gestion des rôles d'utilisateur.

---

## 🚀 **Objectifs**

- Authentifier les utilisateurs via **Keycloak**
- Contrôler l'accès aux pages en fonction des rôles
- Tester le flux OIDC complet (Authorization Code Flow)

---

## 🛠️ **Prérequis**

Avant de commencer, assurez-vous d'avoir les éléments suivants installés :

- **Java 17**
- **Maven**
- **Keycloak** installé et configuré via Docker
- **Spring Boot 3.4.3** avec **Thymeleaf**

---

## 🔌 **Ajout des Dépendances Keycloak**

Ajoutez les dépendances suivantes dans votre fichier `pom.xml` :

```xml
<dependencies>
    <!-- Client OAuth2 pour l'intégration avec Keycloak via OIDC -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>

    <!-- Intégration avec Spring Security pour la gestion d'authentification et d'autorisation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
</dependencies>
```

---

## ⚙️ **Configuration de Keycloak**

### **Points Clés de la Configuration**

Dans `application.properties`, configurez Keycloak avec les paramètres suivants :

```properties
# Paramètres Keycloak
keycloak.realm=intelcom_platform
keycloak.auth-server-url=http://localhost:8080
keycloak.resource=client1
keycloak.logout-url=http://localhost:8080/realms/intelcom_platform/protocol/openid-connect/logout

# Configuration OAuth2
spring.security.oauth2.client.registration.keycloak.client-id=client1
spring.security.oauth2.client.registration.keycloak.client-secret=<SECRET>
spring.security.oauth2.client.registration.keycloak.scope=openid
spring.security.oauth2.client.provider.keycloak.issuer-uri=http://localhost:8080/realms/intelcom_platform
```

---

## 🏗️ **Architecture du Projet**

Voici l'architecture du projet :

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

---

## 🚀 **Démarrage du Projet**

### 1. **Démarrer Keycloak**

Lancez Keycloak via Docker :

```sh
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.5 start-dev
```

### 2. **Configurer Keycloak**

1. **Créez un realm** : `intelcom_platform`
2. **Ajoutez un client** : `client1`
 - **Client Type** : OpenID Connect
 - **Valid Redirect URIs** : `http://localhost:8081/*`
3. **Créez des rôles** : `Admin`, `User`
4. **Créez un utilisateur test** (`laila` avec mot de passe) et attribuez-lui des rôles
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

### 3. **Démarrer l'Application Spring Boot**

Exécutez la commande suivante pour démarrer l'application :

```sh
mvn spring-boot:run
```

### 4. **Accéder à l'Application**

Ouvrez votre navigateur et accédez à :

```
http://localhost:8081
```

Connectez-vous avec un utilisateur Keycloak pour accéder à l'application.

---

## 📝 **Points Clés**

- **Authentification centralisée** : Keycloak gère les utilisateurs et les rôles, simplifiant la gestion des identités.
- **Gestion flexible des rôles et permissions** : Administrable directement depuis l'interface Keycloak.

---

## 🏁 **Conclusion**

Cette intégration avec **Keycloak** permet une gestion centralisée des utilisateurs et des accès dans votre application Spring Boot. Vous bénéficiez d'une authentification sécurisée et d'une gestion des rôles facilement configurable, tout en simplifiant la sécurisation des applications et en garantissant une expérience utilisateur fluide.

---

### 🔗 **Ressources supplémentaires :**

- [Documentation Keycloak](https://www.keycloak.org/documentation)
- [Spring Security OAuth2 Client Documentation](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2)
