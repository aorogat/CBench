package ShallowAnalysis;

import DataSet.Benchmark;
import DataSet.DataSetPreprocessing;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.jena.query.Query;

public class Keywords {
    
    ArrayList<Query> qs;

    public Keywords(ArrayList<Query> queries) {
        qs = queries;
    }

    public static void main(String[] args) {
        
        Keywords k = new Keywords(DataSetPreprocessing.getQueriesWithoutDuplicates(Benchmark.QALD_2));
        k.keywordsAnalysis();
    }

    public void keywordsAnalysis() {
        NumberFormat formatter = new DecimalFormat("#0.00");

        int select = 0, ask = 0, describe = 0, construct = 0,
            distinct = 0, limit = 0, offset = 0, orderBy = 0,
            filter = 0, and = 0, union = 0, opt = 0, graph = 0, 
            notExists = 0, minus = 0, exists = 0,
            aggregators = 0, groupBy = 0, having = 0,
            count = 0, max = 0, min = 0, avg = 0, sum = 0, groupconcat = 0, sample = 0;

        for (Query q : qs) {
            if (q.isSelectType()) {
                select++;
            } else if (q.isAskType()) {
                ask++;
            } else if (q.isDescribeType()) {
                describe++;
            } else if (q.isConstructType()) {
                construct++;
            }

            if (q.isDistinct()) {
                distinct++;
            }
            if (q.hasLimit()) {
                limit++;
            }
            if (q.hasOffset()) {
                offset++;
            }
            if (q.hasOrderBy()) {
                orderBy++;
            }

            if (q.toString().toLowerCase().
                    replace("\n", "").replace("\r", "").replaceAll(" ", "").contains("filter(")) {
                filter++;
            }
            if (q.toString().toLowerCase().contains(" .")
                    || q.toString().toLowerCase().contains(";")) {
                and++;
            }
            if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").
                    replace("\n", "").replace("\r", "").contains("union{")) {
                union++;
            }
            if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").
                    replace("\n", "").replace("\r", "").contains("optional{")) {
                opt++;
            }
            if (q.toString().toLowerCase().replaceAll(" ", "").
                    replace("\n", "").replace("\r", "").contains("graph{")) {
                graph++;
            }
            if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").contains("notexists{")) {
                System.out.println("not exists ==="+q.toString());
                notExists++;
            } else if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").contains("exists{")) {
                System.out.println("exists ==="+q.toString());
                exists++;
            }
            if (q.toString().toLowerCase().replace("\n", "").replaceAll(" ", "").contains("minus{")) {
                System.out.println("minus ==="+q.toString());
                minus++;
            }

            if (q.hasGroupBy()) {
                groupBy++;
            }
            if (q.hasHaving()) {
                having++;
            }

            if (q.toString().toLowerCase().contains("count(")) {
                count++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("min(")) {
                min++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("max(")) {
                max++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("sum(")) {
                sum++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("avg(")) {
                avg++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("groupconcat(")) {
                groupconcat++;
                aggregators++;
            }
            if (q.toString().toLowerCase().contains("sample(")) {
                sample++;
                aggregators++;
            }

        }
        
        System.out.println("\n\n\nKeywords Analysis ----->");
        String format = "%-30s%-20s%-20s%n";
        System.out.format(format, "Element", "#Queries", "Relative%");
        System.out.format(format, "=======", "========", "=========");
        System.out.format(format, "Select ", select + "    ",
                formatter.format(((double) select / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Ask ", ask + "    ",
                formatter.format(((double) ask / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Describe ", describe + "    ",
                formatter.format(((double) describe / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Construct ", construct + "    ",
                formatter.format(((double) construct / (double) qs.size()) * 100) + "% ");
        System.out.format(format, "Distinct ", distinct + "    ",
                formatter.format(((double) distinct / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Limit ", limit + "    ",
                formatter.format(((double) limit / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Offset ", offset + "    ",
                formatter.format(((double) offset / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Order By ", orderBy + "    ",
                formatter.format(((double) orderBy / (double) qs.size()) * 100) + "% ");
        System.out.format(format, "Filter ", filter + "    ",
                formatter.format(((double) filter / (double) qs.size()) * 100) + "%");
        System.out.format(format, "And ", and + "    ",
                formatter.format(((double) and / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Union ", union + "    ",
                formatter.format(((double) union / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Opt ", opt + "    ",
                formatter.format(((double) opt / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Not Exists ", notExists + "    ",
                formatter.format(((double) notExists / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Minus ", minus + "    ",
                formatter.format(((double) minus / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Exists ", exists + "    ",
                formatter.format(((double) exists / (double) qs.size()) * 100) + "% ");
        System.out.format(format, "Count ", count + "    ",
                formatter.format(((double) count / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Min ", min + "    ",
                formatter.format(((double) min / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Max ", max + "    ",
                formatter.format(((double) max / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Sum ", sum + "    ",
                formatter.format(((double) sum / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Avg ", avg + "    ",
                formatter.format(((double) avg / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Groupconcat ", groupconcat + "    ",
                formatter.format(((double) groupconcat / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Sample ", sample + "    ",
                formatter.format(((double) sample / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Aggregators ", aggregators + "    ",
                formatter.format(((double) aggregators / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Group By ", groupBy + "    ",
                formatter.format(((double) groupBy / (double) qs.size()) * 100) + "%");
        System.out.format(format, "Having ", having + "    ",
                formatter.format(((double) having / (double) qs.size()) * 100) + "%");
        

    }
}
