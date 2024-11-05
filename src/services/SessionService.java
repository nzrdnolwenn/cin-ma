package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import models.Movie;
import models.Room;
import models.Session;
import repositories.MovieRepository;
import repositories.MovieRepositoryInterface;
import repositories.RoomRepositoryInterface;
import repositories.SessionRepositoryInterface;

public class SessionService implements SessionServiceInterface {

    private final SessionRepositoryInterface sessionRepository;
    private final RoomServiceInterface roomService;
    private final MovieServiceInterface movieService;
    private final RoomRepositoryInterface roomRepository;
    private final MovieRepositoryInterface movieRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final Scanner scanner = new Scanner(System.in);

    public SessionService(SessionRepositoryInterface sessionRepository, RoomServiceInterface roomService, RoomRepositoryInterface roomRepository, MovieServiceInterface movieService, MovieRepositoryInterface movieRepository) {
        this.sessionRepository = sessionRepository;
        this.roomService = roomService;
        this.roomRepository = roomRepository;
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    @Override
    public void add() {
        try {
            LocalDateTime startHourly = promptForDateTime();
            if (startHourly == null) return;

            roomService.findAll();
            int numberRoom = promptForRoomNumber();
            Room room = roomRepository.findByNumber(numberRoom);
            if (room == null) {
                System.out.println("Salle introuvable.");
                return;
            }

            movieService.findAll();
            String title = promptForMovieTitle();
            Movie movie = movieRepository.findByTitle(title);
            if (movie == null) {
                System.out.println("Film introuvable.");
                return;
            }

            boolean isVo = promptForBoolean("Le film sera diffusé en VO ? (o/n) : ");
            boolean isSt = promptForBoolean("Le film sera diffusé avec les sous-titres ? (o/n) : ");

            Session session = new Session(startHourly, movie, room, isVo, isSt);
            sessionRepository.add(session);
            System.out.println("Séance ajoutée avec succès.");

        } catch (Exception e) {
            System.out.println("Erreur dans la saisie des informations : " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        LocalDateTime startHourly = promptForDateTime();
        if (startHourly == null) return;

        Session session = sessionRepository.findByStartHourly(startHourly);
        if (session != null) {
            sessionRepository.delete(session);
            System.out.println("Séance supprimée avec succès !");
        } else {
            System.out.println("Séance introuvable.");
        }
    }

    @Override
    public void findAllAvailable() {
        System.out.println("Séances disponibles avec des places restantes :");
        List<Session> availableSessions = sessionRepository.findAllAvailable();
        if (availableSessions.isEmpty()) {
            System.out.println("Aucune séance disponible avec des places restantes.");
        } else {
            for (Session session : availableSessions) {
                System.out.println(
                    "Séance : " + session.getMovie().getTitle()
                    + ", le " + session.getStartHourly().toLocalDate()
                    + " à " + session.getStartHourly().toLocalTime()
                    + ", Salle : " + session.getRoom().getNumberRoom()
                    + ", VO : " + (session.isIsVo() ? "Oui" : "Non")
                    + ", ST : " + (session.isIsSt() ? "Oui" : "Non"));
            }
        }
    }

    private LocalDateTime promptForDateTime() {
        try {
            System.out.println("Saisir la date de la séance (dd/MM/yyyy) : ");
            String dateString = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateString, dateFormatter);

            System.out.println("Saisir l'heure de la séance (HH:mm) : ");
            String timeString = scanner.nextLine();
            LocalTime time = LocalTime.parse(timeString, timeFormatter);

            return LocalDateTime.of(date, time);
        } catch (DateTimeParseException e) {
            System.out.println("Format de date ou d'heure invalide.");
        }
        return null;
    }

    private int promptForRoomNumber() {
        System.out.println("Saisir le numéro de la salle : ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Le numéro de salle doit être un nombre entier.");
        }
        return -1;
    }

    private String promptForMovieTitle() {
        System.out.println("Saisir le titre du film : ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Le titre du film ne peut pas être vide.");
            return null;
        }
        return title;
    }

    private boolean promptForBoolean(String message) {
        System.out.println(message);
        return scanner.nextLine().equalsIgnoreCase("o");
    }

}
