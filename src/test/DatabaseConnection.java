package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Connexion partagée à la base de données
    private static Connection connection;

    /**
     * Retourne une connexion valide à la base de données.
     * Si elle n'existe pas ou a été fermée, une nouvelle est créée.
     */
    @SuppressWarnings("exports")
	public static Connection getConnection() throws SQLException {
        try {
            // Vérifie si la connexion est nulle ou fermée
            if (connection == null || connection.isClosed()) {
                // URL de connexion à la base de données Access avec UCanAccess
                String url = "jdbc:ucanaccess://C:\\Users\\hp\\Documents\\ana.accdb";
                connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur de connexion à la base de données : " + e.getMessage(), e);
        }
        return connection;
    }

    /**
     * Ferme la connexion à la base de données.
     * À utiliser uniquement à la fermeture totale de l'application.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion à la base de données fermée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }
}
