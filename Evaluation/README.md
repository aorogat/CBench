[Home](https://github.com/aelroby/CBench/)
# CBench: QA Evaluation Mode
This document shows you how to configure CBench to evaluate a QA system and what you expect from CBench.
* __Run CBench.jar__ Using the command ``` java -jar "PATH/TO/projectFolder/CBench.jar" ```, run the project or change the current directory in the terminal to the directory where CBench located and run this command ``` java -jar CBench.jar ```.
* __Configure CBench__ This is the first step after running CBench. You have to configure the system as shown. *You can use the same answers used in the benchmark file to compare to other system by bypassing the update module and select no. In this case it is your responsability to the select the correct version of KG in your QA system.*
![Image](Images/conf.png)
* The System then preprocess the questions and print all of them as in the figure below.  QALD-1 has 100 questions, just 3 of them are shown.
![Image](Images/Preprosessing.png)
* The System then prints some statistics as shown.
![Image](Images/Statistics.png)
* CBench system collects the QA system answers. For every question, CBench prints the NLQ, Correct answers list and System answers list. It is better to unwrap text as some answers are very long as in the second image.
![Image](Images/collect.png)
![Image](Images/qaquestions.png)
* After CBench feeding the QA system with all questions, It will ask you if you would like to see the evaluation of the individual questions. 
![Image](Images/yes-no.png)
Please select `yes` if you would like to take (Copy/Paste in Spreadsheet) these questions for your own analysis. If `yes` is selected, you will see a list of questions as in the image. (R: Recall, P: Precision and F1: F1 score for each question).
![Image](Images/individual.png)
* CBench will ask you if you would like to see the evaluation of the individual questions categorized by their queries' shapes. Please select `yes` if you would like. 
![Image](Images/yes-no2.png)
In that case, as some shapes are subsuming others, we print questions for every shape that does not belong to other shapes.
![Image](Images/individualCategorizzed.png)
* Then the final scores are reported
![Image](Images/finlScores.png)
* Then you can visualize the properties of the questions that are correctly/incorrectly answered.
![Image](Images/visulize.png)
Then the data will be visualized by Matplotlib as shown. The images will appear one by one. To see the next image please close the current one.
![Image](Images/single.png)
![Image](Images/chain.png)
![Image](Images/chainset.png)
![Image](Images/tree.png)
![Image](Images/star.png)
![Image](Images/forest.png)
![Image](Images/flower.png)
![Image](Images/flowerset.png)

## User-Defined Benchmark
If you would like to evaluate the QA system based on your own benchmark, Open the ```usrDefined.json``` file from the ```data``` folder on your project and replace the questions inside it with your questions using the same format.
![Image](Images/userdefined.png)
 Do not forget, choose option 19 when configuring CBench.
![Image](Images/userdefined2.png)
 
 ## Debugging Benchmark Mode
 If you would like to select a set of questions that have specific properties, select option `20- Properties Defined` while configuring the system. Then, CBench will ask you about the properties of the targeted questions as shown.
 ![Image](Images/properties.png)
For that option, you can select one or more shapes for the targeted queries, determine the minimum and the maximum number of triples and select one or more NLQ types for the targeted questions. In this example, we selected __Chain__ and __Star__ shapes. To exclude __Single Edge__ queries, we set the minimum number of triples to __2__ and to include complex questions, we set the maximum number of triples to __10__. Concerning the NLQ type, we selected the first six types.
