package DataSet;

import NLQAnalysis.OneNLQAnalysis;
import ShapeAnalysis.QueryShapeType;
import ShapeAnalysis.QuestionByQuestionAnalysis;
import java.util.ArrayList;
import java.util.Scanner;
import qa.dataStructures.Question;
import systemstesting.SystemEvaluation;

public class PropertiesDefinedBenchmark {

    public static ArrayList<Question> unwantedQuestions = new ArrayList<>();

    //Query Shape type
    public static final int SINGLE_EDGE = 1;
    public static final int CHAIN = 2;
    public static final int CHAINSET = 3;
    public static final int CYCLE = 4;
    public static final int TREE = 5;
    public static final int STAR = 6;
    public static final int FOREST = 7;
    public static final int FLOWER = 8;
    public static final int FLOWERSET = 9;

    //Question type
    public static final int YES_NO = 1;
    public static final int REQUEST = 2;
    public static final int HOW = 3;
    public static final int WHAT = 4;
    public static final int WHO = 5;
    public static final int WHICH = 6;
    public static final int WHERE = 7;
    public static final int WHEN = 8;
    public static final int WHY = 9;

    public static ArrayList<Integer> queryshapes = new ArrayList<>();
    public static ArrayList<Integer> questionTypes = new ArrayList<>();

    public static int minNoOfTriples = 1;
    public static int maxNoOfTriples = 1;

    public static int maxNoOfquestions = 100;

    public static void getSatisfiedQuestions(ArrayList<Question> questions) {
        Scanner in = new Scanner(System.in);
        System.out.println("        |");
        System.out.println("        ++++> Select the queries shapes for the questions to be used (Enter the integer value) from");
        System.out.println("                [1- Single-Edge, \t2- Chain, \t\t3- Chain Set,");
        System.out.println("                 4- Cycle, \t\t5- Tree, \t\t6- Star, ");
        System.out.println("                 7- Forest, \t\t8- Flower, \t\t9- Flower Ser]");
        System.out.println("               If you would like to select multiple shapes just enter all their numbers.");
        System.out.println("               For example to select Cycle and tree, enter 45.");
        System.out.print("               queries shapes are: ");
        int shapesInt = in.nextInt();

        if (shapesInt < 1) {
            getSatisfiedQuestions(questions);
        }

        while (shapesInt != 0) {
            int next = shapesInt % 10;
            if (next == 0) {
                continue;
            }
            queryshapes.add(next);
            shapesInt /= 10;
        }

        if (!(queryshapes.size() == 1 && queryshapes.contains(1))) {
            System.out.println("        |");
            System.out.println("        ++++> What is the minimum number of triples that each query has a value >= it?");
            System.out.print("               Triples Mimimum number is: ");
            minNoOfTriples = in.nextInt();

            System.out.println("        |");
            System.out.println("        ++++> What is the maximum number of triples that each query has a value <= it?");
            System.out.print("               Triples Maximum number is: ");
            maxNoOfTriples = in.nextInt();
        }
        System.out.println("        |");
        System.out.println("        ++++> Select the question type for the questions to be used (Enter the integer value) from");
        System.out.println("                [1- YES_NO, \t\t2- REQUEST, \t\t3- HOW,");
        System.out.println("                 4- WHAT, \t\t5- WHO, \t\t6- WHICH, ");
        System.out.println("                 7- WHERE, \t\t8- WHEN, \t\t9- WHY]");
        System.out.println("               If you would like to select multiple shapes just enter all their numbers.");
        System.out.println("               For example to select WHAT, WHEN and YES_NO, enter 481.");
        System.out.print("               queries shapes are: ");
        int qesInt = in.nextInt();

        if (qesInt < 1) {
            getSatisfiedQuestions(questions);
        }

        while (qesInt != 0) {
            int next = qesInt % 10;
            if (next == 0) {
                continue;
            }
            questionTypes.add(next);
            qesInt /= 10;
        }

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            //Remove unsupported KG
            if (SystemEvaluation.KB.toLowerCase().equals("dbpedia")
                    && !question.getDatabase().toLowerCase().startsWith("dbpedia")) {
                unwantedQuestions.add(question);
                continue;
            }
            if (SystemEvaluation.KB.toLowerCase().equals("freebase")
                    && !question.getDatabase().toLowerCase().startsWith("freebase")) {
                unwantedQuestions.add(question);
                continue;
            }

            //Remove unsupported query shapes
            boolean required = false;
            try {
                if (queryshapes.contains(SINGLE_EDGE)) {
                    if (QueryShapeType.isSingleEdge(question.getQuestionQuery())) {
                        required = true;
                    }
                }
                if (queryshapes.contains(CHAIN)) {
                    if (QueryShapeType.isChain(question.getQuestionQuery())) {
                        required = true;
                    }
                }
                if (queryshapes.contains(CHAINSET)) {
                    if (QueryShapeType.isChainSet(question.getQuestionQuery())) {
                        required = true;
                    }
                }
                if (queryshapes.contains(CYCLE)) {
                    if (QueryShapeType.isCycle(question.getQuestionQuery())) {
                        required = true;
                    }
                }
                if (queryshapes.contains(TREE)) {
                    if (QueryShapeType.isTree(question.getQuestionQuery())) {
                        required = true;
                    }
                }
                if (queryshapes.contains(STAR)) {
                    if (QueryShapeType.isStar(question.getQuestionQuery())) {
                        required = true;
                    }
                }
                if (queryshapes.contains(FOREST)) {
                    if (QueryShapeType.isForest(question.getQuestionQuery())) {
                        required = true;
                    }
                }
                if (queryshapes.contains(FLOWER)) {
                    if (QueryShapeType.isFlower(question.getQuestionQuery())) {
                        required = true;
                    }
                }
                if (queryshapes.contains(FLOWERSET)) {
                    if (QueryShapeType.isFlowerSet(question.getQuestionQuery())) {
                        required = true;
                    }
                }

                if (!required) {
                    unwantedQuestions.add(question);
                }

                //Remove unsupported query no.of triples
                int noOfTriples = QuestionByQuestionAnalysis.QuestionQueryAnalysis(question).triples;
                if (noOfTriples < minNoOfTriples || noOfTriples > maxNoOfTriples) {
                    unwantedQuestions.add(question);
                    continue;
                }

            } catch (Exception e) {
                unwantedQuestions.add(question);
                continue;
            }

            //Remove unsupported question types
            required = false;
            try {
                if (questionTypes.contains(YES_NO)) //User needs YES_NO
                {
                    if (OneNLQAnalysis.yesNoQuestion(question.getQuestionString())) {
                        required = true;
                    }
                }
                if (questionTypes.contains(REQUEST)) {
                    if (OneNLQAnalysis.requestQuestion(question.getQuestionString())) {
                        required = true;
                    }
                }

                if (questionTypes.contains(WHAT)) {
                    if (OneNLQAnalysis.whatQuestion(question.getQuestionString())) {
                        required = true;
                    }
                }
                if (questionTypes.contains(WHEN)) {
                    if (OneNLQAnalysis.whenQuestion(question.getQuestionString())) {
                        required = true;
                    }
                }
                if (questionTypes.contains(WHERE)) {
                    if (OneNLQAnalysis.whereQuestion(question.getQuestionString())) {
                        required = true;
                    }
                }
                if (questionTypes.contains(WHICH)) {
                    if (OneNLQAnalysis.whichQuestion(question.getQuestionString())) {
                        required = true;
                    }
                }
                if (questionTypes.contains(WHO)) {
                    if (OneNLQAnalysis.whoQuestion(question.getQuestionString())) {
                        required = true;
                    }
                }
                if (questionTypes.contains(WHY)) {
                    if (OneNLQAnalysis.whyQuestion(question.getQuestionString())) {
                        required = true;
                    }
                }
                if (questionTypes.contains(HOW)) {
                    if (OneNLQAnalysis.howQuestion(question.getQuestionString())) {
                        required = true;
                    }
                }

                if (!required) {
                    unwantedQuestions.add(question);
                }
            } catch (Exception e) {
                unwantedQuestions.add(question);
                continue;
            }

        }

        questions.removeAll(unwantedQuestions);

    }

    public static void main(String[] args) {
        DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.PropertiesDefined);
    }

}
