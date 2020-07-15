# CBench
CBench is an extensible and more informative benchmarking framework for evaluating question answering systems over knowledge graphs. CBench facilitates this evaluation using a set of popular benchmarks that can be augmented with other user-provided benchmarks. CBench not only evaluates a question answering system
based on popular single-number metrics, but also gives a detailed analysis of the syntactic and linguistic properties of answered and unanswered questions to better help the developers of question answering systems developers to better understand where their system excels and struggles.

This is an implementation for the ACM SIGMOD International Conference on Management of Data, June 20–25, 2021, paper [CBench: Towards Better Evaluation of Question Answering Over Knowledge Graphs](http://github.com)

### Features
* __Benchmarks Fine-grained Analysis:__ CBench studies several syntactical and linguistic features of a predefined benchmark or a new benchmark to be added by the user.
* __Predefined Benchmarks:__ The currecnt version of CBench supports 17 Benchmarks, 12 of them have their crossponding SPARQL queries.
* __Detailed QA system Evaluation:__ CBench is not support single number evaluation but rather F1-Macro, Micro and Global(With different thresholds) scores. Theses scores are defined in the paper.
* __Fine-grained Evaluation Analysis:__ CBench is able to identify the queries properties of the quetsions that are correctly and incorrectly answered.
* __Qualitative Evaluation of Linguistic Features:__ CBench is able to find the *k* linguistically closest questions to a chosen question *q*.



## Getting Started

### Prerequisites
CBench requires the following development kits and liberaries. You can download the liberaries with the system.
* for Java [JDK 8, Apach Jena, ...]
* for Python [Python 3, Numpy, Matplotlib, ...]

### Deploy CBench via jar
* __Download CBench.jar:__ ToDo
* __Unzip CBench.jar:__ ToDo

### Run CBench in Netbeans
ToDo

### Add a New Benchmark
ToDo

### Evaluate a QA system  via http request
To evaluate your system, CBench will send a POST request to your QA system using the following URL
```
url?query=[question]&kb=[knowledge Base]
```
Where *_url_* is the one that your system is running on for example "http://www.aaa.com/QA/". CBench concatenates your *url* with the *Query string ?query=[question]&kb=[knowledge Base]* using the 2 parameters: *_query_* and *_kb_* that are set on the runtime. The *_query_* parameter value determined based on the current question from the chosen benchmark. For the *_kb_* parameter, you configure it at the beginning after running CBench to [default, dbpedia, wikidata or freebase]. *_default_* value means CBench uses the default benchmark for every file of questions. To add a new KG, read the next section.

### Add a New Knowledge Graph
ToDo

## Support
Please raise potential bugs on github. If you have a research related question, please send it to this email(xxx@carleton.ca)



