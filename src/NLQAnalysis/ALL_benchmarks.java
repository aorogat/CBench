
package NLQAnalysis;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import java.io.IOException;

/**
 *
 * @author aorogat
 */
public class ALL_benchmarks {
    public static void main(String[] args) throws IOException {
        DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.LC_QUAD);
        int total = DataSetPreprocessing.questionsWithoutDuplicates.size();
        
        NLQCategoraizer nlq = new NLQCategoraizer();
        System.out.println("What\t"+nlq.what_Qs.size()/(double)total*100);
        System.out.println("When\t"+nlq.when_Qs.size()/(double)total*100);
        System.out.println("Where\t"+nlq.where_Qs.size()/(double)total*100);
        System.out.println("Which\t"+nlq.which_Qs.size()/(double)total*100);
        System.out.println("Who\t"+nlq.who_Qs.size()/(double)total*100);
        System.out.println("Whom\t"+nlq.whom_Qs.size()/(double)total*100);
        System.out.println("Whose\t"+nlq.whose_Qs.size()/(double)total*100);
        System.out.println("How\t"+nlq.how_Qs.size()/(double)total*100);
        System.out.println("Yes-No\t"+nlq.yes_no_Qs.size()/(double)total*100);
        System.out.println("Request\t"+nlq.request_Qs.size()/(double)total*100);
        System.out.println("Topical\t"+nlq.Topical_Qs.size()/(double)total*100);
        
    }
}
