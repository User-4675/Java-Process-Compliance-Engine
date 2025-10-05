# Assignment 1

This README provides instructions on how to compile and run the application, along with an explanation of its design.

## How to Compile and Run

1. Compile and package the project.
   In the project root directory, run:

       mvn clean package

    This will:
    
    - Clean any previous build files.
    - Compile all Java source files.
    - Run unit tests.
    - Generate a runnable JAR with all dependencies.

    After this step, the JAR will be located at:

       target/assignment1-1.0-SNAPSHOT-jar-with-dependencies.jar

2. Run the project using:

       java -jar target/assignment1-1.0-SNAPSHOT-jar-with-dependencies.jar

   The program will start and prompt you for the number of threads to use for processing traces as
    part of configuration. You will also be able to enable or disable live progress (Note that this will
    impact the performance).

3. Open the Javadoc documentation.
   After running Maven, the HTML documentation will be generated at:

       target/site/apidocs/index.html

   Open this file in a web browser to view the full project documentation, or run (on linux):

       xdg-open target/apidocs/index.html


## System Design

Our project handles large CSV log files, categorizes them per trace, and evaluates their compliance.
It also keeps track of program statistics and implements two design patterns.


##### a) Handling Large File Size

To handle the large csv file efficiently:
 - We read the CSV as a stream using InputStream and InputStreamReader, avoiding loading the entire file into memory.
 - We parse records one by one using Apache Commons CSV, storing only necessary information in Trace objects.
 - This streaming approach ensures low memory usage and enables the program to scale to larger datasets.

#####  b) Implementation of required design patterns

In our project, we implemented two design patterns to improve modularity and performance:

1. Producer-Consumer Pattern
    - Implemented in ComplianceApp to enable multithreading.
    - A single producer thread enqueues cases into a shared queue (sufficient for fast checks).
    - A configurable number of consumer threads dequeue cases from the queue and evaluate their compliance status.
    - This pattern allows the work of checking cases to be processed concurrently while keeping the design efficient for fast case evaluation
      (specific for this project).

2. Observer Pattern
    - Implemented using ComplianceApp and ProgressTracker, which implement the Observable and ProgressObserver interfaces, respectively.
    - The pattern separates progress tracking from the core compliance processing.
    - As ComplianceApp processes cases, it periodically notifies the observer (ProgressTracker) about progress and runtime of check evaluations.
    - Once processing is complete, it sends a final notification with additional statistics, including maximum memory usage and total processing time.
    - The observer is responsible for displaying and converting results to the user, keeping the compliance checking logic decoupled from reporting.


##### c) **Multithreading**

Our application allows the user to configure the number of threads and also prompts whether they want to see live progress. 
Since the compliance check operation on each case is very fast, printing live progress introduces a significant bottleneck, 
which can dominate the total runtime. To measure the real processing speed of the multithreaded implementation, 
the user can choose not to display live progress. To confirm this effect, we ran three test cases where live 
progress was disabled and the expected speedup from using multiple threads could be observed.

    1. Statistic when using single thread: 
        - Max Consumed Memory: 898.740 MB
        - Total log processing time: 0.225 s
        - Average case processing time: 669.714 ns
-----------------------------------------------------------------------
    2. Statistics when using two threads
        - Max Consumed Memory: 899.440 MB
        - Total log processing time: 0.199 s
        - Average case processing time: 1069.876 ns
-----------------------------------------------------------------------
    3. Statistics when using three threads:
        - Max Consumed Memory: 494.305 MB
        - Total log processing time: 0.149 s
        - Average case processing time: 1215.606 ns
-----------------------------------------------------------------------

