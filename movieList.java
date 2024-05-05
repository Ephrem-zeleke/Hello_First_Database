package movies;

import java.util.List;

import static input.InputUtils.*;

public class movieList {

    private final static String DB_PATH = "jdbc:sqlite:movie_watchList.sqlite";

    private static Database database;

    public static void main(String[] args) {

        database = new Database(DB_PATH);
        addNewMovie();
        checkedIfWatchedAndRate();
        deletedWatchedMovies();
        searchMovie();
        displayAllMovies();

    }
    public static void addNewMovie(){
        do {
            // we will write a method that add a movie to the movie list
            // assign a variable for the movie name

            String movieName = stringInput("Enter the movie name");
            // assigning a variable for watched

            boolean movieWatched = yesNoInput("Have you seen the movie yet?");
            // if they watch the movie, ask for rating
            // assign a variable for rating
            int movieStars = 0;
            if (movieWatched){
                movieStars = positiveIntInput("What is your rating out of 5?");
            }

            Movie movie = new Movie(movieName, movieStars, movieWatched);

            database.addNewMovie(movie);

        }while (yesNoInput("Add a movie to the watchlist?"));


    }
    // let's create a method that display all the movies

    public static void displayAllMovies(){

        List<Movie> movies = database.getAllMovies();

        if (movies.isEmpty()){
            System.out.println("No movies to display");
        }else {
            for (Movie movie:movies){
                System.out.println(movie);
            }
        }
    }

    // lets write a method that asks the user if they watched the movie and their rating and update the database

    public static void checkedIfWatchedAndRate(){

        // lets get the movie list that haven't watched
        List<Movie> unwatchedMovie = database.getAllMoviesByWatched(false);

        // now we can ask the user if they watched the movie yet
        // loop all the unwatched movie

        for (Movie movie: unwatchedMovie){
            boolean hasWatched = yesNoInput("Have you watched " + movie + " yet?");
            if (hasWatched){
                int stars = positiveIntInput("What is your rating for " + movie.getName() + " out of 5?");
                movie.setWatched(true);
                movie.setStars(stars);
                database.updateMovie(movie);
            }
        }
    }
    // here now we can ask the user if they want to delete movie from the list

    public static void deletedWatchedMovies (){

        // ask the user if they want to delete the watched movies from the database

        System.out.println("Here is all the movies you have watched:");
        // the list of movies that are watched
        List<Movie> watchedMovies = database.getAllMoviesByWatched(true);

        for (Movie movie:watchedMovies){
            boolean delete = yesNoInput("Delete " + movie + " ?");
            if(delete){
                database.deleteMovie(movie);
            }
        }
    }
    // let's create a method that call the search method
    public static void searchMovie (){
        String movieName = stringInput("Enter movie name:");
        List<Movie> movieWatches = database.search(movieName);

        if (movieWatches.isEmpty()){
            System.out.println("No matches");
        }else {
            for(Movie movie: movieWatches){
                System.out.println(movie);
            }
        }
    }
}
