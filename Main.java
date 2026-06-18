import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        StudentManagementSystem sms =
                new StudentManagementSystem();

        int choice;

        do {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Add Student");
            System.out.println("2. Search Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Display Students");
            System.out.println("6. Generate Report");
            System.out.println("7. Save Text File");
            System.out.println("8. Load Text File");
            System.out.println("9. Save Binary File");
            System.out.println("10. Load Binary File");
            System.out.println("11. Serialize");
            System.out.println("12. Deserialize");
            System.out.println("13. Create Backup");
            System.out.println("14. File Properties");
            System.out.println("0. Exit");

            System.out.print("Choice: ");
            choice = input.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("ID: ");
                    int id = input.nextInt();
                    input.nextLine();

                    System.out.print("Name: ");
                    String name = input.nextLine();

                    System.out.print("Department: ");
                    String dept = input.nextLine();

                    System.out.print("GPA: ");
                    double gpa = input.nextDouble();

                    sms.addStudent(
                            new Student(id,
                                    name,
                                    dept,
                                    gpa)
                    );

                    break;

                case 2:

                    System.out.print("Student ID: ");
                    id = input.nextInt();

                    Student s =
                            sms.searchStudent(id);

                    if (s != null)
                        System.out.println(s);
                    else
                        System.out.println("Not found.");

                    break;

                case 3:

                    System.out.print("ID: ");
                    id = input.nextInt();
                    input.nextLine();

                    System.out.print("New Name: ");
                    name = input.nextLine();

                    System.out.print("New Department: ");
                    dept = input.nextLine();

                    System.out.print("New GPA: ");
                    gpa = input.nextDouble();

                    if (sms.updateStudent(
                            id, name, dept, gpa))
                        System.out.println("Updated.");
                    else
                        System.out.println("Not found.");

                    break;

                case 4:

                    System.out.print("ID: ");
                    id = input.nextInt();

                    if (sms.deleteStudent(id))
                        System.out.println("Deleted.");
                    else
                        System.out.println("Not found.");

                    break;

                case 5:
                    sms.displayStudents();
                    break;

                case 6:
                    sms.generateReport();
                    break;

                case 7:
                    sms.saveTextFile();
                    break;

                case 8:
                    sms.loadTextFile();
                    break;

                case 9:
                    sms.saveBinaryFile();
                    break;

                case 10:
                    sms.loadBinaryFile();
                    break;

                case 11:
                    sms.saveSerializedFile();
                    break;

                case 12:
                    sms.loadSerializedFile();
                    break;

                case 13:
                    sms.createBackup();
                    break;

                case 14:
                    sms.fileProperties();
                    break;

                case 0:
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        input.close();
    }
}
