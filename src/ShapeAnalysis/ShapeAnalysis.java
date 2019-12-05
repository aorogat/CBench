package ShapeAnalysis;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import DataSet.AllQueries;
import DataSet.DataSetPreprocessing;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ShapeAnalysis {

    ArrayList<Query> qs;

    public ShapeAnalysis() {
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates();
    }

    public static void main(String[] args) {
        ShapeAnalysis analysis = new ShapeAnalysis();

        System.out.println("This Program is to analyze the queries shapes for total"
                + " queries = " + analysis.qs.size());
        System.out.println("==================================================");

        analysis.CQ_Analysis();
    }

    //CQ Analysis
    public void CQ_Analysis() {
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
        System.out.println("\\begin{table*}[t]");
        System.out.println("\\caption{Cumulative shape analysis of \\textit{CQ}, \\textit{CQ\\textsubscript{F}}, \\textit{CQ\\textsubscript{OF}} across all logs}");
        System.out.println("\\centering");
        System.out.println("\\begin{tabular}{lllllll}");
        System.out.println("\\hline \\hline");
        System.out.println("& & & " + "QALD 1" + " & & & \\\\");
        System.out.println("\\hline \\hline");
        System.out.println("            & \\textit{CQ}        &            &\\textit{CQ\\textsubscript{F}}        &            &\\textit{CQ\\textsubscript{OF}}           &             \\\\ \\hline");
        System.out.println("Shape       & \\#Queries & Relative\\% & \\#Queries & Relative\\% & \\#Queries & Relative\\%  \\\\ \\hline");
        System.out.println("Single Edge & " + singleShape_CQ + "    & " + formatter.format((Double.valueOf(singleShape_CQ) / Double.valueOf(CQ)) * 100) + "\\%    & " + singleShape_CQf + "      & " + formatter.format((Double.valueOf(singleShape_CQf) / Double.valueOf(CQf)) * 100) + "\\%    & " + singleShape_CQof + "      & " + formatter.format((Double.valueOf(singleShape_CQof) / Double.valueOf(CQof)) * 100) + "\\% \\\\");
        System.out.println("Chain       & " + chain_CQ + "          & " + formatter.format((Double.valueOf(chain_CQ) / Double.valueOf(CQ)) * 100) + "\\%    &       " + chain_CQf + "      & " + formatter.format((Double.valueOf(chain_CQf) / Double.valueOf(CQf)) * 100) + "\\%    & " + chain_CQof + "      & " + formatter.format((Double.valueOf(chain_CQof) / Double.valueOf(CQof)) * 100) + "\\% \\\\ ");
        System.out.println("Chain Set   & " + chainSet_CQ + "       & " + formatter.format((Double.valueOf(chainSet_CQ) / Double.valueOf(CQ)) * 100) + "\\%    &    " + chainSet_CQf + "      & " + formatter.format((Double.valueOf(chainSet_CQf) / Double.valueOf(CQf)) * 100) + "\\%    & " + chainSet_CQof + "      & " + formatter.format((Double.valueOf(chainSet_CQof) / Double.valueOf(CQof)) * 100) + "\\% \\\\");
        System.out.println("Star        & " + star_CQ + "           & " + formatter.format((Double.valueOf(star_CQ) / Double.valueOf(CQ)) * 100) + "\\%    &        " + star_CQf + "      & " + formatter.format((Double.valueOf(star_CQf) / Double.valueOf(CQf)) * 100) + "\\%    & " + star_CQof + "      & " + formatter.format((Double.valueOf(star_CQof) / Double.valueOf(CQof)) * 100) + "\\% \\\\");
        System.out.println("Tree        & " + tree_CQ + "           & " + formatter.format((Double.valueOf(tree_CQ) / Double.valueOf(CQ)) * 100) + "\\%    &        " + tree_CQf + "      & " + formatter.format((Double.valueOf(tree_CQf) / Double.valueOf(CQf)) * 100) + "\\%    & " + tree_CQof + "      & " + formatter.format((Double.valueOf(tree_CQof) / Double.valueOf(CQof)) * 100) + "\\% \\\\");
        System.out.println("Forest      & " + forest_CQ + "         & " + formatter.format((Double.valueOf(forest_CQ) / Double.valueOf(CQ)) * 100) + "\\%    &      " + forest_CQf + "      & " + formatter.format((Double.valueOf(forest_CQf) / Double.valueOf(CQf)) * 100) + "\\%    & " + forest_CQof + "      & " + formatter.format((Double.valueOf(forest_CQof) / Double.valueOf(CQof)) * 100) + "\\% \\\\ \\hline");
        System.out.println("Cycle       & " + cycle_CQ + "          & " + formatter.format((Double.valueOf(cycle_CQ) / Double.valueOf(CQ)) * 100) + "\\%    &       " + cycle_CQf + "      & " + formatter.format((Double.valueOf(cycle_CQf) / Double.valueOf(CQf)) * 100) + "\\%    & " + cycle_CQof + "      & " + formatter.format((Double.valueOf(cycle_CQof) / Double.valueOf(CQof)) * 100) + "\\% \\\\");
        System.out.println("Flower      & " + flower_CQ + "         & " + formatter.format((Double.valueOf(flower_CQ) / Double.valueOf(CQ)) * 100) + "\\%    &      " + flower_CQf + "      & " + formatter.format((Double.valueOf(flower_CQf) / Double.valueOf(CQf)) * 100) + "\\%    & " + flower_CQof + "      & " + formatter.format((Double.valueOf(flower_CQof) / Double.valueOf(CQof)) * 100) + "\\% \\\\");
        System.out.println("Flower Set  & " + flowerSet_CQ + "      & " + formatter.format((Double.valueOf(flowerSet_CQ) / Double.valueOf(CQ)) * 100) + "\\%    &   " + flowerSet_CQf + "      & " + formatter.format((Double.valueOf(flowerSet_CQf) / Double.valueOf(CQf)) * 100) + "\\%    & " + flowerSet_CQof + "      & " + formatter.format((Double.valueOf(flowerSet_CQof) / Double.valueOf(CQof)) * 100) + "\\% \\\\ \\hline");
        System.out.println("Total       & " + CQ + "      & " + "100.0" + "\\%    & " + CQf + "      & " + "100.0" + "\\%    & " + CQof + "      & " + "100.0" + "\\%");
        System.out.println("\\end{tabular}");
        System.out.println("\\end{table*}");
        System.out.println("");
    }
   
}
