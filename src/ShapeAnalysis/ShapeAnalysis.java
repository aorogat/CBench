package ShapeAnalysis;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ShapeAnalysis {

    ArrayList<Query> qs;

    public ShapeAnalysis(ArrayList<Query> queries) {
        qs = queries;
    }

    public static void main(String[] args) {
        ShapeAnalysis analysis = new ShapeAnalysis(DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.GraphQuestions));

        System.out.println("This Program is to analyze the queries shapes for total"
                + " queries = " + analysis.qs.size());
        System.out.println("==================================================");

        analysis.analysis();
    }

    //CQ Analysis
    public void analysis() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        int CQ = 0;
        int singleShape_CQ = 0;
        int chain_CQ = 0;
        int chainSet_CQ = 0;
        int star_CQ = 0;
        int tree_CQ = 0;
        int forest_CQ = 0;
        int cycle_CQ = 0;
        int flower_CQ = 0;
        int flowerSet_CQ = 0;
        for (Query q : qs) {
            if (QueryCategory.isCQ(q.toString())) {
                CQ++;
                if (QueryShapeType.isSingleEdge(q.toString())) {
                    singleShape_CQ++;
                }
                if (QueryShapeType.isChain(q.toString())) {
                    chain_CQ++;
                }
                if (QueryShapeType.isChainSet(q.toString())) {
                    chainSet_CQ++;
                }
                if (QueryShapeType.isStar(q.toString())) {
                    star_CQ++;
                }
                if (QueryShapeType.isTree(q.toString())) {
                    tree_CQ++;
                }
                if (QueryShapeType.isForest(q.toString())) {
                    forest_CQ++;
                }
                if (QueryShapeType.isCycle(q.toString())) {
                    cycle_CQ++;
                }
                if (QueryShapeType.isFlower(q.toString())) {
                    flower_CQ++;
                }
                if (QueryShapeType.isFlowerSet(q.toString())) {
                    flowerSet_CQ++;
                }
            }
        }
        int CQf = 0;
        int singleShape_CQf = 0;
        int chain_CQf = 0;
        int chainSet_CQf = 0;
        int star_CQf = 0;
        int tree_CQf = 0;
        int forest_CQf = 0;
        int cycle_CQf = 0;
        int flower_CQf = 0;
        int flowerSet_CQf = 0;
        for (Query q : qs) {
            if (QueryCategory.isCQ_F(q.toString())) {
                CQf++;
                if (QueryShapeType.isSingleEdge(q.toString())) {
                    singleShape_CQf++;
                }
                if (QueryShapeType.isChain(q.toString())) {
                    chain_CQf++;
                }
                if (QueryShapeType.isChainSet(q.toString())) {
                    chainSet_CQf++;
                }
                if (QueryShapeType.isStar(q.toString())) {
                    star_CQf++;
                }
                if (QueryShapeType.isTree(q.toString())) {
                    tree_CQf++;
                }
                if (QueryShapeType.isForest(q.toString())) {
                    forest_CQf++;
                }
                if (QueryShapeType.isCycle(q.toString())) {
                    cycle_CQf++;
                }
                if (QueryShapeType.isFlower(q.toString())) {
                    flower_CQf++;
                }
                if (QueryShapeType.isFlowerSet(q.toString())) {
                    flowerSet_CQf++;
                }
            }
        }
        int CQof = 0;
        int singleShape_CQof = 0;
        int chain_CQof = 0;
        int chainSet_CQof = 0;
        int star_CQof = 0;
        int tree_CQof = 0;
        int forest_CQof = 0;
        int cycle_CQof = 0;
        int flower_CQof = 0;
        int flowerSet_CQof = 0;
        for (Query q : qs) {
            if (QueryCategory.isCQ_OF(q.toString())) {
                CQof++;
                if (QueryShapeType.isSingleEdge(q.toString())) {
                    singleShape_CQof++;
                }
                if (QueryShapeType.isChain(q.toString())) {
                    chain_CQof++;
                }
                if (QueryShapeType.isChainSet(q.toString())) {
                    chainSet_CQof++;
                }
                if (QueryShapeType.isStar(q.toString())) {
                    star_CQof++;
                }
                if (QueryShapeType.isTree(q.toString())) {
                    tree_CQof++;
                }
                if (QueryShapeType.isForest(q.toString())) {
                    forest_CQof++;
                }
                if (QueryShapeType.isCycle(q.toString())) {
                    cycle_CQof++;
                }
                if (QueryShapeType.isFlower(q.toString())) {
                    flower_CQof++;
                }
                if (QueryShapeType.isFlowerSet(q.toString())) {
                    flowerSet_CQof++;
                }
            }
        }
        
        System.out.println("\n\n\nShape Analysis ----->");
        String format = "%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n";
        System.out.format(format, "Shape" +"\t", "#CQ_Queries" +"\t", "Relative%" +"\t", "#CQ_f_Queries" +"\t", "Relative%" +"\t", "#CQ_of_Queries" +"\t", "Relative%");
        System.out.format(format, "=======" +"\t", "========" +"\t", "=========" +"\t", "========" +"\t", "=========" +"\t", "========" +"\t", "=========");
        System.out.format(format, "Single Edge"  +"\t", 
                singleShape_CQ  +"\t", formatter.format((singleShape_CQ / (double) CQ) * 100) +"\t",  
                singleShape_CQf  +"\t", formatter.format((singleShape_CQf / (double) CQf) * 100) +"\t", 
                singleShape_CQof  +"\t", formatter.format((singleShape_CQof / (double) CQof) * 100));
        
        System.out.format(format, "Chain"  +"\t", 
                chain_CQ  +"\t", formatter.format((chain_CQ / (double) CQ) * 100) +"\t",  
                chain_CQf  +"\t", formatter.format((chain_CQf / (double) CQf) * 100) +"\t", 
                chain_CQof  +"\t", formatter.format((chain_CQof / (double) CQof) * 100));
        
        System.out.format(format, "Chain Set" +"\t", 
                chainSet_CQ  +"\t", formatter.format((chainSet_CQ / (double) CQ) * 100) +"\t",  
                chainSet_CQf  +"\t", formatter.format((chainSet_CQf / (double) CQf) * 100) +"\t", 
                chainSet_CQof  +"\t", formatter.format((chainSet_CQof / (double) CQof) * 100));
        
        System.out.format(format, "Star"  +"\t", 
                star_CQ  +"\t", formatter.format((star_CQ / (double) CQ) * 100) +"\t",  
                star_CQf  +"\t", formatter.format((star_CQf / (double) CQf) * 100) +"\t", 
                star_CQof  +"\t", formatter.format((star_CQof / (double) CQof) * 100));
        
        System.out.format(format, "Tree"  +"\t", 
                tree_CQ  +"\t", formatter.format((tree_CQ / (double) CQ) * 100) +"\t",  
                tree_CQf  +"\t", formatter.format((tree_CQf / (double) CQf) * 100) +"\t", 
                tree_CQof  +"\t", formatter.format((tree_CQof / (double) CQof) * 100));
        
        System.out.format(format, "Forest"  +"\t", 
                forest_CQ  +"\t", formatter.format((forest_CQ / (double) CQ) * 100) +"\t",  
                forest_CQf  +"\t", formatter.format((forest_CQf / (double) CQf) * 100) +"\t", 
                forest_CQof  +"\t", formatter.format((forest_CQof / (double) CQof) * 100));
        
        System.out.format(format, "Cycle"  +"\t", 
                cycle_CQ  +"\t", formatter.format((cycle_CQ / (double) CQ) * 100) +"\t",  
                cycle_CQf  +"\t", formatter.format((cycle_CQf / (double) CQf) * 100) +"\t", 
                cycle_CQof  +"\t", formatter.format((cycle_CQof / (double) CQof) * 100));
        
        System.out.format(format, "Flower"  +"\t", 
                flower_CQ  +"\t", formatter.format((flower_CQ / (double) CQ) * 100) +"\t",  
                flower_CQf  +"\t", formatter.format((flower_CQf / (double) CQf) * 100) +"\t", 
                flower_CQof  +"\t", formatter.format((flower_CQof / (double) CQof) * 100));
        
        System.out.format(format, "Flower Set"  +"\t", 
                flowerSet_CQ  +"\t", formatter.format((flowerSet_CQ / (double) CQ) * 100) +"\t",  
                flowerSet_CQf  +"\t", formatter.format((flowerSet_CQf / (double) CQf) * 100) +"\t", 
                flowerSet_CQof  +"\t", formatter.format((flowerSet_CQof / (double) CQof) * 100));
        
        
        
    }
   
}
