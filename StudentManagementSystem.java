import java.io.*;
import java.util.*;

public class StudentManagementSystem {

    private ArrayList<Student> students = new ArrayList<>();

    private final String DIRECTORY = "StudentRecords";
    private final String TEXT_FILE = DIRECTORY + "/students.txt";
    private final String BINARY_FILE = DIRECTORY + "/students.dat";
    private final String SERIAL_FILE = DIRECTORY + "/students.ser";
    private final String BACKUP_FILE = DIRECTORY + "/backup.ser";

    public StudentManagementSystem() {
        createFiles();
    }

    private void createFiles() {

        try {

            File dir = new File(DIRECTORY);

            if (!dir.exists()) {
                dir.mkdir();
            }

            new File(TEXT_FILE).createNewFile();
            new File(BINARY_FILE).createNewFile();
            new File(SERIAL_FILE).createNewFile();

        } catch (IOException e) {
            System.out.println("File creation error.");
        }
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public Student searchStudent(int id) {

        for (Student s : students) {

            if (s.getStudentId() == id) {
                return s;
            }
        }

        return null;
    }

    public boolean updateStudent(int id,
                                 String name,
                                 String dept,
                                 double gpa) {

        Student s = searchStudent(id);

        if (s != null) {

            s.setName(name);
            s.setDepartment(dept);
            s.setGpa(gpa);

            return true;
        }

        return false;
    }

    public boolean deleteStudent(int id) {

        Student s = searchStudent(id);

        if (s != null) {
            students.remove(s);
            return true;
        }

        return false;
    }

    public void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            System.out.println(s);
        }
    }

    // TEXT FILE

    public void saveTextFile() {

        try (PrintWriter pw =
                     new PrintWriter(TEXT_FILE)) {

            for (Student s : students) {

                pw.println(
                        s.getStudentId() + "," +
                        s.getName() + "," +
                        s.getDepartment() + "," +
                        s.getGpa()
                );
            }

            System.out.println("Saved to text file.");

        } catch (Exception e) {
            System.out.println("Text save error.");
        }
    }

    public void loadTextFile() {

        students.clear();

        try (Scanner file =
                     new Scanner(new File(TEXT_FILE))) {

            while (file.hasNextLine()) {

                String line = file.nextLine();

                String[] data = line.split(",");

                students.add(
                        new Student(
                                Integer.parseInt(data[0]),
                                data[1],
                                data[2],
                                Double.parseDouble(data[3])
                        )
                );
            }

            System.out.println("Loaded from text file.");

        } catch (Exception e) {
            System.out.println("Text load error.");
        }
    }

    // BINARY FILE

    public void saveBinaryFile() {

        try (DataOutputStream dos =
                     new DataOutputStream(
                             new FileOutputStream(BINARY_FILE))) {

            dos.writeInt(students.size());

            for (Student s : students) {

                dos.writeInt(s.getStudentId());
                dos.writeUTF(s.getName());
                dos.writeUTF(s.getDepartment());
                dos.writeDouble(s.getGpa());
            }

            System.out.println("Saved to binary file.");

        } catch (Exception e) {
            System.out.println("Binary save error.");
        }
    }

    public void loadBinaryFile() {

        students.clear();

        try (DataInputStream dis =
                     new DataInputStream(
                             new FileInputStream(BINARY_FILE))) {

            int count = dis.readInt();

            for (int i = 0; i < count; i++) {

                int id = dis.readInt();
                String name = dis.readUTF();
                String dept = dis.readUTF();
                double gpa = dis.readDouble();

                students.add(
                        new Student(id, name, dept, gpa)
                );
            }

            System.out.println("Loaded from binary file.");

        } catch (Exception e) {
            System.out.println("Binary load error.");
        }
    }

    // SERIALIZATION

    public void saveSerializedFile() {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(SERIAL_FILE))) {

            oos.writeObject(students);

            System.out.println("Serialized successfully.");

        } catch (Exception e) {
            System.out.println("Serialization error.");
        }
    }

    @SuppressWarnings("unchecked")
    public void loadSerializedFile() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(SERIAL_FILE))) {

            students =
                    (ArrayList<Student>) ois.readObject();

            System.out.println("Deserialized successfully.");

        } catch (Exception e) {
            System.out.println("Deserialization error.");
        }
    }

    // REPORT

    public void generateReport() {

        if (students.isEmpty()) {

            System.out.println("No student records.");
            return;
        }

        Student highest = students.get(0);
        Student lowest = students.get(0);

        double total = 0;

        for (Student s : students) {

            total += s.getGpa();

            if (s.getGpa() > highest.getGpa()) {
                highest = s;
            }

            if (s.getGpa() < lowest.getGpa()) {
                lowest = s;
            }
        }

        System.out.println("\n===== REPORT =====");
        System.out.println("Total Students : " + students.size());

        System.out.println(
                "Highest GPA : " +
                        highest.getName() +
                        " (" +
                        highest.getGpa() +
                        ")"
        );

        System.out.println(
                "Lowest GPA : " +
                        lowest.getName() +
                        " (" +
                        lowest.getGpa() +
                        ")"
        );

        System.out.println(
                "Average GPA : " +
                        (total / students.size())
        );
    }

    // FILE PROPERTIES

    public void fileProperties() {

        File file = new File(SERIAL_FILE);

        System.out.println("\nFile Name: "
                + file.getName());

        System.out.println("Path: "
                + file.getAbsolutePath());

        System.out.println("Size: "
                + file.length() + " bytes");

        System.out.println("Last Modified: "
                + new Date(file.lastModified()));
    }

    // BACKUP

    public void createBackup() {

        try (
                BufferedInputStream bis =
                        new BufferedInputStream(
                                new FileInputStream(SERIAL_FILE));

                BufferedOutputStream bos =
                        new BufferedOutputStream(
                                new FileOutputStream(BACKUP_FILE))
        ) {

            int data;

            while ((data = bis.read()) != -1) {
                bos.write(data);
            }

            System.out.println("Backup created.");

        } catch (Exception e) {
            System.out.println("Backup error.");
        }
    }
}
