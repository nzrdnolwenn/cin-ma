package repositories;

import database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import models.Movie;
import models.Room;
import models.Session;

public class SessionRepository implements SessionRepositoryInterface {

    @Override
    public void add(Session session) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedStartHourly = session.getStartHourly().format(formatter);

        String sqlRequest = "INSERT INTO session (startHourly, movieTitle, numberRoom, isVo, isSt) VALUES ('"
                + formattedStartHourly + "', '"
                + session.getMovie().getTitle() + "', "
                + session.getRoom().getNumberRoom() + ", "
                + session.isIsVo() + ", "
                + session.isIsSt()
                + ")";

        try (
                Connection connection = Database.getConnection(); Statement statement = connection.createStatement()) {
                    int rowsAffected = statement.executeUpdate(sqlRequest);
                    if (rowsAffected > 0) {
                        System.out.println("Séance ajoutée à la base de données.");
                    } else {
                        System.out.println("Échec de l'ajout de la séance.");
                    }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la séance : " + e.getMessage());
        }
    }

    @Override
    public void delete(Session session) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedStartHourly = session.getStartHourly().format(formatter);

        String sqlRequest = "DELETE FROM session WHERE startHourly = '" + formattedStartHourly + "'";
        try (
                Connection connection = Database.getConnection(); Statement statement = connection.createStatement()) {
                    int rowsDeleted = statement.executeUpdate(sqlRequest);
                    if (rowsDeleted > 0) {
                        System.out.println("Séance supprimée de la base de données.");
                    } else {
                        System.out.println("Aucune séance trouvée avec l'horaire : " + formattedStartHourly);
                    }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la séance : " + e.getMessage());
        }
    }

    @Override
    public Session findByStartHourly(LocalDateTime startHourly) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedStartHourly = startHourly.format(formatter);
        
        String sqlRequest = "SELECT * FROM session WHERE startHourly = '" + formattedStartHourly + "'";

        try (
                Connection connection = Database.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {

            if (resultSet.next()) {
                LocalDateTime sessionStartHourly = resultSet.getTimestamp("startHourly").toLocalDateTime();
                String movieTitle = resultSet.getString("movieTitle");
                int roomNumber = resultSet.getInt("numberRoom");
                boolean isVo = resultSet.getBoolean("isVo");
                boolean isSt = resultSet.getBoolean("isSt");

                MovieRepositoryInterface movieRepository = new MovieRepository();
                RoomRepositoryInterface roomRepository = new RoomRepository();

                Movie movie = movieRepository.findByTitle(movieTitle);
                Room room = roomRepository.findByNumber(roomNumber);

                if (movie != null && room != null) {
                    return new Session(sessionStartHourly, movie, room, isVo, isSt);
                }else {
                    System.out.println("Film ou salle introuvable pour la séance.");
                }
            }else {
                System.out.println("Aucune séance trouvée avec l'horaire : " + formattedStartHourly);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de la séance : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        String sqlRequest = "SELECT * FROM session";
        
        try (
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlRequest)) {

            while(resultSet.next()){
                int sessionId = resultSet.getInt("id");
                LocalDateTime sessionStartHourly = resultSet.getTimestamp("startHourly").toLocalDateTime();
                String movieTitle = resultSet.getString("movieTitle");
                int roomNumber = resultSet.getInt("numberRoom");
                boolean isVo = resultSet.getBoolean("isVo");
                boolean isSt = resultSet.getBoolean("isSt");

                MovieRepositoryInterface movieRepository = new MovieRepository();
                RoomRepositoryInterface roomRepository = new RoomRepository();

                Movie movie = movieRepository.findByTitle(movieTitle);
                Room room = roomRepository.findByNumber(roomNumber);

                if (movie != null && room != null) {
                    sessions.add(new Session(sessionId, sessionStartHourly, movie, room, isVo, isSt));
                }
            }
            if (sessions.isEmpty()) {
                System.out.println("Aucune séance trouvée dans la base de données.");
            }

            }catch (SQLException e) {
                System.err.println("Erreur lors de la récupération des séances : " + e.getMessage());
        }
        return sessions;
    }

    @Override
    public List<Session> findAllAvailable() {

        List<Session> availableSessions = new ArrayList<>();
        List<Session> allSessions = findAll();
        
        ReservationRepositoryInterface reservationRepository = new ReservationRepository();
        for (Session session : allSessions) {
            int reservationCount = reservationRepository.countReservationsBySession(session);
            int seatCount = session.getRoom().getSeatCount();
    
            if (reservationCount < seatCount) {
                availableSessions.add(session);
            }
        }

        if (availableSessions.isEmpty()) {
            System.out.println("Aucune séance disponible avec des places restantes.");
        }
        
        return availableSessions;
    }
}