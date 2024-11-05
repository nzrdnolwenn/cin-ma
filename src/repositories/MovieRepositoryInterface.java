package repositories;

import java.util.List;
import models.Movie;

public interface MovieRepositoryInterface {
    void add(Movie movie);
    void delete(Movie movie);
    Movie findByTitle(String title);
    List<Movie> findAll();
    
}
