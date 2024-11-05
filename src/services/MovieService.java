package services;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import models.Movie;
import repositories.MovieRepositoryInterface;

public class MovieService implements MovieServiceInterface {

    private MovieRepositoryInterface movieRepository;
    private final Scanner scanner = new Scanner(System.in);

    public MovieService(MovieRepositoryInterface movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void add() {
        try{
            String title = promptForTitle();
            if (title == null) return;

            Duration duration = promptForDuration();
            if (duration == null) return;

            System.out.println("Est-il interdit aux moins de 18ans ? (o/n) : ");
            boolean isForbiddenUnder18 = scanner.nextLine().equalsIgnoreCase("o");

            Movie movie = new Movie(title, duration, isForbiddenUnder18);
            movieRepository.add(movie);
            System.out.println("Film ajouté à la base de données.");

        }catch (Exception e) {
            System.out.println("Erreur dans la saisie des informations : " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        String title = promptForTitle();
        if (title == null) return;

        Movie movie = movieRepository.findByTitle(title);
        if (movie != null) {
            movieRepository.delete(movie);
            System.out.println("Film supprimé avec succès !");
        } 
        else {
            System.out.println("Film introuvable.");
        }
    }

    @Override
    public void findAll() {
        List<Movie> movies = movieRepository.findAll();

        if (movies.isEmpty()) {
            System.out.println("Aucun film dans la base de données.");
        } else {
            System.out.println("Liste des films disponibles :");
            for (Movie movie : movies) {
                System.out.println("Titre : " + movie.getTitle() + 
                                   ", Durée : " + movie.getDuration().toMinutes() + " minutes" +
                                   ", Interdit aux moins de 18 ans : " + (movie.isForbiddenUnder18() ? "Oui" : "Non"));
            }
        }
    }

    private String promptForTitle() {
        System.out.println("Saisir le titre du film : ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Le titre du film ne peut pas être vide.");
            return null;
        }
        return title;
    }

    private Duration promptForDuration() {
        System.out.println("Saisir la durée du film (en minutes) : ");
        try {
            int durationMinutes = Integer.parseInt(scanner.nextLine().trim());
            if (durationMinutes <= 0) {
                System.out.println("La durée doit être un nombre positif.");
                return null;
            }
            return Duration.ofMinutes(durationMinutes);
        } catch (NumberFormatException e) {
            System.out.println("La durée doit être un nombre entier.");
        }
        return null;
    }
}
