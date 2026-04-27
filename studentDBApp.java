import java.sql.*;
import java.util.*;

public class studentDBApp {
    // Database connection details
    static final String URL = "jdbc:mysql://10.10.8.119:3306/se21381_db";
    static final String USER = "se21381"; 
    static final String PASS = "se21381";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println(" Connected to MySQL Database!");

            do {
                System.out.println("\n===== MENU =====");
                System.out.println("1. Add Student");
                System.out.println("2. Delete Student");
                System.out.println("3. Edit Student");
                System.out.println("4. Display Students");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addStudent(conn, sc);
                        break;
                    case 2:
                        deleteStudent(conn, sc);
                        break;
                    case 3:
                        editStudent(conn, sc);
                        break;
                    case 4:
                        displayStudents(conn);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } while (choice != 5);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------- ADD STUDENT ----------
    public static void addStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter name: ");
        sc.nextLine(); // clear buffer
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        System.out.print("Enter marks: ");
        float marks = sc.nextFloat();

        String sql = "INSERT INTO students (name, age, marks) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setFloat(3, marks);

        int rows = ps.executeUpdate();
        System.out.println(rows + " student(s) added.");
    }

    // ---------- DELETE STUDENT ----------
    public static void deleteStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter student ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM students WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        int rows = ps.executeUpdate();
        System.out.println(rows + " student(s) deleted.");
    }

    // ---------- EDIT STUDENT ----------
    public static void editStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter student ID to edit: ");
        int id = sc.nextInt();
        System.out.print("Enter new marks: ");
        float marks = sc.nextFloat();

        String sql = "UPDATE students SET marks = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setFloat(1, marks);
        ps.setInt(2, id);

        int rows = ps.executeUpdate();
        System.out.println(rows + " student(s) updated.");
    }

    // ---------- DISPLAY ALL STUDENTS ----------
    public static void displayStudents(Connection conn) throws SQLException {
        String sql = "SELECT * FROM students";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        System.out.println("\n--- Student Records ---");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") +
                    ", Name: " + rs.getString("name") +
                    ", Age: " + rs.getInt("age") +
                    ", Marks: " + rs.getFloat("marks"));
        }
    }
}


// CREATE TABLE students (
//     id INT PRIMARY KEY AUTO_INCREMENT,
//     name VARCHAR(50) NOT NULL,
//     age INT,
//     marks FLOAT
// );

// INSERT INTO students(name, age, marks)
// VALUES
// ('Amit',21,78.5),
// ('Priya',20,92.0),
// ('Sneha',22,88.7),
// ('Rohit',19,75.2);