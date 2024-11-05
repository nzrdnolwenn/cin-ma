package repositories;

import database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Room;

public class RoomRepository implements RoomRepositoryInterface {

	@Override
	public void add(Room room) {
		String sqlRequest = "INSERT INTO room (numberRoom, seatCount) VALUES ("
			+ room.getNumberRoom() + ", "
			+ room.getSeatCount()
			+ ")";
		try (
				Connection connection = Database.getConnection();
				Statement statement = connection.createStatement()) {
					int rowsAffected = statement.executeUpdate(sqlRequest);

				if (rowsAffected > 0) {
                System.out.println("Salle ajoutée à la base de données.");
            } else {
                System.out.println("Échec de l'ajout de la salle.");
            }
		} catch (SQLException e) {
			System.err.println("Erreur lors de l'ajout de la salle : " + e.getMessage());
		}
	}

	@Override
	public void delete(Room room) {
		String sqlRequest = "DELETE FROM room WHERE numberRoom = " + room.getNumberRoom();
		try (
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement())
        {
            int rowsDeleted = statement.executeUpdate(sqlRequest);
			if (rowsDeleted > 0) {
                System.out.println("Salle supprimée de la base de données.");
            } else {
                System.out.println("Aucune salle trouvée avec le numéro : " + room.getNumberRoom());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la salle : " + e.getMessage());
        }
	}

    @Override
    public List<Room> findAll() {
		List<Room> rooms = new ArrayList<>();
		String sqlRequest = "SELECT * FROM room";
		try (
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlRequest)) {

            while (resultSet.next()) {
                int numberRoom = resultSet.getInt("numberRoom");
                int seatCount = resultSet.getInt("seatCount");

                Room room = new Room(numberRoom, seatCount);
                rooms.add(room);
            }
			if (rooms.isEmpty()) {
                System.out.println("Aucune salle trouvée dans la base de données.");
            }
        } catch (SQLException e) {
			System.err.println("Erreur lors de la récupération des salles : " + e.getMessage());
        }
        return rooms;
    }

    @Override
    public Room findByNumber(int numberRoom) {
        String sqlRequest = "SELECT * FROM room WHERE numberRoom = " + numberRoom;

        try (
			Connection connection = Database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlRequest)) {

			if (resultSet.next()) {
				int roomNumber = resultSet.getInt("numberRoom");
                int seatCount = resultSet.getInt("seatCount");
				return new Room(roomNumber, seatCount);
			}
			else {
                System.out.println("Aucune salle trouvée avec le numéro : " + numberRoom);
            }

		} catch (SQLException e) {
			System.err.println("Erreur lors de la recherche de la salle : " + e.getMessage());
		}
		return null;
    }

}
