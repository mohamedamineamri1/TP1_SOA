# TP1 : Modélisation XML et Interrogation XPath avec Maven en Java

## Informations générales

| | |
|---|---|
| **Nom et prénom** | [AMRI] [Mohamed Amine ] |
| **Filière** | LSI3 |
| **Matière** | Architecture SOA et services web |
| **Établissement** | Faculté des Sciences de Sfax, Département d'informatique et des Communications |
| **Année universitaire** | 2025-2026 |
| **Enseignantes** | Amel Corine ZAYANI, Hela HAKIM, Rim MOHAMED |
| **Domaine choisi** | Gestion d'un cinéma |

---

## 1. Objectifs

À l'issue de ce TP, il s'agit de :

- Choisir un domaine réel et le modéliser en XML.
- Créer un schéma XSD pour valider la structure du document XML.
- Créer un projet Maven en Java.
- Implémenter la validation XML/XSD.
- Interroger le document XML avec XPath.

**Outils utilisés :** Eclipse IDE, Java JDK >= 1.8 , Maven, API `javax.xml` (JAXP, XPath).

---

## 2. Structure du projet

```
tp1-xml-xpath/
├── pom.xml
└── src/main/
    ├── java/tn/fsf/soa/
    │   ├── Main.java            (point d'entrée du programme)
    │   ├── XmlValidator.java    (fonctionnalité 1 : validation XML/XSD)
    │   └── XPathQuery.java      (fonctionnalité 2 : requêtes XPath)
    └── resources/
        ├── cinema.xml           (données)
        └── cinema.xsd           (schéma de validation)
```

### Rôle de chaque fichier

| Fichier | Rôle |
|---|---|
| `pom.xml` | Configuration Maven : version de Java (1.8), encodage UTF-8, plugin d'exécution |
| `cinema.xml` | Document XML contenant les films, les salles et les utilisateurs |
| `cinema.xsd` | Schéma définissant la structure et les contraintes du XML |
| `XmlValidator.java` | Valide le XML par rapport au XSD et affiche les erreurs avec leur ligne |
| `XPathQuery.java` | Charge le XML en DOM et évalue les requêtes XPath |
| `Main.java` | Lance la validation, puis les requêtes XPath si le document est valide |

### Modélisation XML

```
cinema
├── films
│   └── film (id, genre, disponible)
│       ├── titre, realisateur, annee, duree, note
├── salles
│   └── salle (id)
│       ├── nom, capacite
└── utilisateurs
    └── utilisateur (id, actif)
        ├── nom, prenom, email
```

**Choix de modélisation :**
- Les **attributs** (`id`, `genre`, `disponible`, `actif`) représentent les identifiants et les états (métadonnées).
- Les **éléments** (`titre`, `annee`, `note`, etc.) représentent les données descriptives.

### Exécution

Depuis Eclipse : clic droit sur `Main.java` > `Run As` > `Java Application`.

Depuis le terminal :
```
mvn compile exec:java
```

---

## 3. Explication des concepts

### XML
Langage de balisage qui sert à décrire et échanger des données de façon structurée. Un document est **bien formé** si sa syntaxe est correcte (balises fermées, imbriquées, une seule racine).

### XSD (XML Schema Definition)
Schéma qui décrit la structure attendue d'un document XML. Un document est **valide** s'il respecte ce schéma. Éléments utilisés :

| Concept | Rôle dans le projet |
|---|---|
| `xs:simpleType` + `xs:restriction` | Contraindre une valeur (note entre 0 et 10, année, genre dans une liste) |
| `xs:pattern` | Valider l'email avec une expression régulière |
| `xs:complexType` + `xs:sequence` | Imposer les sous-éléments et leur ordre |
| `xs:attribute use="required"` | Rendre un attribut obligatoire |
| `xs:ID` | Garantir l'unicité des identifiants |
| `maxOccurs="unbounded"` | Autoriser plusieurs occurrences d'un élément |

### Validation XML/XSD (JAXP)
- `SchemaFactory` : charge le schéma XSD.
- `Schema` : représentation du schéma compilé.
- `Validator` : vérifie le document XML par rapport au schéma.
- `ErrorHandler` : collecte toutes les erreurs avec le numéro de ligne.

### XPath
Langage de requête permettant de sélectionner des nœuds dans un document XML.

- `XPathFactory` : crée le moteur XPath.
- `XPathExpression` : requête compilée.
- `XPathConstants.NODESET` : résultat sous forme de liste de nœuds.
- `XPathConstants.NUMBER` : résultat numérique (`count`, `sum`).

### Exemples de requêtes XPath

| Requête | Résultat |
|---|---|
| `//film/titre` | Tous les titres de films |
| `//utilisateur[@actif='true']/nom` | Noms des utilisateurs actifs |
| `//film[@disponible='true']/titre` | Films disponibles |
| `//film[@genre='Science-fiction']/titre` | Films de science-fiction |
| `//film[annee > 2010]/titre` | Films sortis après 2010 |
| `//film[note >= 8.5]/titre` | Films avec une note d'au moins 8.5 |
| `count(//film)` | Nombre total de films |
| `sum(//film/note) div count(//film)` | Note moyenne |
| `sum(//salle/capacite)` | Capacité totale des salles |

### Maven
Outil de gestion de projet Java. Le fichier `pom.xml` décrit la configuration du projet. L'API `javax.xml` fait partie du JDK : aucune dépendance externe n'est nécessaire.

---

## 4. Résultats

- La validation du document `cinema.xml` par `cinema.xsd` réussit.
- Les requêtes XPath renvoient les résultats attendus (titres, utilisateurs actifs, statistiques).
- Les erreurs volontaires (note à 15, genre inconnu, élément manquant, identifiant dupliqué) sont bien détectées avec le numéro de ligne.


## 5. Conclusion

Ce TP m'a permis de réaliser la chaîne complète de traitement d'un document XML : **modéliser** un domaine réel, **valider** sa structure avec un schéma XSD, puis **l'interroger** avec XPath dans un projet Maven.

J'ai compris la différence entre un document *bien formé* et un document *valide*, ainsi que l'intérêt de contraindre les données (types, énumérations, expressions régulières, unicité des identifiants). J'ai aussi vu que l'API JAXP, incluse dans le JDK, suffit pour ces deux fonctionnalités.

Ces notions sont fondamentales en architecture SOA : le XML sert de format d'échange entre services, et le XSD joue le rôle de **contrat** qui permet à un service de vérifier un message avant de le traiter.
