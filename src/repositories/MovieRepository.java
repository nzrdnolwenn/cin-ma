package repositories;

import database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import models.Movie;

public class MovieRepository implements MovieRepositoryInterface {

    @Override
    public void add(Movie movie) {
        String sqlRequest = "INSERT INTO movie (title, duration, isForbiddenUnder18) VALUES ('"
                + movie.getTitle() + "'', "
                + movie.getDuration().toMinutes() + ", "
                + movie.isForbiddenUnder18()
                + ")";

        try (
                Connection connection = Database.getConnection(); Statement statement = connection.createStatement()) {
                    int rowsAffected =statement.executeUpdate(sqlRequest);
                    if (rowsAffected > 0) {
                        System.out.println("Film ajouté à la base de données.");
                    } else {
                        System.out.println("Échec de l'ajout du film.");
                    }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du film : " + e.getMessage());
        }
    }

    @Override
    public void delete(Movie movie) {
        String sqlRequest = "DELETE FROM movie WHERE title = '" + movie.getTitle() + "'";
        try (
                Connection connection = Database.getConnection(); Statement statement = connection.createStatement()) {

            int rowsDeleted = statement.executeUpdate(sqlRequest);
            if (rowsDeleted > 0) {
                System.out.println("Film supprimé de la base de données.");
            } else {
                System.out.println("Aucun film trouvé avec le titre : " + movie.getTitle());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du film : " + e.getMessage());
        }
    }

    @Override
    public Movie findByTitle(String title) {
        String sqlRequest = "SELECT * FROM movie WHERE title = '" + title + "'";

        try (
                Connection connection = Database.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest)) {

            if (resultSet.next()) {
                String movieTitle = resultSet.getString("title");
                int durationMinutes = resultSet.getInt("duration");
                Duration duration = Duration.ofMinutes(durationMinutes);
                Boolean isForbiddenUnder18 = resultSet.getBoolean("isForbiddenUnder18");
                
                return new Movie(movieTitle, duration, isForbiddenUnder18);
            }else {
                System.out.println("Aucun film trouvé avec le titre : " + title);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du film : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        String sqlRequest = "SELECT * FROM movie";
        try (
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlRequest)) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int durationMinutes = resultSet.getInt("duration");
                Duration duration = Duration.ofMinutes(durationMinutes);
                Boolean isForbiddenUnder18 = resultSet.getBoolean("isForbiddenUnder18");

                Movie movie = new Movie(title, duration, isForbiddenUnder18);
                movies.add(movie);
            }
            if (movies.isEmpty()) {
                System.out.println("Aucun film trouvé dans la base de données.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les films : " + e.getMessage());
        }
        return movies;
    }
}
