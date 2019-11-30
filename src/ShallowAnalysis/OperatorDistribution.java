package ShallowAnalysis;

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
                                U = 0, UF = 0, AU = 0, AUF = 0, CPF_U = 0;

    public OperatorDistribution() {
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates();
    }

    public static void main(String[] args) {
        OperatorDistribution k = new OperatorDistribution();
        k.analysis();
    }

    public void analysis() {
        NumberFormat formatter = new DecimalFormat("#0.00");

        for (Query q : qs) {
            boolean FILTER = q.toString().toLowerCase().contains("filter"), 
                    AND = q.toString().toLowerCase().contains(" ."), 
                    OPT = q.toString().toLowerCase().contains("opt"), 
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
            
        }
        
        
        CPF = none + A + F + AF;
        CPF_O = O + OF + AO + AOF;
        CPF_G = G;
        CPF_U = U + UF + AU + AUF;
        
        for (Query q : qs) {
            System.out.println(q.toString());
        }
        
        System.out.println(none);
         System.out.println(F);
         System.out.println(A);
         System.out.println(AF);
         System.out.println(CPF);
        
         System.out.println(O);
         System.out.println(OF);
         System.out.println(AO);
         System.out.println(AOF);
         System.out.println(CPF_O);
        
         System.out.println(G);
         System.out.println(CPF_G);
        
         System.out.println(U);
         System.out.println(UF);
         System.out.println(AU);
         System.out.println(AUF);
         System.out.println(CPF_U);
         
         System.out.println("");
        
         System.out.println(formatter.format(Double.valueOf(none)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(F)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(A)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(AF)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(CPF)/qs.size()*100) );
        
         System.out.println(formatter.format(Double.valueOf(O)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(OF)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(AO)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(AOF)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(CPF_O)/qs.size()*100) );
        
         System.out.println(formatter.format(Double.valueOf(G)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(CPF_G)/qs.size()*100) );
        
         System.out.println(formatter.format(Double.valueOf(U)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(UF)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(AU)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(AUF)/qs.size()*100) );
         System.out.println(formatter.format(Double.valueOf(CPF_U)/qs.size()*100) );
                
    }


}
