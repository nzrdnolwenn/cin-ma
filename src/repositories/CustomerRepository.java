package repositories;

import database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import models.Customer;

public class CustomerRepository implements CustomerRepositoryInterface{

	@Override
	public void add(Customer customer) {
		String sqlRequest = "INSERT INTO customer (name, birthDate) VALUES ('"
				+ customer.getName() + "', '"
				+ customer.getBirthDate()
				+ "')";

		try (
				Connection connection = Database.getConnection();
				Statement statement = connection.createStatement()) {
				int rowsAffected = statement.executeUpdate(sqlRequest);
				if(rowsAffected> 0){
					System.out.println("Client ajouté à la base de données.");
				}
				else {
					System.out.println("Échec de l'ajout du client.");
				}
		} catch (SQLException e) {
			System.err.println("Erreur lors de l'ajout du client : " + e.getMessage());
		}
	}

	@Override
	public Customer findByName(String name){

		String sqlRequest = "SELECT * FROM customer WHERE name = '" + name + "'";

		try (
			Connection connection = Database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlRequest)) {

			if (resultSet.next()) {
				String customerName = resultSet.getString("name");
				LocalDate birthDate = resultSet.getDate("birthDate").toLocalDate();
				return new Customer(customerName, birthDate);
			}else {
                System.out.println("Aucun client trouvé avec le nom : " + name);
            }
		} catch (SQLException e) {
			System.err.println("Erreur lors de la recherche du client : " + e.getMessage());
		}
		return null;
	}

	@Override
	public void delete(Customer customer) {
		String sqlRequest = "DELETE FROM customer WHERE name = '"+ customer.getName() + "'";
		try (
			Connection connection = Database.getConnection();
			Statement statement = connection.createStatement())
		{
			int rowsDeleted = statement.executeUpdate(sqlRequest);
			System.out.println(sqlRequest);
			if (rowsDeleted > 0) {
                System.out.println("Client supprimé de la base de données.");
            } else {
                System.out.println("Aucun client trouvé à supprimer avec le nom : " + customer.getName());
            }
		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression du client : " + e.getMessage());
		}
	}
}
