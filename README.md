# Java Process Compliance Engine

This project is a high-performance, multithreaded Java application designed to analyze business process event logs for compliance. It evaluates traces from a large dataset against a predefined set of rules using various checking strategies, demonstrating software engineering principles and design patterns.

## Key Features

- **Multithreaded Processing**: Utilizes a **Producer-Consumer** pattern to concurrently process thousands of event traces, significantly improving performance.
- **Advanced Design Patterns**: Implements the **Strategy** pattern to apply different compliance-checking algorithms dynamically and the **Observer** pattern to decouple progress tracking from the core processing logic.
- **Efficient File Handling**: Processes large (500MB+) event logs by streaming data, ensuring a low memory footprint.
- **CI/CD Automation**: Features a complete GitLab CI pipeline that automates builds, runs Checkstyle for code quality, executes unit tests, and performs compliance checks on updated data files.
- **Modular & Extensible**: The architecture is designed to be easily extensible with new compliance rules and checking strategies.

## Skills & Learning Outcomes

This project demonstrates proficiency in:

- **Concurrent Programming**: Deep understanding and practical implementation of multithreading in Java to solve real-world performance bottlenecks.
- **Software Architecture**: Application of fundamental design patterns (Strategy, Observer, Producer-Consumer) to build a robust, maintainable, and scalable application.
- **Object-Oriented Design**: Strong object-oriented principles are used to model the problem domain of processes, traces, and events.
- **Test-Driven Development (TDD)**: Comprehensive unit tests are included to ensure the correctness of each compliance strategy and component.
- **DevOps & Automation**: Experience in setting up and configuring a CI/CD pipeline (GitLab CI) to automate the entire development and deployment workflow.
- **Performance Optimization**: Techniques for handling large datasets and optimizing application runtime through efficient I/O and concurrency.

## The Dataset: BPI Challenge 2019

This application is designed to work with a CSV-formatted version of the **BPI Challenge 2019** event log. The BPI Challenge is an annual international competition in the field of process mining.

-   **Official BPI Challenge Website**: For more information about the challenge, visit [icpmconference.org](https://icpmconference.org/2019/icpm-2019/contests-challenges/bpi-challenge-2019/).
-   **Full CSV Dataset (500MB+)**: The full dataset can be downloaded directly as a ZIP file containing the CSV from [here](http://icpmconference.org/2019/wp-content/uploads/sites/6/2019/02/BPIChallenge2019CSV.zip). After downloading and unzipping, place the `.csv` file into the `event_log/` directory.
-   **Lite Version for Testing**: For convenience, a lite version of the dataset (`bpi_challenge_2019_lite.csv`) containing the first 3,000 events is included in the `event_log/` directory. This allows you to quickly run and test the application without downloading the full file.

## System Design

### 1. Multithreaded Processing (Producer-Consumer)
A single producer thread reads events from the CSV file and enqueues them as `Trace` objects. A configurable pool of consumer threads dequeues these traces and executes the compliance checks concurrently. This significantly speeds up the analysis of the entire dataset.

### 2. Dynamic Compliance Logic (Strategy Pattern)
Different types of traces require different compliance rules. The Strategy pattern allows the application to select the appropriate checking algorithm (`TwoWayMatch`, `ThreeWayBeforeGR`, etc.) for a given trace at runtime. This makes the system flexible and easy to extend with new rules.

### 3. Decoupled Reporting (Observer Pattern)
The core compliance-checking logic is decoupled from UI/reporting. The `ComplianceApp` acts as the `Observable`, notifying the `ProgressTracker` (`Observer`) about the status of the analysis. This allows for clean separation of concerns.

### 4. CI/CD Pipeline
The GitLab CI pipeline automates the following stages:
- **`checkstyle`**: Enforces a consistent code style across the project.
- **`build`**: Compiles the Java source code.
- **`test`**: Runs all unit tests to ensure code correctness.
- **`conformance-check`**: Automatically runs the compliance check on any `.csv` file updated in the `event_log/` directory and saves the results as a job artifact.

## How to Run the Application

### Prerequisites
- Java 11 or higher
- Apache Maven

### Steps
1.  **Compile the project and package it into a runnable JAR:**
    ```shell
    mvn clean package
    ```

2.  **Run the application with the lite dataset:**
    ```shell
    # The <file_name> should be in the event_log/ directory
    java -jar target/engine-jar-with-dependencies.jar BPI_Challenge_2019_lite.csv
    ```
    The application will prompt you to configure the number of threads to use for processing and whether to display live progress updates. Live progress may not be visibly apparent when processing the small `lite` database due to its speed.  

    *Note: The file name `BPI_Challenge_2019_lite.csv` can be (and must be if you are running actual database) configured to database in `event_log/`*                    

3.  **View the results:**
    A summary (`.txt`) and a detailed report (`.csv`) will be generated in the `reports/` directory.

4.  **(Optional) View the Javadoc documentation:**
    Open `target/site/apidocs/index.html` in your web browser.
    ```shell
    # On Linux
    xdg-open target/apidocs/index.html
    ```
