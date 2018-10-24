S419 - Machine Learning Assignment 1 README  
============================================  

#### Author ####
Julia Froegel  

### Description ###  
Implements a KD Tree - Groups data point into smaller subsets of similar data points to make finding the closest data point easier 

### Language ###
Java 10

###Content###
- KDBuilder
- KDTree
- Point

###Usage###
`java KDBuilder [input-data-path] [set-size]`

- [input-data-path]
     - Path to text file with input data
     - I made this technically optional with default of 'inputData/2d_small.txt' to make running in eclipse easier
     
- [set-size]
     - Max size for sets in leafs
     - I made this technically optional with default of '1' to make running in eclipse easier
