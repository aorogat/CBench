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
static boolean subquery = false;
    
    @Override
    public void visit(ElementPathBlock el) {
        
        
        // ...go through all the triples...
        Iterator<TriplePath> triples = el.patternElts();
        while (triples.hasNext()) {
            TriplePath triple = triples.next();
            NoOfTriples.counter++;
        }
        if(subquery)
        {
            subquery = false;
            return;
        }
        switch (NoOfTriples.counter) {
            case 0:
                NoOfTriples.zero++;
                break;
            case 1:
                NoOfTriples.one++;
                break;
            case 2:
                NoOfTriples.two++;
                break;
            case 3:
                NoOfTriples.three++;
                break;
            case 4:
                NoOfTriples.four++;
                break;
            case 5:
                NoOfTriples.five++;
                break;
            case 6:
                NoOfTriples.six++;
                break;
            case 7:
                NoOfTriples.seven++;
                break;
            case 8:
                NoOfTriples.eight++;
                break;
            case 9:
                NoOfTriples.nine++;
                break;
            case 10:
                NoOfTriples.ten++;
                break;
            default:
                NoOfTriples.elevenOrMore++;
                break;

        }
    }
    
    @Override
    public void visit(ElementSubQuery el) {
        subquery = true;
        Query q = el.getQuery();
        Element e = q.getQueryPattern();

        System.out.println(e.toString());
        System.out.println("===============================");
        ElementVisitorBase visitor = new ElementVistorImpl();
        ElementWalker.walk(e, visitor);
        
    }
    
}
