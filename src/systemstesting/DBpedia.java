package systemstesting;
import UptodatAnswers.CuratedAnswer;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;


/**
 *
 * @author aorogat
 */
public class DBpedia {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String query = "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
"PREFIX dct: <http://purl.org/dc/terms/>\n" +
"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
"SELECT * WHERE {\n" +
"  ?resource foaf:name ?answer .\n" +
"  ?resource rdfs:label ?label .\n" +
"  FILTER(LANG(?label) = \"en\") .\n" +
"  ?resource dct:subject dbr:Category:Superhero_film_characters .\n" +
"  FILTER(! strStarts(LCASE(?label), LCASE(?answer))).\n" +
"  VALUES ?resource { <http://dbpedia.org/resource/Captain_America> } .\n" +
"} \n" +
"ORDER BY ?resource";
        ArrayList ans = CuratedAnswer.upToDateAnswerDBpedia(query, "dbpedia");
        System.out.println(ans.toString());
    }
}
