package tn.fsf.soa;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlValidator {

    public static boolean valider(String xmlPath, String xsdPath) {
        try (InputStream xsd = XmlValidator.class.getResourceAsStream(xsdPath);
             InputStream xml = XmlValidator.class.getResourceAsStream(xmlPath)) {

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();

            // Collecter toutes les erreurs
            final List<String> erreurs = new ArrayList<>();
            validator.setErrorHandler(new ErrorHandler() {
                public void warning(SAXParseException e) {
                    erreurs.add("Ligne " + e.getLineNumber() + " : " + e.getMessage());
                }
                public void error(SAXParseException e) {
                    erreurs.add("Ligne " + e.getLineNumber() + " : " + e.getMessage());
                }
                public void fatalError(SAXParseException e) throws SAXException {
                    throw e;
                }
            });

            validator.validate(new StreamSource(xml));

            if (erreurs.isEmpty()) {
                System.out.println("[OK] Le document XML est VALIDE.");
                return true;
            }
            System.out.println("[ERREUR] Le document XML est INVALIDE :");
            erreurs.forEach(e -> System.out.println("  - " + e));
            return false;

        } catch (SAXException e) {
            System.out.println("[ERREUR] Document INVALIDE : " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erreur technique : " + e.getMessage());
            return false;
        }
    }
}