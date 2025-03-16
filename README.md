Calculator Project Report
Overview:
This project is a simple interactive command-line calculator in Java. It evaluates arithmetic expressions and supports functions like Math.pow, Math.abs, and Math.sqrt. It includes a history feature that allows users to view past calculations and reuse them.

Design Choices:

JShell API: Used to evaluate user expressions dynamically.
History: Stores calculations and their results for easy recall.
Error Handling: Catches issues like unbalanced parentheses and division by zero.
User Input: The user enters expressions, and results or errors are displayed.
Challenges Encountered:

Parsing and converting user-friendly functions like power() to Java functions.
Managing history and allowing users to recall past calculations.
Handling errors like invalid syntax or math errors (division by zero).
Data Structures:

List<String> history: Stores the expression and result.
Stack<Character>: Used to check if parentheses are balanced.
Algorithms:

Expression Preprocessing: Converts user functions into Java syntax using regular expressions.
Bracket Balancing: Checks if parentheses are balanced using a stack.
Evaluation: Uses JShell to evaluate valid expressions.
Improvements Made:

Added history feature to recall past calculations.
Improved error handling with more specific messages.
Enhanced user experience with easier navigation.
Files:
No external files are used for input or output. Everything is handled through the console.

Conclusion:
This project is a simple yet functional calculator with advanced features like expression evaluation, history tracking, and error handling, making it a practical tool for users.
