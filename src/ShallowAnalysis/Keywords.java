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
public class Keywords 
{
    ArrayList<Query> qs;
    
    public Keywords()
    {
        qs = DataSetPreprocessing.getQueriesWithoutDuplicates();
    }
    
    public static void main(String[] args) {
        Keywords k = new Keywords();
        k.keywordsAnalysis();
    }
    
    public void keywordsAnalysis()
    {
        NumberFormat formatter = new DecimalFormat("#0.00");
        
        int select = 0, ask = 0, describe = 0, construct = 0;
        
        int distinct = 0, limit = 0, offset = 0, orderBy = 0;
        
        int filter = 0,and = 0,union = 0,opt = 0,graph = 0,notExists = 0,minus = 0,exists = 0;
        
        int aggregators = 0, groupBy = 0, having = 0;
        
        for (Query q : qs) {
            if(q.isSelectType()) select++;
            else if(q.isAskType()) ask++;
            else if(q.isDescribeType()) describe++;
            else if(q.isConstructType()) construct++;
            
            if(q.isDistinct()) distinct++;
            if(q.hasLimit()) limit++;
            if(q.hasOffset()) offset++;
            if(q.hasOrderBy()) orderBy++;
            
            if(q.toString().toLowerCase().contains("filter")) filter++;
            if(q.toString().toLowerCase().contains(" .")) and++;
            if(q.toString().toLowerCase().contains("union")) union++;
            if(q.toString().toLowerCase().contains("opt")) opt++;
            if(q.toString().toLowerCase().contains("graph")) graph++;
            if(q.toString().toLowerCase().contains("not exists")) notExists++;
            else if(q.toString().toLowerCase().contains("exists")) exists++;
            if(q.toString().toLowerCase().contains("minus")) minus++;
            
            if(q.hasAggregators()) aggregators++;
            if(q.hasGroupBy()) groupBy++;
            if(q.hasHaving()) having++;
            
        
        }
        
        //System.out.println("\\begin{longtable}");
        //System.out.println("\\caption{Keywords count in queries}");
        //System.out.println("\\centering");
        System.out.println("\\begin{tabular}{lll}");
        System.out.println("\\hline \\hline");
        System.out.println("& "+ "QALD 1" +" & \\\\");
        System.out.println("\\hline \\hline");
        System.out.println("Element       & \\#Queries & Relative\\%   \\\\ \\hline");
        System.out.println("Select & " + select + "    & " + formatter.format((Double.valueOf(select)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Asks & " + ask + "    & " + formatter.format((Double.valueOf(ask)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Describe & " + describe + "    & " + formatter.format((Double.valueOf(describe)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Construct & " + construct + "    & " + formatter.format((Double.valueOf(construct)/Double.valueOf(qs.size()))*100)+"\\% \\\\ \\hline");
        System.out.println("Distinct & " + distinct + "    & " + formatter.format((Double.valueOf(distinct)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Limit & " + limit + "    & " + formatter.format((Double.valueOf(limit)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Offset & " + offset + "    & " + formatter.format((Double.valueOf(offset)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Order By & " + orderBy + "    & " + formatter.format((Double.valueOf(orderBy)/Double.valueOf(qs.size()))*100)+"\\% \\\\ \\hline");
        System.out.println("Filter & " + filter + "    & " + formatter.format((Double.valueOf(filter)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("And & " + and + "    & " + formatter.format((Double.valueOf(and)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Union & " + union + "    & " + formatter.format((Double.valueOf(union)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Opt & " + opt + "    & " + formatter.format((Double.valueOf(opt)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Not Exists & " + notExists + "    & " + formatter.format((Double.valueOf(notExists)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Minus & " + minus + "    & " + formatter.format((Double.valueOf(minus)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Exists & " + exists + "    & " + formatter.format((Double.valueOf(exists)/Double.valueOf(qs.size()))*100)+"\\% \\\\ \\hline");
        System.out.println("Aggregators & " + aggregators + "    & " + formatter.format((Double.valueOf(aggregators)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Group By & " + groupBy + "    & " + formatter.format((Double.valueOf(groupBy)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("Having & " + having + "    & " + formatter.format((Double.valueOf(having)/Double.valueOf(qs.size()))*100)+"\\% \\\\");
        System.out.println("\\end{tabular}");
        //System.out.println("\\end{longtable}");
        System.out.println("");
        
        
        System.out.println(select);
        System.out.println(ask);
        System.out.println(describe);
        System.out.println(construct);
        System.out.println(distinct);
        System.out.println(limit);
        System.out.println(offset);
        System.out.println(orderBy);
        System.out.println(filter);
        System.out.println("and" + and);
        System.out.println(union);
        System.out.println(opt);
        System.out.println(notExists);
        System.out.println(minus);
        System.out.println(exists);
        System.out.println(aggregators);
        System.out.println(groupBy);
        System.out.println(having);
        System.out.println("");
        System.out.println(formatter.format((Double.valueOf(select)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(ask)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(describe)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(construct)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(distinct)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(limit)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(offset)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(orderBy)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(filter)/Double.valueOf(qs.size()))*100));
        System.out.println("and" + formatter.format((Double.valueOf(and)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(union)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(opt)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(notExists)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(minus)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(exists)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(aggregators)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(groupBy)/Double.valueOf(qs.size()))*100));
        System.out.println(formatter.format((Double.valueOf(having)/Double.valueOf(qs.size()))*100));
    }
}
