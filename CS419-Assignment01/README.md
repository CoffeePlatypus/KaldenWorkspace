CS419 - Machine Learning Assignment 1 README  
============================================  

#### Author ####
Julia Froegel  

### Description ###  
Implements the decision tree learning algorithm to predict if mushrooms are poisonous.

### Language ###
Java 10

### Content ###
- src 
	- Mushroom.java
	- DecisionTree.java
	- DecisionTreeDriver.java
- output
	- output1.txt
	- output1graph.png
	- output2.txt
	- output2graph.png

### Usage ###
Run from command line in following format 
 
`java DecisionTreeDriver [-a] [-d] [-f=<fileName>] [-v]`
* `-a` 
     - [optional]
     - include to run in failure analysis mode
     - prints counts for incorrect predictions

* `-d` 
     - [optional]
     - include to run in debug mode
     - this generates a lot of output, slows performance, and is not recommended 

* `-v`  
     - [optional] 
     - include to run in verbose mode and print final ultimate tree structure  

* `-f=<filePath>` 
     - [optional] 
     - include to run with a specific input file of mushroom data
     - `<filePath>` 
       - required if `-f=` is used 
       - specifies path to mushroom path test file
     - if `-f=` is not used then the program read from `mushroom_data.txt` from the same directory that it is in

