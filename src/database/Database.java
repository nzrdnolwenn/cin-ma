package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mariadb://localhost:3306/movietheater";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    // Chargement explicite du pilote JDBC
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Pilote JDBC non trouvé : " + e.getMessage());
        }
    }

    // Méthode pour obtenir une nouvelle connexion à chaque appel
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'obtention de la connexion : " + e.getMessage());
            return null;
        }
    }

    // Méthode pour fermer la connexion
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connexion fermée avec succès.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}
