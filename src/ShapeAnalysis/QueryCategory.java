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
                || !isAOF(queryString)) {
            return false;
        }
        return true;
    }

    public static boolean isCQ_F(String queryString) //CQ_F: Conjunctive Query with simple filter
    {
        if (queryString.toLowerCase().contains("optional")
                || !isAOF(queryString)) {
            return false;
        }
        return true;
    }

    public static boolean isCQ_OF(String queryString) //CQ_OF: Conjunctive Query with simple filter and OPT
    {
        if (!isAOF(queryString)) {
            return false;
        }
        return true;
    }

}
