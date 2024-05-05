package org.example;

import java.sql.*;

public class HelloDatabase {
    public static void main(String[] args) throws SQLException {
        // let's create a string that defined where the database is located

        String url = "jdbc:sqlite:hello.sqlite"; // this is where the database is
        // set a connection between java program and the database

        Connection connection = DriverManager.getConnection(url);

        // here the intellij recommend us to deal with an exception if the file is not in the driver
        // for now, we will throw an exception if it happens
        Statement statement = connection.createStatement();

        // using the sql language, we can ask the database to create table, update, delete or add to the table
        // now let's create a table

        //String createTableSql = "CREATE TABLE cats (name TEXT, age INTEGER)";

        // now we can ask the database to create the cats table
        //statement.execute(createTableSql);

        // let's write some data to the table

        // String insertDataSql = "INSERT INTO cats VALUES ('maru', 10)";
        //statement.execute(insertDataSql);

        //String insertDataSql = "INSERT INTO cats VALUES ('Hello kitty', 40)";
        //statement.execute(insertDataSql);

        // we can get all tha data from tha cats table

        String getAllDataSql = "SELECT * FROM cats";
        // since we need the database return all the data we will use a method called resultSet
        ResultSet allCats = statement.executeQuery(getAllDataSql);

        // it is not ideal to load all the data from database to our computer
        // we have to read each line and we can use the while loop

        while (allCats.next()){
            String name = allCats.getString("name");
            int age = allCats.getInt("age");
            System.out.println(name + " is " + age + " years old.");
        }



    }
}
