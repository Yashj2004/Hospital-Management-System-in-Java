package HospitalManagmentSystem;

import java.util.*;
import java.sql.*;
public class hospitalmanagmentsystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Password";

    public static void main(String argu[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            patient patient = new patient(connection, scanner);
            doctor doctor = new doctor(connection, scanner);
            while (true) {
                System.out.println("\t\t\t hospital managment system\n");
                System.out.println("1.add patient ");
                System.out.println("2.view patient");
                System.out.println("3.view doctor");
                System.out.println("4.book appointment");
                System.out.println("5.exit");
                System.out.println("enter your choice:\t");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.viewPatient();
                        break;
                    case 3:
                        doctor.viewdoctor();
                        break;
                    case 4:
                        bookappointment(patient,doctor,connection,scanner);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("enter valid choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookappointment(patient patient, doctor doctor, Connection connection, Scanner scanner) {
        System.out.println("enter patient id: \t");
        int patientid = scanner.nextInt();
        System.out.println("enter doctor id:\t");
        int doctorid = scanner.nextInt();
        System.out.println("enter appointment date(yyyy-mm-dd):\t");
        String appointmentdate = scanner.next();
        if (patient.getpatientbyid(patientid) && doctor.getdoctorsbyid(doctorid)) {
            if (checkDoctoravalability(doctorid, appointmentdate,connection)) {
                String appointmentquery= "INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
                try{
                    PreparedStatement preparedStatement=connection.prepareStatement(appointmentquery);
                    preparedStatement.setInt(1,patientid);
                    preparedStatement.setInt(2,doctorid);
                    preparedStatement.setString(3,appointmentdate);
                    int rowsaffected= preparedStatement.executeUpdate();
                    if(rowsaffected>0){
                        System.out.println("appointment booked");
                    }
                    else{
                        System.out.println("failed to book appointment");
                    }

                }catch(SQLException e){
                    e.printStackTrace();
                }
            } else {
                System.out.println("doctor id not avalable at that date");
            }
        } else {
            System.out.println("either doctor or patient doesn't exist");
        }
    }
public static boolean checkDoctoravalability(int doctorid, String appointmentdate,Connection connection){
        String query="SELECT COUNT (*) FROM appointment WHERE doctor_id=? AND appointment_date=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorid);
            preparedStatement.setString(2,appointmentdate);
            ResultSet resultSet= preparedStatement.executeQuery();
            int count =resultSet.getInt(1);
            if(count==0) {
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
