# Term Project 3: Performance Comparison

This project builds on the relational algebra operations with indexing introduced in Project 2. In Project 3, we aim to analyze the performance of various indexing techniques by comparing different data structures used as indices. This evaluation will focus on Select and Join operations, using the tuple generator to create large datasets and benchmark runtime performance.

## Key Features

- **Objective:**  
Analyze the performance of various indexing techniques by comparing different data structures used as indices. This evaluation will focus on Select and Join operations, using the tuple generator to create large datasets and benchmark runtime performance.

- **Key Goals:**
    - Measure and compare the runtime performance of Select and Join operations using different indexing structures, focusing on runtime efficiency in terms of milliseconds.
    - Utilize the tuple generator to create large datasets and evaluate the impact of each indexing structure (NO_MAP, TREE_MAP, HASH_MAP, BPTREE_MAP) on Select and Join performance.
    - Report findings through performance plots (runtime vs. dataset size) and provide insights on the most effective indexing structure for specific relational algebra operations in Java.
- **Significance:**  
    - Demonstrates the practical use of relational algebra concepts in Java, focusing on optimized implementation of Select, Join, Union, and Minus operations.
## Authors

- Oscar Gross  
- Shruti Chari  
- Lauren Lee  
- Pranavi Srinivasa  
- Jacob Tate

## Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed the latest version of Java (Java 22).

## Technologies Used
- Java 22

## Execution

1. You can clone the project or download the Zip and extract it.
2. Open the project in any IDE with the root folder containing `src`, `pom.xml` files.
3. Use this command to compile: javac --enable-preview --release 22 *.java (If you do not have Java 22, change it to the version you have).
4. Use this command to run: java --enable-preview TableTest
5. Run the project and check the application logs to ensure that the project is up and running successfully.


## Contribution

This project is a collaborative effort involving a team of 5 members, including a manager.

- Oscar and Pranavi implemented the join test cases
- Shruti and Lauren implemented the select test cases
- Jacob worked on the graphs

All 5 members equally collaborated on the ReadMe and Documentation. 

## Documentation

Please refer to the documentation file named `Project3Documentation.md`

