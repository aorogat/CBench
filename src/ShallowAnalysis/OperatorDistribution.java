package ShallowAnalysis;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.jena.query.Query;

/**
 *
 * @author Abdelghny Orogat
 */
public class OperatorDistribution 
{
    ArrayList<Query> qs;
    public static int none = 0, F = 0, A = 0, AF = 0, CPF = 0, 
                                O = 0, OF = 0, AO = 0, AOF = 0, CPF_O = 0,
                                G = 0, CPF_G = 0,
                                U = 0, UF = 0, AU = 0, AUF = 0, CPF_U = 0,
                                FGP = 0, FGU = 0, FAG = 0, AOUF = 0, AOUFG = 0;

    public OperatorDistribution() {
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_1);
    }

    public static void main(String[] args) {
        OperatorDistribution k = new OperatorDistribution();
        k.analysis();
    }

    public void analysis() {
        NumberFormat formatter = new DecimalFormat("#0.00");

        for (Query q : qs) {
            boolean FILTER = q.toString().toLowerCase().contains("filter"), 
                    
                    AND = q.toString().toLowerCase().contains(" .")
                     ||q.toString().toLowerCase().contains(" ;"), 
                    
                    OPT = q.toString().toLowerCase().contains("optional"),
                    
                    GRAPH = q.toString().toLowerCase().contains("graph"), 
                    
                    UNION = q.toString().toLowerCase().contains("union");
            
            if(FILTER && !AND && !OPT && !GRAPH && !UNION)F++;
            if(!FILTER && AND && !OPT && !GRAPH && !UNION)A++;
            if(FILTER && AND && !OPT && !GRAPH && !UNION)AF++;
            
            if(!FILTER && !AND && OPT && !GRAPH && !UNION)O++;
            if(FILTER && !AND && OPT && !GRAPH && !UNION)OF++;
            if(!FILTER && AND && OPT && !GRAPH && !UNION)AO++;
            if(FILTER && AND && OPT && !GRAPH && !UNION)AOF++;
            
            if(!FILTER && !AND && !OPT && GRAPH && !UNION)G++;
            
            if(!FILTER && !AND && !OPT && !GRAPH && UNION)U++;
            if(FILTER && !AND && !OPT && !GRAPH && UNION)UF++;
            if(!FILTER && AND && !OPT && !GRAPH && UNION)AU++;
            if(FILTER && AND && !OPT && !GRAPH && UNION)AUF++;
            
            if(!FILTER && !AND && !OPT && !GRAPH && !UNION)none++;
            
            
            if(FILTER && !AND && OPT && GRAPH && !UNION)FGP++;
            if(FILTER && !AND && !OPT && GRAPH && UNION)FGU++;
            if(FILTER && AND && !OPT && GRAPH && !UNION)FAG++;
            if(FILTER && AND && OPT && !GRAPH && UNION)AOUF++;
            if(FILTER && AND && OPT && GRAPH && UNION)AOUFG++;
            
//            System.out.println(q.toString());
//            System.out.println("==============");
//            System.out.println("Filter = "+ FILTER + "AND = "+ AND + "OPT = "+ OPT + "Graph = "+GRAPH +"Union = "+ UNION );
//            
        }
        
        
        CPF = none + A + F + AF;
        CPF_O = O + OF + AO + AOF;
        CPF_G = G;
        CPF_U = U + UF + AU + AUF;
         
         
        System.out.println("\\begin{tabular}{lll}");
        System.out.println("\\hline \\hline");
        System.out.println("& "+ "QALD 1" +" & \\\\");
        System.out.println("\\hline \\hline");
        System.out.println("Operators       & \\#Queries & Relative\\%   \\\\ \\hline");
        
         System.out.println("none & "+ none + "    & " + formatter.format(Double.valueOf(none)/qs.size()*100) +"\\% \\\\" );
         System.out.println("F & " + F + "    & " +  formatter.format(Double.valueOf(F)/qs.size()*100) +"\\% \\\\" );
         System.out.println("A & " + A + "    & " +  formatter.format(Double.valueOf(A)/qs.size()*100) +"\\% \\\\" );
         System.out.println("A, F & " + AF + "    & " +  formatter.format(Double.valueOf(AF)/qs.size()*100) +"\\% \\\\" );
         System.out.println("CPF & " + CPF + "    & " +  formatter.format(Double.valueOf(CPF)/qs.size()*100) +"\\% \\\\" );
        
         System.out.println("O & " + O + "    & " +  formatter.format(Double.valueOf(O)/qs.size()*100) +"\\% \\\\" );
         System.out.println("O, F & " + OF + "    & " +  formatter.format(Double.valueOf(OF)/qs.size()*100) +"\\% \\\\" );
         System.out.println("A, O & " + AO + "    & " +  formatter.format(Double.valueOf(AO)/qs.size()*100) +"\\% \\\\" );
         System.out.println("A, O, F & " + AOF + "    & " +  formatter.format(Double.valueOf(AOF)/qs.size()*100) +"\\% \\\\" );
         System.out.println("CPF + O & +" + CPF_O + "    & +" +  formatter.format(Double.valueOf(CPF_O)/qs.size()*100) +"\\% \\\\" );
        
         System.out.println("G & " + G + "    & " +  formatter.format(Double.valueOf(G)/qs.size()*100) +"\\% \\\\" );
         System.out.println("CPF + G & +" + CPF_G + "    & +" +  formatter.format(Double.valueOf(CPF_G)/qs.size()*100) +"\\% \\\\" );
        
         System.out.println("U & " + U + "    & " +  formatter.format(Double.valueOf(U)/qs.size()*100) +"\\% \\\\" );
         System.out.println("UF & " + UF + "    & " +  formatter.format(Double.valueOf(UF)/qs.size()*100) +"\\% \\\\" );
         System.out.println("AU & " + AU + "    & " +  formatter.format(Double.valueOf(AU)/qs.size()*100) +"\\% \\\\" );
         System.out.println("AUF & " + AUF + "    & " +  formatter.format(Double.valueOf(AUF)/qs.size()*100) +"\\% \\\\" );
         System.out.println("CPF + U & +" + CPF_U + "    & +" +  formatter.format(Double.valueOf(CPF_U)/qs.size()*100) +"\\% \\\\" );
                
        System.out.println("\\end{tabular}");
        
        
        System.out.println(formatter.format(Double.valueOf(none)/qs.size()*100) +"\\%" );
         System.out.println(formatter.format(Double.valueOf(F)/qs.size()*100) +"\\%" );
         System.out.println(formatter.format(Double.valueOf(A)/qs.size()*100) +"\\%" );
         System.out.println(formatter.format(Double.valueOf(AF)/qs.size()*100) +"\\%" );
         System.out.println(formatter.format(Double.valueOf(CPF)/qs.size()*100) +"\\%" );
        
         System.out.println(formatter.format(Double.valueOf(O)/qs.size()*100) +"\\%" );
         System.out.println(formatter.format(Double.valueOf(OF)/qs.size()*100) +"\\%" );
         System.out.println(formatter.format(Double.valueOf(AO)/qs.size()*100) +"\\%" );
         System.out.println(formatter.format(Double.valueOf(AOF)/qs.size()*100) +"\\%" );
         System.out.println("'+"+formatter.format(Double.valueOf(CPF_O)/qs.size()*100) +"\\%" );
        
         System.out.println(formatter.format(Double.valueOf(G)/qs.size()*100) +"\\%" );
         System.out.println("'+"+formatter.format(Double.valueOf(CPF_G)/qs.size()*100) +"\\%" );
        
         System.out.println(formatter.format(Double.valueOf(U)/qs.size()*100) +"\\%" );
         System.out.println(formatter.format(Double.valueOf(UF)/qs.size()*100) +"\\%" );
         System.out.println(formatter.format(Double.valueOf(AU)/qs.size()*100) +"\\%" );
         System.out.println(formatter.format(Double.valueOf(AUF)/qs.size()*100) +"\\%" );
         System.out.println("'+"+formatter.format(Double.valueOf(CPF_U)/qs.size()*100) +"\\%" );
                
         System.out.println("FGP = "+FGP);
         System.out.println("FGU = "+FGU);
         System.out.println("FAG = "+FAG);
         System.out.println("AOUF = "+AOUF);
    }


}
