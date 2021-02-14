# CBench
CBench is an extensible and more informative benchmarking framework for evaluating question answering systems over knowledge graphs. CBench facilitates this evaluation using a set of popular benchmarks that can be augmented with other user-provided benchmarks. CBench not only evaluates a question answering system
based on popular single-number metrics, but also gives a detailed analysis of the syntactic and linguistic properties of answered and unanswered questions to better help the developers of question answering systems developers to better understand where their system excels and struggles.


### Features
* __Benchmarks Fine-grained Analysis:__ CBench studies several syntactical and linguistic features of a predefined benchmark or a new benchmark to be added by the user.
* __Predefined Benchmarks:__ The currecnt version of CBench supports 17 Benchmarks, 12 of them have their crossponding SPARQL queries.
* __Benchmarks Analysis:__ CBench enables you to analyize one of the predefined benchmarks or your own benchmark. The analysis includes the shallow and shape analysis for the SPARQL quries and the natural language analysis.
* __Detailed QA system Evaluation:__ CBench is not support single number evaluation but rather F1-Macro, Micro and Global(With different thresholds) scores. Theses scores are defined in the paper.
* __QA system Evaluation Debugging Mode:__ Within the QA Evaluation Debugging Mode, the user can control CBench's output questions based on any of the linguistic, syntactical or structural features of all the questions and queries in CBench.
* __Fine-grained Evaluation Analysis:__ CBench is able to identify the queries properties of the quetsions that are correctly and incorrectly answered.
* __Qualitative Evaluation of Linguistic Features:__ CBench is able to find the *k* linguistically closest questions to a chosen question *q*.

## Table of Content
* Run CBench
  * Getting Started
  * [Benchmarks Analysis](https://github.com/aelroby/CBench/blob/master/Analysis/README.md)
  * [QA system Evaluation](https://github.com/aelroby/CBench/tree/master/Evaluation)
    * [QA Online Evaluation by http requests](https://github.com/aorogat/CBench/blob/master/Evaluation/httpEvaluation.md)
    * [QA Offline Evaluation by a benchmark file](https://github.com/aorogat/CBench/blob/master/Evaluation/fileEvaluation.md)
    * [QA Evaluation by your own method](https://github.com/aorogat/CBench/blob/master/Evaluation/customizedEvaluation.md)
  * [Add a new Benchmark](https://github.com/aorogat/CBench/blob/master/newBenchmark.md)
  * [Add a Knowledge Graph](https://github.com/aorogat/CBench/blob/master/addKG.md)
* [Edit CBench](https://github.com/aorogat/CBench/blob/master/editCBench.md)
* [Examples for Shapes used by CBench](https://github.com/aorogat/CBench/tree/master/Shapes)
* [Why all versions of QALD?](https://github.com/aorogat/CBench/edit/master/whyAllQALDs.md)



## Getting Started

### Prerequisites
CBench requires the following development kits and liberaries. You can download the liberaries with the system.
* for Java [JDK 8, Download liberaries from Lib folder]
* for Python [Python 3, Numpy, Pandas, Matplotlib, Spacy, Scipy, Statistics]

### Deploy CBench via jar
* __Download CBench.jar:__ Download the *CBench.jar* file and other folders. The project structure must be as follow
```
projectFolder
│   
└─── CBench.jar
│
|─── data
│   |─── userDefinied.json
│   |─── DBpedia
│   |   │─── No_SPARQL
│   |   └─── SPARQL
|   |
│   └─── Freebase
│       │─── No_SPARQL
│       └─── SPARQL
│
└─── lib
|   └─── ... All .jar files
│   
└─── evaluate.py

```
*  __Run CBench.jar:__ Using the command ``` java -jar "PATH/TO/projectFolder/CBench.jar" ```, run the project.
* __Configure the System:__ While the system is running, it asks you about some parameters. Theses parameters are
  * __Mode:__ You can select between two modes: (1) [Benchmarks Analysis](https://github.com/aelroby/CBench/blob/master/Analysis/README.md) or (2) [QA system Evaluation](https://github.com/aelroby/CBench/tree/master/Evaluation).
  * __KG:__ The desired knowledge graph.
* __Start the System:__ After you configured the system, it starts and does the following. __(It is prefered to unwrap the text)__
  * __Questions Preprocessing:__ CBench read the question form the raw files and remove dupicates. All the questions will be printed.
  * __Benchmark Statistics:__ CBench prints questions statistics.

For Benchmark Analysis Mode,
  * __Benchmark:__ The desired benchmark for the analysis must be selected. It can be a predefined benchmark or a userdefined benchmark [See the section "Add a New Benchmark" for how to add your own benchmark].
  * __Print Analysis results:__ CBench prints shallow, shape and linguistic analysis results. 

For QA Evaluation Mode [For how to evaluated a QA system, see section "Evaluate a QA system  via http request"],
  * __Benchmark:__ The desired benchmark for the QA Evaluation must be selected. It can be a predefined benchmark, a user-defined benchmark or Properties Defined benchmark. The Properties Defined option used for the QA Evaluation Debugging Mode.
    * __Properties Defined Benchmark:__ For this option, the CBench asks the user about the required properties and based on the targeted KG, It will collect the questions from the benchmarks that target the selected KG and achieve the user-defined properties. 
Afetr the Benchmark Preparation, CBench will do the following
  * __Collecting Correct answers and Systems answers:__ CBench collects gold answers, feeds QA system with the questions and collects system answers. CBench prints the results per question while it is running.
  * __Final Report:__ CBench prints the evaluated questions then print them again categorized by queries shapes. The values are separated by tabs to be easy for you to paste them in a spreadsheet for your own analysis. After that, it prints the performance scores defined in the paper.
  * __Data Visualization:__ A Python script generates the fine-grained analysis visualization. To do so, you have to setup python on your machine and these libraries: Numpy, Pandas, Matplotlib, Spacy, Scipy and Statistics. CBench asks you for the python3 setup location; for example, if you use anaconda on an ubuntu machine, the path usually is ``` /home/username/anaconda3/bin ```.


## Support
Please raise potential bugs on github. If you have a research related question, please send it to this email(abdelghny.orogat@carleton.ca)



