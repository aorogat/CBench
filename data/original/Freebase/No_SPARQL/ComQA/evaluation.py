'''
  Official evaluation script for ComQA dataset.
  Gold file: question \t [gold]
  Predictions file: question \t [predictions]
'''

import sys
import json


'''
  load data
'''
def load(file_path):
  data = {}
  with open(file_path) as f:
    for line in f:
      tokens = line.strip().split('\t')
      data[tokens[0]] = tokens[1]
  return data

'''
  compute recall, precision, and f1 for one question
'''
def compute_metrics(gold, predictions):

  # empty gold
  if len(gold) == 0:
    if len(predictions) == 0:
      return (1, 1, 1)
    else:
      return (0, 0, 0)

  # empty predictions
  if len(gold) > 0 and len(predictions) == 0:
    return (0,0,0)


  # both lists are not empty
  precision = 0
  for prediction in predictions:
    if prediction in gold:
      precision += 1
  precision = float(precision) / len(predictions)

  recall = 0
  for entry in gold:
    if entry in predictions:
      recall += 1
  recall = float(recall) / len(gold)

  f1 = 0
  if (precision + recall) > 0:
    f1 = 2 * recall * precision / (precision + recall)
  return (precision, recall, f1)


'''
  go over all lines and compute precision, recall and F1
'''
def compute_resutls(gold, predictions):
  average_recall = 0
  average_precision = 0
  average_f1 = 0
  n_questions = 0
  n_correct = 0

  out = open('output.txt', 'w')
  for question in gold.keys():
    out.write(question + '\t')
    gold_set = [x.lower() for x in json.loads(gold[question])]
    gold_set = set(gold_set)
    predictions_set = [x.lower() for x in json.loads(predictions[question])]
    predictions_set = set(predictions_set)
    precision, recall, f1 = compute_metrics(gold_set, predictions_set)
    if f1 == 1:
      n_correct += 1
    average_precision += precision
    average_recall += recall
    average_f1 += f1
    n_questions += 1

    out.write(str(precision) + '\t')
    out.write(str(recall) + '\t')
    out.write(str(f1))
    out.write('\n')

  out.close()
  return n_questions, n_correct, average_precision, average_recall, average_f1


if __name__ == '__main__':
  if len(sys.argv) != 3:
    sys.exit("Usage: %s <gold_file> <result_file>" % sys.argv[0])
  # load gold and predictions
  gold = load(sys.argv[1])
  predictions = load(sys.argv[2])
  if(len(gold) != len(predictions)):
    raise Exception('Number of questions in gold different from number of questions in predictions')

  n_questions, n_correct, average_precision, average_recall, average_f1 = compute_resutls(gold, predictions)

  # print aggregate results
  average_precision = float(average_precision) / n_questions
  average_recall = float(average_recall) / n_questions
  average_f1 = float(average_f1) / n_questions
  accuracy = float(n_correct) / n_questions
  print "Number of questions: " + str(n_questions)
  print "Average precision over questions: " + str(average_precision)
  print "Average recall over questions: " + str(average_recall)
  print "Average f1 over questions: " + str(average_f1)
  print "Accuracy over questions: " + str(accuracy)
  average_new_f1 = 2 * average_recall * average_precision / (average_precision + average_recall)
  print "F1 of average recall and average precision: " + str(average_new_f1)

