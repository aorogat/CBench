package compiling;

import qa.dataStructures.Question;

public class QueryParsingTools {
	//Calculate the number of tuples in the query
	static int getNumberOfTuples(String query) {
		int ind = query.indexOf("{");
		int result = 0;
		
		
		char[] temp = query.substring(ind).toCharArray();
		
		for(int i = 0; i< temp.length; i++) {
			try {
				if(temp[i] == '.' && temp[i+1] == ' ')
					result++;
			}
			catch(Exception e) {}
			
			try {
				if(temp[i] == '.' && temp[i+1] == '}')
					result++;
			}
			catch(Exception e) {}
			
			try {
				if(temp[i] == '.' && temp[i+1] == '}')
					result++;
			}
			catch(Exception e) {}
			
			try {
				if(temp[i] == '>' && temp[i+1] == ' ' && temp[i+2] == '}')
					result++;
			}
			catch(Exception e) {}
			
			try {
				if(Character.isLetter(temp[i]) && temp[i+1] == '}')
					result++;
			}
			catch(Exception e) {}
			
			try {
				if(Character.isLetter(temp[i]) && temp[i+1] == ' ' && temp[i+2] == '}')
					result++;
			}
			catch(Exception e) {}

			
		}

		return result;
	}

	//Calculate which combinations of operators are found in the query
	static void operatorDistribution(Question q, boolean full) {
		String query = q.getQuestionQuery();

		if(full)
			QACompiler.operatordistributioncounterfull ++;
		else
			QACompiler.operatordistributioncounter ++;
		
		boolean filter, and, opt, graph, union, limit, min, orderby, offset, sum, groupby, having, notexists, asc, desc, count;
		filter = query.toLowerCase().contains("filter");
		limit = query.toLowerCase().contains("limit");
		opt = query.toLowerCase().contains("optional");
		min = query.toLowerCase().contains("min");
		union = query.toLowerCase().contains("union");
		and = query.toLowerCase().contains(" and ");
		orderby = query.toLowerCase().contains("order by");
		offset = query.toLowerCase().contains("offset");
		graph = query.toLowerCase().contains("graph");
		sum = query.toLowerCase().contains("sum");
		groupby = query.toLowerCase().contains("group by");
		having = query.toLowerCase().contains("having");
		notexists = query.toLowerCase().contains("not exists");
		asc = query.toLowerCase().contains("asc(");
		desc = query.toLowerCase().contains("desc(");
		count = query.toLowerCase().contains("count");

		if(!filter && !and && !opt && !graph && !union&& !limit && !min  && !orderby&& !offset && !graph && !groupby && !having && !notexists && !asc && !desc && !sum && !count) {
			int temp = CompilerLogs.operatorDistribution.put("none", 0);
			temp ++;
			
			if(full) {
				CompilerLogs.operatorDistributionFull.put("none", temp);
			}
			else {
				CompilerLogs.operatorDistribution.put("none", temp);
			}
			
			return;
		}
		for(int i = 0; i < SparqlInfo.commonkeywordscombo.size(); i++) {

			if(SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("filter") == filter && 
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("optional") == opt &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("graph") == graph &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("union") == union && 
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("limit") == limit &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("min") == min &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("and") == and &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("order by") == orderby &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("offset") == offset &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("graph") == graph &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("sum") == sum &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("group by") == groupby &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("having") == having &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("not exists") == notexists &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("asc(") == asc &&
				SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("count") == count
				&& SparqlInfo.commonkeywordscombo.get(i).toLowerCase().contains("desc(") == desc)  {
					int temp = CompilerLogs.operatorDistribution.put(SparqlInfo.commonkeywordscombo.get(i), 0);
					temp ++;
					if(full)
						CompilerLogs.operatorDistributionFull.put(SparqlInfo.commonkeywordscombo.get(i), temp);
					else
						CompilerLogs.operatorDistribution.put(SparqlInfo.commonkeywordscombo.get(i), temp);
					return;
			}
		}

		System.out.println("ALERT: " + query+"\n\n");
	}
}
