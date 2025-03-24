# Objectif 
meten pratique les competqnce apppris dans la 1 ere partie Keycloak_Init;
**creer :** 
- un realm : par exemple realm_test_1
- deux client : Client1 et Client2
va dans Clients > Create
IdClient 
```bash
Client_1
```
Name
```bash
Client_1
```
Description
```bash
Client_1
```
Root URL 
```bash
http://localhost:8081
```
Home URL 
```bash
http://localhost:8081/home
```
Valid redirect URIs
```bash
http://localhost:8081/*
```
Web origins : pour specifier les domaines (origines) autorisee a faire des requetes CROS 
```bash
http://localhost:8081
```
Valid post logout redirect URIs
```bash
http://localhost:8081/home
```
set the client authentification ON 
Autherisation ON 


**Set Roles**
va dans le client et ajouter les roles : 

**create users**

**assigner les roles to users**

**creer scoop and mappers**

## Configuration Client Application is the spring-boot application 
 1- Ajouter les depandance : voir POM.xml 
 2- ajuster le ficheir application.properties avec les donees du client keycloak (the secreat key est dans clients > votre client > credentials)




