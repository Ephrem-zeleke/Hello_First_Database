package movies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    // create a field name databasePath

    private String databasePath;
    // let's create a constructor
    // create a table or make sure it is created
    Database (String databasePath){

        // create a connection with the database that is located on your computer or somewhere else in the internet
        this.databasePath = databasePath;
        try (Connection connection = DriverManager.getConnection(databasePath);
             Statement statement = connection.createStatement()){
            // create a table here

            statement.execute("CREATE TABLE IF NOT EXISTS movies (name TEXT, stars INTEGERS, watched BOOLEAN)");

        }catch (SQLException e){
            System.out.println("Error creating the DB table because " + e);
        }
        // if something error happen, our code shouldn't crashed so we have to handle errors with try and catch


    }
    // write a method that add data to the database

    public void addNewMovie(Movie movie){
        // add the try catch block to handle an error

        String insertSql = "INSERT INTO movies VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(databasePath);

             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)){

             preparedStatement.setString(1, movie.getName());
             preparedStatement.setInt(2, movie.getStars());
             preparedStatement.setBoolean(3, movie.isWatched());

             preparedStatement.execute();


        }catch (SQLException e){
            System.out.println("Error adding movie " + movie + " because " + e);
        }


    }
    // add a new method that return all the movies in the movie list
    public List<Movie> getAllMovies() {

        try (Connection connection = DriverManager.getConnection(databasePath);
        Statement statement = connection.createStatement()){

            // now here we can get all the movies

            ResultSet movieResults = statement.executeQuery("SELECT * FROM movies");

            // loop over all the movies
            // create a movie object
            List<Movie> movies = new ArrayList<>();
            while (movieResults.next()){
                String name = movieResults.getString("name");
                int stars = movieResults.getInt("stars");
                boolean watched = movieResults.getBoolean("watched");

                Movie movie = new Movie(name, stars, watched);
                movies.add(movie);

            }return movies;




        }catch (SQLException e){
            System.out.println("Error fetching all the movies because " + e);
            return null;
        }

    }
    // update the data in the database
    // create a method that update data in the database

    public List<Movie> getAllMoviesByWatched(boolean watchedStatus){
        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movies WHERE watched = ?")) {

            preparedStatement.setBoolean(1, watchedStatus);
            ResultSet movieResult = preparedStatement.executeQuery();

            // create a new object

            List<Movie> movies = new ArrayList<>();
            while (movieResult.next()){
                String name = movieResult.getString("name");
                int stars = movieResult.getInt("stars");
                boolean watched = movieResult.getBoolean("watched");

                Movie movie = new Movie(name, stars, watched);
                movies.add(movie);

            }
            return movies;
        }catch (SQLException e){
            System.out.println("Error getting movie because " + e);
            return null;
        }
    }
    // now let's write a method that update the movie information

    public void updateMovie(Movie movie){

        // this method will update the movie information
        // write a sql command to update and assign it in variable

        String sql = "UPDATE movies stars = ?, watched = ?, WHERE name = ?";

        // use the try catch block to catch any error
        // connect to the database using the connection method

        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){


            //
            preparedStatement.setInt(1, movie.getStars());
            preparedStatement.setBoolean(2, movie.isWatched());
            preparedStatement.setString(3, movie.getName());

            // finally execute the prepared statement
            preparedStatement.execute();

        }catch (SQLException e){
            System.out.println("Error updating movie " + movie + " because " + e);
        }

    }

    // let's create a method that delete movie from the list

    public void deleteMovie (Movie movie){

        // here we can write the try catch block develop the connection

        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM movies WHERE name = ?")) {

            preparedStatement.setString(1, movie.getName());
            preparedStatement.execute();

        }catch (SQLException e){
            System.out.println("Error deleting " + movie + " because " + e);
        }
    }
    // write a method for search. it will search a movie list or partial search

    public List<Movie> search( String searchTerm){

        String sql = "SELECT * FROM movies WHERE UPPER(name) LIKE UPPER(?)";

        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, "%" + searchTerm + "%");

            ResultSet movieResults = preparedStatement.executeQuery();

            List<Movie> movies = new ArrayList<>();
            while (movieResults.next()){
                String name = movieResults.getString("name");
                int stars = movieResults.getInt("stars");
                boolean watched = movieResults.getBoolean("watched");

                Movie movie = new Movie(name, stars, watched);
                movies.add(movie);

            }
            return movies;

        }catch (SQLException e){
            System.out.println("Error searching " + searchTerm + " because " + e);
            return null;
        }
    }

}

