package NEWQA;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import ShapeAnalysis.QueryProperties;
import ShapeAnalysis.QueryShapeType;
import static ShapeAnalysis.QuestionByQuestionAnalysis.QuestionQueryAnalysis;
import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;
import qa.dataStructures.Question;

public class QuestionShape {

    static ArrayList<Question> questions;
    static ArrayList<Question> singleShape_Qs = new ArrayList<Question>();
    static ArrayList<Question> chain_Qs = new ArrayList<Question>();
    static ArrayList<Question> chainSet_Qs = new ArrayList<Question>();
    static ArrayList<Question> star_Qs = new ArrayList<Question>();
    static ArrayList<Question> tree_Qs = new ArrayList<Question>();
    static ArrayList<Question> forest_Qs = new ArrayList<Question>();
    static ArrayList<Question> cycle_Qs = new ArrayList<Question>();
    static ArrayList<Question> flower_Qs = new ArrayList<Question>();
    static ArrayList<Question> flowerSet_Qs = new ArrayList<Question>();

    public static void getQuestions() {
        DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_9);
        questions = DataSetPreprocessing.questionsWithoutDuplicates;

        for (Question q : questions) {
            String queryString = q.getQuestionQuery();
            try {
                Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
                String current = query.toString();
                if (QueryShapeType.isSingleEdge(current)) {
                    singleShape_Qs.add(q);
                } else if (QueryShapeType.isChain(current)) {
                    chain_Qs.add(q);
                } else if (QueryShapeType.isChainSet(current)) {
                    chainSet_Qs.add(q);
                } else if (QueryShapeType.isStar(current)) {
                    star_Qs.add(q);
                } else if (QueryShapeType.isTree(current)) {
                    tree_Qs.add(q);
                } else if (QueryShapeType.isForest(current)) {
                    forest_Qs.add(q);
                } else if (QueryShapeType.isCycle(current)) {
                    cycle_Qs.add(q);
                } else if (QueryShapeType.isFlower(current)) {
                    flower_Qs.add(q);
                } else if (QueryShapeType.isFlowerSet(current)) {
                    flowerSet_Qs.add(q);
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void printQuestions(ArrayList<Question> qs, String ListName) {
        System.out.println(ListName + " ===========================================>");
        for (Question q : qs) {
            System.out.print(q.getQuestionString()
                    + "\t" + q.getQuestionQuery() + "\t");
            
            QueryProperties properties = QuestionQueryAnalysis(q);
            System.out.println(properties.triples  + "\t" + properties.type + "\t" + properties.keywords);
        }
    }

    public static void main(String[] args) {
        getQuestions();
        printQuestions(flowerSet_Qs, "Flower Set");
        printQuestions(flower_Qs, "Flower");
        printQuestions(cycle_Qs, "Cycle");
        printQuestions(forest_Qs, "Forest");
        printQuestions(tree_Qs, "Tree");
        printQuestions(star_Qs, "Star");
        printQuestions(chainSet_Qs, "Chain Set");
        printQuestions(chain_Qs, "Chain");
        printQuestions(singleShape_Qs, "Single");
    }

}
