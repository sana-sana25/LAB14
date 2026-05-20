# LAB 14 - SecureStorageLabJava

Application Android Java démontrant les mécanismes de persistance locale sécurisée sous Android :

- SharedPreferences
- EncryptedSharedPreferences
- Internal Storage
- JSON
- Cache
- External App-Specific Storage

## Objectif

Le projet montre comment :
- sauvegarder des préférences utilisateur,
- protéger un token sensible,
- manipuler des fichiers internes,
- utiliser un cache temporaire,
- nettoyer les données localement.

---
# Technologies utilisées

- Java
- Android SDK
- AndroidX Security Crypto
- JSON
- SharedPreferences
- Android Keystore

# Fonctionnalités

## SharedPreferences
Sauvegarde :
- nom utilisateur,
- langue,
- thème.

## EncryptedSharedPreferences
Stockage sécurisé du token :
- chiffrement AES256,
- Android Keystore,
- aucune fuite dans Logcat.

## Internal Storage
Création de :
- `note.txt`
- `students.json`

## JSON
Sauvegarde et lecture d’une liste d’étudiants :
- id
- nom
- âge



## Nettoyage complet
Le bouton **Effacer** supprime :
- SharedPreferences
- SecurePrefs
- fichiers internes
- cache

---

# Architecture

```text
com.example.securestoragelabjava
│
├── ui
├── prefs
├── files
├── cache
├── external
└── model
```

---

# Sécurité

L’application applique plusieurs bonnes pratiques :

- aucun token affiché dans Logcat,
- token stocké via `EncryptedSharedPreferences`,
- utilisation de `MODE_PRIVATE`,
- UTF-8 imposé,
- cache réservé aux données temporaires,
- nettoyage complet des données.

---

# Vérification Logcat

Les données non sensibles apparaissent dans les logs :

```text
Prefs chargées name=sana, lang=en, theme=dark
```

Le token n’est jamais affiché en clair.

Seule sa longueur apparaît :

```text
tokenLength=14
```
<img width="1916" height="1013" alt="image" src="https://github.com/user-attachments/assets/a09eb71b-5ee8-4c43-a2fe-1fd908379e97" />

---
# Vidéo démonstrative


https://github.com/user-attachments/assets/06a3bf01-fe5f-4194-b30a-fa72e6b89732



---



---

# Auteur

ASSEKNOUR Sana
ENSA Marrakech
Génie Cyberdéfense et Systèmes de Télécommunications 
