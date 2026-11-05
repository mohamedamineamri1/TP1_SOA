package tn.fsf.soa;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

public class XPathQuery {

    private final Document doc;
    private final XPath xpath;

    // Le constructeur charge le XML et prépare le moteur XPath
    public XPathQuery(String xmlPath) throws Exception {
        try (InputStream xml = XPathQuery.class.getResourceAsStream(xmlPath)) {
            this.doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(xml);
        }
        this.xpath = XPathFactory.newInstance().newXPath();
    }

    // Requête qui retourne une liste de nœuds
    public void afficherListe(String libelle, String requete) throws Exception {
        NodeList noeuds = (NodeList) xpath.compile(requete)
                .evaluate(doc, XPathConstants.NODESET);

        System.out.println("\n" + libelle + "   [" + requete + "]");
        if (noeuds.getLength() == 0) {
            System.out.println("   (aucun résultat)");
        }
        for (int i = 0; i < noeuds.getLength(); i++) {
            System.out.println("   - " + noeuds.item(i).getTextContent());
        }
    }
    // Requête qui retourne un nombre (count, sum...)
    public void afficherNombre(String libelle, String requete) throws Exception {
        Double valeur = (Double) xpath.compile(requete)
                .evaluate(doc, XPathConstants.NUMBER);

        System.out.println("\n" + libelle + "   [" + requete + "]");
        System.out.println("   = " + valeur);
    }
}