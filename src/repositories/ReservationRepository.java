package repositories;

import database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import models.Customer;
import models.Reservation;
import models.Session;

public class ReservationRepository implements ReservationRepositoryInterface {

    @Override
    public void add(Reservation reservation) {
        String sqlRequest = "INSERT INTO reservation (customerName, sessionId) VALUES ('"
                + reservation.getCustomer().getName() + "', "
                + reservation.getSession().getId()
                + ")";

        try (
                Connection connection = Database.getConnection(); Statement statement = connection.createStatement()) {
                    int rowsAffected = statement.executeUpdate(sqlRequest);
                    if (rowsAffected > 0) {
                        System.out.println("Réservation ajoutée à la base de données.");
                    } else {
                        System.out.println("Échec de l'ajout de la réservation.");
                    }
        
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la réservation : " + e.getMessage());
        }
    }

    @Override
    public Reservation findByCustomerAndSession(Customer customer, Session session) {
        String sqlRequest = "SELECT * FROM reservation WHERE customerName = '" + customer.getName() + "' AND sessionId = " + session.getId();
        try (
                Connection connection = Database.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {

            if (resultSet.next()) {
                return new Reservation(customer, session);
            }else {
                System.out.println("Aucune réservation trouvée pour ce client et cette séance.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de la réservation : " + e.getMessage());
        }
        return null;
    }

    @Override
    public int countReservationsBySession(Session session) {
        String sqlRequest = "SELECT COUNT(*) AS reservationCount FROM reservation WHERE sessionId = " + session.getId();

        try (
                Connection connection = Database.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlRequest)) {

            if (resultSet.next()) {
                return resultSet.getInt("reservationCount");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des réservations : " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void delete(Reservation reservation) {
        String sqlRequest = "DELETE FROM reservation WHERE customerName = " + reservation.getCustomer().getName() + "' AND sessionId = '" + reservation.getSession().getId();
        try (
                Connection connection = Database.getConnection(); Statement statement = connection.createStatement()) {
                    int rowsDeleted = statement.executeUpdate(sqlRequest);
                    if (rowsDeleted > 0) {
                        System.out.println("Réservation supprimée de la base de données.");
                    } else {
                        System.out.println("Aucune réservation trouvée pour ce client et cette séance.");
                    }
                   
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la réservation : " + e.getMessage());
        }
    }
}
