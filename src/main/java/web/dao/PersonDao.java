//package web.dao;
//
//import org.springframework.stereotype.Component;
//import web.model.Person;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//@Component
//public class PersonDao {
//
//private static final String URL = "jdbc:postgresql://localhost:5432/Database226";
//    private static final String USERNAME = "postgres";
//    private static final String  PASSWORD= "123";
//
//    private static Connection connection;
//
//    static {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<Person> index() throws SQLException {
//    List<Person> people = new ArrayList<>();
//
//        Statement statement = connection.createStatement();
//
//        String SQL = "SELECT * FROM Person";
//        ResultSet resultSet = statement.executeQuery(SQL);
//
//        while (resultSet.next()){
//            Person person = new Person();
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("name"));
//
//            people.add(person);
//
//        }
//
//        return people;
//    }
//
//   public Person show(int id){
////    return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
//return  new Person();
//    }
//
//    public void save(Person person){
////    people.add(person);
//    }
//
//}
//
//
