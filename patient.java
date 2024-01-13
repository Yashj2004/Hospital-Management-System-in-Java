package HospitalManagmentSystem;
import java.util.*;
import java.sql.*;
public class patient {
    private Connection connection;
    private Scanner scanner;
    public patient(Connection connection, Scanner scanner){
        this .connection= connection;
        this .scanner =scanner;
    }
    public void addPatient(){
        System.out.print("enter patient name:\t");
        String name=scanner.next();
        System.out.print("enter patient age:\t");
        int age=scanner.nextInt();
        System.out.print("enter patient gender:\t");
        String gender=scanner.next();
        try{
            String query="INSERT INTO patients(name,age, gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement=connection .prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
       int affectedRows= preparedStatement.executeUpdate();
       if(affectedRows>0){
           System.out.println("patient added succesfully");
       }
       else{
           System.out.println("failed to add patient");
       }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void viewPatient(){
        String query="select * from patients";
        try{
        PreparedStatement preparedStatement=connection .prepareStatement(query);
        ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("patients:\n");
            System.out.println("+--------+---------------------+---------------+----------------+");
            System.out.println("|ID      | NAME                |AGE            |GENDER          ");
            System.out.println("+--------+---------------------+---------------+----------------+");
            while(resultSet.next()){
                int id= resultSet.getInt("id");
                String name=resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-8s|%-21s|%-15s|%-16s\n",id,name,age,gender);
                System.out.println("+--------+---------------------+---------------+----------------+");

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getpatientbyid(int id){
        String query="select * FROM patients WHERE ID =?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
