package tn.fsf.soa;

public class Main {

    private static final String XML_FILE = "/cinema.xml";
    private static final String XSD_FILE = "/cinema.xsd";

    public static void main(String[] args) throws Exception {

        System.out.println("========== 1. VALIDATION XML / XSD ==========");
        if (!XmlValidator.valider(XML_FILE, XSD_FILE)) {
            System.out.println("Interrogation XPath annulée.");
            return;
        }

        System.out.println("\n========== 2. INTERROGATION XPATH ==========");
        XPathQuery q = new XPathQuery(XML_FILE);

        q.afficherListe("1) Tous les titres de films", "//film/titre");
        q.afficherListe("2) Utilisateurs actifs", "//utilisateur[@actif='true']/nom");
        q.afficherListe("3) Films disponibles", "//film[@disponible='true']/titre");
        q.afficherListe("4) Films de science-fiction", "//film[@genre='Science-fiction']/titre");
        q.afficherListe("5) Films sortis après 2010", "//film[annee > 2010]/titre");
        q.afficherListe("6) Films avec note >= 8.5", "//film[note >= 8.5]/titre");
        q.afficherNombre("7) Nombre de films", "count(//film)");
        q.afficherNombre("8) Note moyenne", "sum(//film/note) div count(//film)");
        q.afficherNombre("9) Capacité totale des salles", "sum(//salle/capacite)");
    }
}