package ShapeAnalysis;

//Graph treated as a list of edges (Edge Class)

public class QueryCategory extends org.apache.jena.query.Query {

    //Classes of Queries
    private static boolean isAOF(String queryString) //AOF: AND, Optional, Filter
    {
        if (queryString.toLowerCase().contains("graph")
         || queryString.toLowerCase().contains("union")) {
            return false;
        }
        return true;
    }
    
    public static boolean isCQ(String queryString) //CQ: Conjunctive Query
    {
        if (queryString.toLowerCase().contains("filter")
         || queryString.toLowerCase().contains("optional")
         || ! isAOF(queryString)) {
            return false;
        }
        return true;
    }

    public static boolean isCQ_F(String queryString) //CQ_F: Conjunctive Query with simple filter
    {
        if (queryString.toLowerCase().contains("optional")
         || ! isAOF(queryString)) {
            return false;
        }
        return true;
    }

    public static boolean isCQ_OF(String queryString) //CQ_OF: Conjunctive Query with simple filter and OPT (well designed)
    {
         if (! isAOF(queryString)) {
            // if not well designed
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "select ?resource ?name ?label ?freebase where {\n"
                + "  values ?type { dbpedia-owl:Film dbpedia-owl:Person }\n"
                + "  values ?namePart { \"John\" \"Mary\" }\n"
                + "\n"
                + "  ?resource rdfs:label ?label ;\n"
                + "            foaf:name ?name ;\n"
                + "            a ?type ;\n"
                + "            owl:sameAs ?freebase .\n"
                + "\n"
                + "  filter ( langMatches( lang(?label), 'en' ) &&\n"
                + "           strstarts(str(?freebase), \"http://rdf.freebase.com\" ) &&\n"
                + "           contains( ?name, ?namePart ) )\n"
                + "}\n"
                + "limit 10";
        if (isCQ(s)) 
            System.out.println("This Query is CQ");
        else
            System.out.println("This Query is Not CQ");
    }
}
