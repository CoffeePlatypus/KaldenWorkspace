CS419 - Machine Learning Assignment 03 
======================================

###Author###
Julia Froegel

###Description###
A neural net that learns to classify data
My highest average accuracy is 20.5% accuracy 
One time I had 39% accuracy testing and training on trainingSet_05.dat with 2 hidden layers of length 5 but I have 'fixed' things since then and been unable replicate this since. 

###Language###
Java 10

###Content###
- src
     - Layer.java
     - NeuralNet.java
     - NeuralNetDriver.jave
     - Perceptron.java
     - Point.java
- neuralNets
     - location where neural nets get saved to
     - net1.txt 
          - neural net for resolution 5 data
- testSet_data
      - testSet_05.dat
      - testSet_10.dat
      - testSet_15.dat
      - testSet_20.dat
- trainSet_data
      - trainSet_05.dat
      - trainSet_10.dat
      - trainSet_15.dat
      - trainSet_20.dat 
      
###Usage###
`java NeuralNetDriver`

Requires that neuralNet folder exists for saving nets
Requires that test data is located in the testSet_data folder
Requires that test data is located in the trainSet_data folder

###Known Problem Areas###
- My accuracy is not that high but I encountered the problem where if I train to much or have too many layers the amount of testing on points of first class appears to skew my net towards always guessing something is the first class. 
