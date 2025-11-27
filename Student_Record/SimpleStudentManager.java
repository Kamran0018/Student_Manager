import java.awt.*;
import javax.swing.*;
import java.io.*;

// Student class - stores one student's information
class Student {
    String name, registrationNo, course;
    double cgpa;
    Student next;  // pointer to next student

    Student(String name, String registrationNo, String course, double cgpa) {
        this.name = name;
        this.registrationNo = registrationNo;
        this.course = course;
        this.cgpa = cgpa;
        this.next = null;
    }
}

// StudentRecord class - manages all students using linked list
class StudentRecord {
    Student start = null;  // first student in the list

    // Add new student at the end
    void addStudent(String name, String registrationNo, String course, double cgpa) {
        Student newStudent = new Student(name, registrationNo, course, cgpa);
        
        if (start == null) {
            start = newStudent;  // first student
        } else {
            Student temp = start;
            while (temp.next != null) {
                temp = temp.next;  // go to last student
            }
            temp.next = newStudent;  // add at end
        }
    }

    // Display all students
    String showAllStudents() {
        if (start == null) return "No students found!";
        
        String result = "";
        Student temp = start;
        while (temp != null) {
            result += "Name: " + temp.name + "\n";
            result += "Registration Number: " + temp.registrationNo + "\n";
            result += "Course: " + temp.course + "\n";
            result += "CGPA: " + temp.cgpa + "\n\n";
            temp = temp.next;
        }
        return result;
    }

    // Search student by registration number
    String searchStudent(String registrationNo) {
        Student temp = start;
        while (temp != null) {
            if (temp.registrationNo.equals(registrationNo)) {
                return "Found Student:\nName: " + temp.name + "\nRegistration Number: " + temp.registrationNo + 
                       "\nCourse: " + temp.course + "\nCGPA: " + temp.cgpa;
            }
            temp = temp.next;
        }
        return "Student not found!";
    }

    // Delete student by registration number
    boolean deleteStudent(String registrationNo) {
        if (start == null) return false;

        // If first student to delete
        if (start.registrationNo.equals(registrationNo)) {
            start = start.next;
            return true;
        }

        // Search and delete
        Student prev = start;
        Student current = start.next;
        while (current != null) {
            if (current.registrationNo.equals(registrationNo)) {
                prev.next = current.next;  // skip current student
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    // Sort students by CGPA (highest first)
    // Sort students by CGPA (highest first) - simplified version
void sortByCGPA() {
    if (start == null || start.next == null) return;

    Student current, index;
    double tempCgpa;
    String tempName, tempReg, tempCourse;

    // Simple Bubble Sort by swapping data only
    for (current = start; current != null; current = current.next) {
        for (index = current.next; index != null; index = index.next) {
            if (current.cgpa < index.cgpa) {
                // Swap all data fields (instead of nodes)
                tempCgpa = current.cgpa;
                current.cgpa = index.cgpa;
                index.cgpa = tempCgpa;

                tempName = current.name;
                current.name = index.name;
                index.name = tempName;

                tempReg = current.registrationNo;
                current.registrationNo = index.registrationNo;
                index.registrationNo = tempReg;

                tempCourse = current.course;
                current.course = index.course;
                index.course = tempCourse;
            }
        }
    }
}


    // Count total students
    int countStudents() {
        int count = 0;
        Student temp = start;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    // Clear all students permanently
    void clearAll() {
        start = null;
    }

    // Get a temporary copy of all students (for display clear functionality)
    String getDisplayData() {
        return showAllStudents();
    }

    // Save to file
    void saveToFile() {
        try {
            FileWriter file = new FileWriter("students.txt");
            Student temp = start;
            while (temp != null) {
                file.write(temp.name + "," + temp.registrationNo + "," + temp.course + "," + temp.cgpa + "\n");
                temp = temp.next;
            }
            file.close();
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }

    // Load from file
    void loadFromFile() {
        try {
            BufferedReader file = new BufferedReader(new FileReader("students.txt"));
            String line;
            while ((line = file.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    double cgpa = Double.parseDouble(data[3]);
                    addStudent(data[0], data[1], data[2], cgpa);
                }
            }
            file.close();
        } catch (Exception e) {
            System.out.println("No previous data found");
        }
    }
}

// Main GUI class
public class SimpleStudentManager extends JFrame {
    // GUI components - updated fields
    JTextField nameField, registrationField, courseField, cgpaField;
    JTextField searchField, deleteField;
    JTextArea displayArea;
    StudentRecord students = new StudentRecord();

    public SimpleStudentManager() {
        // Window setup - larger size for better visibility
        setTitle("Student Record Manager");
        setSize(800, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        // Load existing data
        students.loadFromFile();

        // Create GUI
        createGUI();
        
        // Save data when closing
        Runtime.getRuntime().addShutdownHook(new Thread(() -> students.saveToFile()));
        
        setVisible(true);
    }

    void createGUI() {
        setLayout(new BorderLayout());

        // Input panel with larger fonts and colors
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Student"));
        inputPanel.setBackground(new Color(248, 248, 255));

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 15);

        nameField = new JTextField();
        registrationField = new JTextField();
        courseField = new JTextField();
        cgpaField = new JTextField();

        // Set larger font for all fields
        nameField.setFont(fieldFont);
        registrationField.setFont(fieldFont);
        courseField.setFont(fieldFont);
        cgpaField.setFont(fieldFont);

        // Set field background color
        nameField.setBackground(Color.WHITE);
        registrationField.setBackground(Color.WHITE);
        courseField.setBackground(Color.WHITE);
        cgpaField.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Name:");
        JLabel regLabel = new JLabel("Registration No:");
        JLabel courseLabel = new JLabel("Course:");
        JLabel cgpaLabel = new JLabel("CGPA (0-10):");

        // Set larger font for labels
        nameLabel.setFont(labelFont);
        regLabel.setFont(labelFont);
        courseLabel.setFont(labelFont);
        cgpaLabel.setFont(labelFont);

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(regLabel);
        inputPanel.add(registrationField);
        inputPanel.add(courseLabel);
        inputPanel.add(courseField);
        inputPanel.add(cgpaLabel);
        inputPanel.add(cgpaField);

        // Search/Delete panel with larger fonts
        JPanel searchPanel = new JPanel(new GridLayout(1, 4, 15, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search & Delete"));
        searchPanel.setBackground(new Color(255, 248, 248));
        
        searchField = new JTextField();
        deleteField = new JTextField();
        
        // Set larger fonts for search fields
        searchField.setFont(fieldFont);
        deleteField.setFont(fieldFont);
        searchField.setBackground(Color.WHITE);
        deleteField.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Find by Reg No:");
        JLabel deleteLabel = new JLabel("Remove by Reg No:");
        searchLabel.setFont(labelFont);
        deleteLabel.setFont(labelFont);
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(deleteLabel);
        searchPanel.add(deleteField);

        // Button panel with larger buttons and colors
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 12, 12));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        buttonPanel.setBackground(new Color(248, 255, 248));
        
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        
        JButton addBtn = new JButton("Add Student");
        JButton showBtn = new JButton("View All");
        JButton searchBtn = new JButton("Search");
        JButton deleteBtn = new JButton("Delete");
        JButton sortBtn = new JButton("Sort by CGPA");
        JButton clearDisplayBtn = new JButton("Clear Display");

        // Set larger font and colors for buttons
        JButton[] buttons = {addBtn, showBtn, searchBtn, deleteBtn, sortBtn, clearDisplayBtn};
        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setPreferredSize(new Dimension(140, 40));
            btn.setBackground(new Color(230, 240, 255));
            btn.setForeground(Color.BLACK);
        }

        // First row - main actions
        buttonPanel.add(addBtn);
        buttonPanel.add(showBtn);
        buttonPanel.add(searchBtn);
        
        // Second row - modify actions
        buttonPanel.add(deleteBtn);
        buttonPanel.add(sortBtn);
        buttonPanel.add(clearDisplayBtn);

        // Display area with larger font and colors
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        displayArea.setMargin(new Insets(20, 20, 20, 20));
        displayArea.setBackground(new Color(255, 255, 250));
        displayArea.setForeground(Color.BLACK);
        displayArea.setText("Welcome! Click 'View All' to see student records or 'Add Student' to create new entries.");
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student Records Display"));

        // Top panel combining input, search, and buttons with spacing
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(inputPanel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(searchPanel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(buttonPanel);

        // Add to main frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button actions
        addBtn.addActionListener(e -> addStudent());
        showBtn.addActionListener(e -> showAllStudents());
        searchBtn.addActionListener(e -> searchStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        sortBtn.addActionListener(e -> sortByCGPA());
        clearDisplayBtn.addActionListener(e -> clearDisplayOnly());
    }

    // Button action methods
    void addStudent() {
        String name = nameField.getText().trim();
        String registration = registrationField.getText().trim();
        String course = courseField.getText().trim();
        String cgpaText = cgpaField.getText().trim();

        if (name.isEmpty() || registration.isEmpty() || course.isEmpty() || cgpaText.isEmpty()) {
            displayArea.setText("Please fill all fields!");
            return;
        }

        try {
            double cgpa = Double.parseDouble(cgpaText);
            if (cgpa < 0 || cgpa > 10) {
                displayArea.setText("CGPA must be between 0 and 10!");
                return;
            }
            students.addStudent(name, registration, course, cgpa);
            displayArea.setText("Student added successfully!");
            
            // Clear input fields
            nameField.setText("");
            registrationField.setText("");
            courseField.setText("");
            cgpaField.setText("");
        } catch (NumberFormatException e) {
            displayArea.setText("Please enter a valid CGPA (number)!");
        }
    }

    void showAllStudents() {
        displayArea.setText(students.showAllStudents());
    }

    void searchStudent() {
        String registration = searchField.getText().trim();
        if (registration.isEmpty()) {
            displayArea.setText("Please enter registration number to search!");
            return;
        }
        displayArea.setText(students.searchStudent(registration));
    }

    void deleteStudent() {
        String registration = deleteField.getText().trim();
        if (registration.isEmpty()) {
            displayArea.setText("Please enter registration number to delete!");
            return;
        }
        
        boolean deleted = students.deleteStudent(registration);
        if (deleted) {
            displayArea.setText("Student deleted successfully!");
            deleteField.setText("");
        } else {
            displayArea.setText("Student not found!");
        }
    }

    void sortByCGPA() {
        students.sortByCGPA();
        displayArea.setText("Students sorted by CGPA (highest first):\n\n" + students.showAllStudents());
    }

    void clearDisplayOnly() {
        displayArea.setText("Display cleared temporarily.\nData is still saved in file.\nClick 'Show All' to view records again.");
    }

    public static void main(String[] args) {
        new SimpleStudentManager();
    }
}