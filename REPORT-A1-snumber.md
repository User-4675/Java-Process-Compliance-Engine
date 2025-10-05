# Advanced Programming, 2025-2026 | Assignment 1

Student: <snumber> - <Name(s)> <Surname(s)>

## Task Distribution

### Planned

> NOTE: This part of the report ("Planned" subsection) should be committed to the repository within the first few days. Otherwise, we assume that the students had no agreement early on, which will impact disputes on expected workload (should they emerge).

In this section, describe the initial plan to distribute the tasks between the group members.
There are several different ways to distribute the tasks, could be based on parts of the system, or type of responsibility. It is important that this description is as detailed as possible to avoid misunderstandings later on.

This part should include an explicit declaration of expected workload, stating that all students are expected to have the same workload or, if not, the expected load distribution. See an example of each below:
- "Based on the task distribution above, I declare that I expect all students to have the same workload."
- "Based on the task distribution above, I declare that I expect <Name> (<snumber>) to handle about 40% of the workload, <Name> (<snumber>) to handle about 30% of the workload, and <Name> (<snumber>) to handle about 30% of the workload."

### Executed

In this section you report the tasks you performed in practice, AND what tasks you believe your team members performed. Again, be as detailed as possible. This is YOUR report, i.e., your take on the work distribution of all students in this group. For each student, report two things: (a) the tasks and (b) the overall workload. This reporting will be used as the basis for any disputes.

- Student <5866413>: (Samuel Mikuláš)
    - Tasks:
        - (list of tasks)
    - Workload:
        - I believe I did 1/3 of the work.

- Student <snumber>:
    - Tasks:
        - (list of tasks)
    - Workload:
        - I believe that <Name> did X% of the work. (e.g., 1/3, half, etc.)

- Student <snumber>:
    - Tasks:
        - (list of tasks)
    - Workload:
        - I believe that <Name> did X% of the work. (e.g., 1/3, half, etc.)

> NOTE: The workload distribution will impact individual grades (if other than equal workloads). If we note any discrepancies in the reports, the work distribution will be discussed with the group.

## Use of GenAI
During the course of our project, we used Generative AI (model: ChatGPT 5) to assist with several tasks:

- **Clarification of Assignment Requirements**: AI helped us interpret the assignment specifications, particularly regarding compliance rules. 
        For example, we prompted it to clarify conditions such as "Should 'Record Invoice Receipt' never precede 'Cancel Invoice Receipt'?" and provided 
        guidance on understanding the structure of the CSV file.

- **Data Loading**: AI supported our understanding of the Apache Commons CSV library and its use within the `DataLoader` class. We explored how to iterate 
        over CSV records efficiently using prompts like "What is the Apache Commons CSV dependency?" and "How to use iterators for parsing a CSV file?". 
        This enabled us to correctly implement the logic for loading and parsing trace data.

- **Design Patterns**: Although we were familiar with the Observer pattern, implementing the Producer-Consumer pattern posed challenges. AI assisted 
        us in understanding the pattern by explaining the roles of producers and consumers. It also helped clarify thread logic and anonymous function 
        usage in thread initialization, through prompts such as "Explain the Producer-Consumer pattern" and "How should an anonymous function in thread 
        initialization look?"

- **Multithreading**: The use of `BlockingQueue` and `LinkedBlockingQueue` was new to us. AI provided explanations on how blocking queues operate, why 
        threads may block, and the handling of `InterruptedException`. Prompts included "How does BlockingQueue work?" and "When and why does a blocking 
        queue block threads?"

- **Application UI and Animations**: AI guided us in enhancing the user interface with colored console text, suggesting the use of a `ConsoleColor` enum 
        instead of string-based color codes. For live progress simulation, AI provided strategies for implementing smooth animations, including separating 
        data loading from progress display, using a dedicated thread, and optimal sleep intervals. Prompts included "How can we separate animation from data 
        loading?" and "How does `.flush()` work to improve live progress display?"

- **Testing**: AI contributed to designing test cases and generating sample CSV files for testing purposes. Prompts such as "What classes should be tested?",
        "Generate a testing CSV file", and "What other tests should we use to verify our project?" helped us ensure comprehensive coverage. All tests were 
         reviewed and validated by us to confirm correctness.

Regarding the **usefulness** of AI, we state that it significantly assisted us in understanding the assignment and its key concepts. 
It accelerated the development process and provided more focused guidance for our project. However, there were instances where AI 
produced incorrect or misleading suggestions, which required verification and, in some cases, led to longer debugging sessions. 
Overall, our experience was positive, as AI effectively sped up repetitive tasks such as generating test cases and improving the user 
interface, allowing us to concentrate more on implementing the core logic of the project.


## Declaration

[x] I declare that the information in this report is truthful and accurate.

[X] The content in this report and my delivered code is my own work, potentially supported by generative AI tools, and I take full responsibility for the content.
