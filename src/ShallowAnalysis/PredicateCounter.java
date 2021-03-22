/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShallowAnalysis;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.jena.graph.Node;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementSubQuery;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;

/**
 *
 * @author aorogat
 */
public class PredicateCounter {

    ArrayList<Query> qs;

    public PredicateCounter(ArrayList<Query> queries) {
        qs = queries;
    }

    public static void main(String[] args) {

        PredicateCounter k = new PredicateCounter(DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.LC_QUAD));
        k.count();
    }

    private void count() {

        final Set<String> resource = new HashSet<String>();
        final Set<String> ontology = new HashSet<String>();
        final Set<String> property = new HashSet<String>();
        for (Query q : qs) {
            try {
                // This will walk through all parts of the query
                ElementWalker.walk(q.getQueryPattern(),
                        // For each element...
                        new ElementVisitorBase() {
                            @Override
                            public void visit(ElementPathBlock el) {
                                // ...go through all the triples...
                                Iterator<TriplePath> triples = el.patternElts();
                                while (triples.hasNext()) {
                                    TriplePath triple = triples.next();
                                    String s, o, p;
                                    s = triple.getSubject().toString();
                                    o = triple.getObject().toString();
                                    p = triple.getPredicate().toString();
                                    if (s.startsWith("http://dbpedia.org/ontology/")) {
                                        ontology.add(s);
                                    } else if (s.startsWith("http://dbpedia.org/property/")) {
                                        property.add(s);
                                    } else if (s.startsWith("http://dbpedia.org/resource/")) {
                                        resource.add(s);
                                    }

                                    if (p.startsWith("http://dbpedia.org/ontology/")) {
                                        ontology.add(p);
                                    } else if (p.startsWith("http://dbpedia.org/property/")) {
                                        property.add(p);
                                    } else if (p.startsWith("http://dbpedia.org/resource/")) {
                                        resource.add(p);
                                    }

                                    if (o.startsWith("http://dbpedia.org/ontology/")) {
                                        ontology.add(o);
                                    } else if (o.startsWith("http://dbpedia.org/property/")) {
                                        property.add(o);
                                    } else if (o.startsWith("http://dbpedia.org/resource/")) {
                                        resource.add(o);
                                    }
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
                );
            } catch (Exception e) {
            }
        }

        System.out.print(resource.size() + ": ");
        System.out.println(resource.toString());
        System.out.print(property.size() + ": ");
        System.out.println(property.toString());
        System.out.print(ontology.size() + ": ");
        System.out.println(ontology.toString());
    }

}
