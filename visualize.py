import os
print(os.getcwd())
os.chdir(os.getcwd())

import numpy as np
import matplotlib.pyplot as plt




def visualizeFile(fileName):
    f = open(fileName, "rt")
    all = []
    properties = []
    uniqueProperties = []
    for x in f:
        current = x.replace('\n','').split("\t")
        properties.append(current[0])
        current[1] = int(current[1])
        current[2] = int(current[2])
        all.append(current)
    #print(all)
    uniqueProperties = sorted(list(set(properties)))
    #print(properties)
    correct = []
    incorrect = []
    for u in uniqueProperties:
        cor = 0
        incor = 0
        for a in all:
            if a[0] == u:
                if a[1] == 1:
                    cor+=1
                else:
                    incor+=1
        correct.append(cor)
        incorrect.append(incor)
    #print(uniqueProperties)
    print('Correct = ', correct)
    print('Incorrect = ', incorrect)


    N = len(uniqueProperties)
    ind = np.arange(N)  # the x locations for the groups
    width = 0.35  # the width of the bars: can also be len(x) sequence

    p1 = plt.barh(ind, correct, width)
    p2 = plt.barh(ind, incorrect, width, left=correct)

    plt.xlabel('Questions')
    plt.title(fileName)
    plt.yticks(ind, uniqueProperties)
    plt.legend((p1[0], p2[0]), ('Correct', 'Incorrect'), ncol=2)
    plt.tight_layout()
    
    print('\nClose the image ' + fileName + ' to see the next one.')
    plt.show()


try:
    visualizeFile("singleEdge.txt")
except:
    print('No data in singleEdge.txt')

try:
    visualizeFile("chain.txt")
except:
    print('No data in chain.txt')

try:
    visualizeFile("chainSet.txt")
except:
    print('No data in chainSet.txt')

try:
    visualizeFile("tree.txt")
except:
    print('No data in tree.txt')

try:
    visualizeFile("star.txt")
except:
    print('No data in star.txt')

try:
    visualizeFile("cycle.txt")
except:
    print('No data in cycle.txt')

try:
    visualizeFile("forest.txt")
except:
    print('No data in forest.txt')

try:
    visualizeFile("flower.txt")
except:
    print('No data in flower.txt')

try:
    visualizeFile("flowerSet.txt")
except:
    print('No data in flowerSet.txt')
