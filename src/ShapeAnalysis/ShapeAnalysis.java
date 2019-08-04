package ShapeAnalysis;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import DataSet.AllQueries;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ShapeAnalysis {
    ArrayList<Query> qs;
    
    public ShapeAnalysis()
    {
        qs = AllQueries.getAllQueries();
    }

    public static void main(String[] args) {
        ShapeAnalysis analysis = new ShapeAnalysis();
        
        System.out.println("This Program is to analyze the queries shapes for total"
                + " queries = " + analysis.qs.size());
        System.out.println("/////////////////////////////////////////////////////////");
        System.out.println("/////////////////////////////////////////////////////////");
        
        analysis.CQ_Analysis();
        System.out.println("/////////////////////////////////////////////////////////");
        System.out.println("/////////////////////////////////////////////////////////");
        analysis.CQ_F_Analysis();
        System.out.println("/////////////////////////////////////////////////////////");
        System.out.println("/////////////////////////////////////////////////////////");
        analysis.CQ_OF_Analysis();
    }
    
    //CQ Analysis
    public void CQ_Analysis()
    {
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
                if (QueryShapeType.isSingleEdge(q.toString()))
                    singleShape_CQ++;
                if (QueryShapeType.isChain(q.toString()))
                    chain_CQ++;
                if (QueryShapeType.isChainSet(q.toString()))
                    chainSet_CQ++;
                if (QueryShapeType.isStar(q.toString()))
                    star_CQ++;
                if (QueryShapeType.isTree(q.toString()))
                    tree_CQ++;
                if (QueryShapeType.isForest(q.toString()))
                    forest_CQ++;
                if (QueryShapeType.isCycle(q.toString()))
                    cycle_CQ++;
                if (QueryShapeType.isFlower(q.toString()))
                    flower_CQ++;
                if (QueryShapeType.isFlowerSet(q.toString()))
                    flowerSet_CQ++;
            }
        }
        System.out.println("\t\t CQ (" + CQ + " Queries)");
        System.out.println("-----------------------------------------------");
        System.out.println("Shape \t\t #Queries \t Relative %");
        System.out.println("-----------------------------------------------");
        System.out.println("Single Edge \t "+ singleShape_CQ +" \t\t " + formatter.format((Double.valueOf(singleShape_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Chain \t\t "+ chain_CQ +" \t\t " + formatter.format((Double.valueOf(chain_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Chain Set \t "+ chainSet_CQ +" \t\t " + formatter.format((Double.valueOf(chainSet_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Star \t\t "+ star_CQ +" \t\t " + formatter.format((Double.valueOf(star_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Tree \t\t "+ tree_CQ +" \t\t " + formatter.format((Double.valueOf(tree_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Forest \t\t "+ forest_CQ +" \t\t " + formatter.format((Double.valueOf(forest_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("-----------------------------------------------");
        System.out.println("Cycle \t\t "+ cycle_CQ +" \t\t " + formatter.format((Double.valueOf(cycle_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Flower \t\t "+ flower_CQ +" \t\t " + formatter.format((Double.valueOf(flower_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Flower Set \t "+ flowerSet_CQ +" \t\t " + formatter.format((Double.valueOf(flowerSet_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("===============================================");
        System.out.println("Total \t\t "+ CQ +" \t\t 100.0%");
    }
    
    //CQ_F Analysis
    public void CQ_F_Analysis()
    {
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
            if (QueryCategory.isCQ_F(q.toString())) {
                CQ++;
                if (QueryShapeType.isSingleEdge(q.toString()))
                    singleShape_CQ++;
                if (QueryShapeType.isChain(q.toString()))
                    chain_CQ++;
                if (QueryShapeType.isChainSet(q.toString()))
                    chainSet_CQ++;
                if (QueryShapeType.isStar(q.toString()))
                    star_CQ++;
                if (QueryShapeType.isTree(q.toString()))
                    tree_CQ++;
                if (QueryShapeType.isForest(q.toString()))
                    forest_CQ++;
                if (QueryShapeType.isCycle(q.toString()))
                    cycle_CQ++;
                if (QueryShapeType.isFlower(q.toString()))
                    flower_CQ++;
                if (QueryShapeType.isFlowerSet(q.toString()))
                    flowerSet_CQ++;
            }
        }
        System.out.println("\t\t CQ_F (" + CQ + " Queries)");
        System.out.println("-----------------------------------------------");
        System.out.println("Shape \t\t #Queries \t Relative %");
        System.out.println("-----------------------------------------------");
        System.out.println("Single Edge \t "+ singleShape_CQ +" \t\t " + formatter.format((Double.valueOf(singleShape_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Chain \t\t "+ chain_CQ +" \t\t " + formatter.format((Double.valueOf(chain_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Chain Set \t "+ chainSet_CQ +" \t\t " + formatter.format((Double.valueOf(chainSet_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Star \t\t "+ star_CQ +" \t\t " + formatter.format((Double.valueOf(star_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Tree \t\t "+ tree_CQ +" \t\t " + formatter.format((Double.valueOf(tree_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Forest \t\t "+ forest_CQ +" \t\t " + formatter.format((Double.valueOf(forest_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("-----------------------------------------------");
        System.out.println("Cycle \t\t "+ cycle_CQ +" \t\t " + formatter.format((Double.valueOf(cycle_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Flower \t\t "+ flower_CQ +" \t\t " + formatter.format((Double.valueOf(flower_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Flower Set \t "+ flowerSet_CQ +" \t\t " + formatter.format((Double.valueOf(flowerSet_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("===============================================");
        System.out.println("Total \t\t "+ CQ +" \t\t 100.0%");
    }
    
    //CQ_OF Analysis
    public void CQ_OF_Analysis()
    {
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
            if (QueryCategory.isCQ_OF(q.toString())) {
                CQ++;
                if (QueryShapeType.isSingleEdge(q.toString()))
                    singleShape_CQ++;
                if (QueryShapeType.isChain(q.toString()))
                    chain_CQ++;
                if (QueryShapeType.isChainSet(q.toString()))
                    chainSet_CQ++;
                if (QueryShapeType.isStar(q.toString()))
                    star_CQ++;
                if (QueryShapeType.isTree(q.toString()))
                    tree_CQ++;
                if (QueryShapeType.isForest(q.toString()))
                    forest_CQ++;
                if (QueryShapeType.isCycle(q.toString()))
                    cycle_CQ++;
                if (QueryShapeType.isFlower(q.toString()))
                    flower_CQ++;
                if (QueryShapeType.isFlowerSet(q.toString()))
                    flowerSet_CQ++;
            }
        }
        System.out.println("\t\t CQ_OF (" + CQ + " Queries)");
        System.out.println("-----------------------------------------------");
        System.out.println("Shape \t\t #Queries \t Relative %");
        System.out.println("-----------------------------------------------");
        System.out.println("Single Edge \t "+ singleShape_CQ +" \t\t " + formatter.format((Double.valueOf(singleShape_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Chain \t\t "+ chain_CQ +" \t\t " + formatter.format((Double.valueOf(chain_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Chain Set \t "+ chainSet_CQ +" \t\t " + formatter.format((Double.valueOf(chainSet_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Star \t\t "+ star_CQ +" \t\t " + formatter.format((Double.valueOf(star_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Tree \t\t "+ tree_CQ +" \t\t " + formatter.format((Double.valueOf(tree_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Forest \t\t "+ forest_CQ +" \t\t " + formatter.format((Double.valueOf(forest_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("-----------------------------------------------");
        System.out.println("Cycle \t\t "+ cycle_CQ +" \t\t " + formatter.format((Double.valueOf(cycle_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Flower \t\t "+ flower_CQ +" \t\t " + formatter.format((Double.valueOf(flower_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("Flower Set \t "+ flowerSet_CQ +" \t\t " + formatter.format((Double.valueOf(flowerSet_CQ)/Double.valueOf(CQ))*100) + "%");
        System.out.println("===============================================");
        System.out.println("Total \t\t "+ CQ +" \t\t 100.0%");
    }

}
