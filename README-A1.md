# Assignment 1

This README provides instructions on how to compile and run the application, along with an explanation of its design.

## How to Compile and Run

1. Compile and package the project.
   In the project root directory (where pom.xml is located), run:

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
    part of configuration.

3. Open the Javadoc documentation.
   After running Maven, the HTML documentation will be generated at:

       target/site/apidocs/index.html

   Open this file in a web browser to view the full project documentation, or run (on linux):

       xdg-open target/apidocs/index.html


## System Design

ADD YOUR CONTENT HERE
