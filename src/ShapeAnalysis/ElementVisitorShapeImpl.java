package ShapeAnalysis;

import Graph.Edge;
import java.util.Iterator;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementSubQuery;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;

public class ElementVisitorShapeImpl extends ElementVisitorBase {


    @Override
    public void visit(ElementPathBlock el) {
        Iterator<TriplePath> triples = el.patternElts();
        while (triples.hasNext()) {
            TriplePath triple = triples.next();

            QueryShapeType.graph.edges.add(new Edge(triple.getSubject().toString(), triple.getObject().toString()));
            
            if (!QueryShapeType.graph.vertices.contains(triple.getSubject().toString())) {
                QueryShapeType.graph.vertices.add(triple.getSubject().toString());
            }
            if (!QueryShapeType.graph.vertices.contains(triple.getObject().toString())) {
                QueryShapeType.graph.vertices.add(triple.getObject().toString());
            }
        }
    }

    @Override
    public void visit(ElementSubQuery el) {
        Query q = el.getQuery();
        Element e = q.getQueryPattern();

        ElementVisitorBase visitor = new ElementVisitorShapeImpl();
        ElementWalker.walk(e, visitor);

    }

}
