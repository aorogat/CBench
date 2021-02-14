[<< Home](https://github.com/aorogat/CBench/)

# QA Offline Evaluation by a benchmark file
You can use this mode if your QA  system does not support HTTP requests. In this mode,  CBench outputs a file that includes the questions from the benchmark selected by you with empty fields corresponding to the answers for each question.Your QA system must update this file with the answers. Then CBench can calculate the quality scores and generate the interactive evaluation report for you.

Here are the steps to use this mode
* Ask CBench for the required benchmark file

* Fill the empty answers fields by your QA system

* Return the updated files to CBench to calculate the scores
