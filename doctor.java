package HospitalManagmentSystem;
import java.util.*;
import java.sql.*;
public class doctor {
    private Connection connection;
    private Scanner scanner;
    public doctor(Connection connection, Scanner scanner){
        this .connection= connection;
        this .scanner =scanner;
    }
    public void viewdoctor(){
        String query="select * from doctors";
        try{
            PreparedStatement preparedStatement=connection .prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("doctors:\n");
            System.out.println("+--------------+---------------------+----+----------------+");
            System.out.println("|doctorID      | NAME                |specialization       |");
            System.out.println("+--------------+---------------------+---------------------+");
            while(resultSet.next()){
                int id= resultSet.getInt("id");
                String name=resultSet.getString("name");
//                int age = resultSet.getInt("age");
                String specilization = resultSet.getString("SPECILIZATION");
                System.out.printf("|%-14s|%-21s|%-21s\n",id,name,specilization);
                System.out.println("+--------------+---------------------+---------------------+");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getdoctorsbyid(int id){
        String query="select * FROM doctors WHERE ID =?";
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

