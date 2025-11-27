# Student Record Manager - Java Application

## Problem
Your Java application was compiled with a newer version of Java (Java 23) but your system has Java 8 runtime. This causes a version mismatch error.

## Solutions

### Option 1: Install JDK (Recommended)
1. Download Eclipse Temurin JDK 8 from: https://adoptium.net/
2. Install it and ensure "Add to PATH" is checked
3. Open a new PowerShell window
4. Navigate to your project folder:
   ```
   cd "c:\Users\aryan\OneDrive\Desktop\Student_Record\Student_Record"
   ```
5. Compile the Java file:
   ```
   javac StudentManagerApp.java
   ```
6. Run the application:
   ```
   java StudentManagerApp
   ```

### Option 2: Use Online Compiler (Temporary)
1. Copy the contents of `StudentManagerApp.java`
2. Go to https://www.online-java.com/ or https://www.jdoodle.com/online-java-compiler/
3. Paste the code and run it online

### Option 3: Use VS Code with Java Extensions
1. Install the "Extension Pack for Java" in VS Code
2. VS Code will help you install the required JDK
3. You can then run the Java file directly from VS Code

## About Your Application
Your Student Record Manager is a GUI application that:
- Allows adding student records (Registration No, Name, Course, Roll No, Fee Status)
- Displays all student records
- Searches students by roll number
- Deletes student records
- Counts total students
- Saves/loads data to/from `students.txt` file

The application uses Java Swing for the graphical interface and implements a linked list data structure to manage student records.