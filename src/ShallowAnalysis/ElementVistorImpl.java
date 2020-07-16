package ShallowAnalysis;

import java.util.Iterator;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementSubQuery;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;

public class ElementVistorImpl extends ElementVisitorBase {
    
    @Override
    public void visit(ElementPathBlock el) {
        // ...go through all the triples...
        Iterator<TriplePath> triples = el.patternElts();
        while (triples.hasNext()) {
            TriplePath triple = triples.next();
            NoOfTriples.counter++;
        }
    }
    
    @Override
    public void visit(ElementSubQuery el) {
        Query q = el.getQuery();
        Element e = q.getQueryPattern();

        ElementVisitorBase visitor = new ElementVistorImpl();
        ElementWalker.walk(e, visitor);
        
    }
    
}
