package test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionUtilisateur {

	public void ajouterUtilisateur(Utilisateur utilisateur) throws SQLException {
	    String query = "INSERT INTO Utilisateurs (login, motDePasse, role) VALUES (?, ?, ?)";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

	        stmt.setString(1, utilisateur.getLogin());
	        stmt.setString(2, utilisateur.getMotDePasse());
	        stmt.setString(3, utilisateur.getRole());

	        stmt.executeUpdate();

	        // Récupérer l'id généré automatiquement
	        try (ResultSet rs = stmt.getGeneratedKeys()) {
	            if (rs.next()) {
	                int idGénéré = rs.getInt(1);
	                utilisateur.setIdClient(idGénéré); // mettre à jour l'objet Utilisateur si nécessaire
	            }
	        }
	    }
	}


    public Utilisateur authentifier(String login, String motDePasse) throws SQLException {
        String query = "SELECT * FROM Utilisateurs WHERE login = ? AND motDePasse = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Utilisateur(
                    rs.getInt("idUtilisateur"),
                    rs.getString("login"),
                    rs.getString("motDePasse"),
                    rs.getString("role"),
                    rs.getInt("idClient")
                );
            }
            return null;
        }
    }

    public List<Utilisateur> obtenirUtilisateursParRole(String role) throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM Utilisateurs WHERE role = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                utilisateurs.add(new Utilisateur(
                    rs.getInt("idUtilisateur"),
                    rs.getString("login"),
                    rs.getString("motDePasse"),
                    rs.getString("role"),
                    rs.getInt("idClient")
                ));
            }
        }
        return utilisateurs;
    }

    public void modifierUtilisateur(Utilisateur utilisateur) throws SQLException {
        String query = "UPDATE Utilisateurs SET login = ?, motDePasse = ?, role = ?, idClient = ? WHERE idUtilisateur = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, utilisateur.getLogin());
            stmt.setString(2, utilisateur.getMotDePasse());
            stmt.setString(3, utilisateur.getRole());
            stmt.setInt(4, utilisateur.getIdClient());
            stmt.setInt(5, utilisateur.getIdUtilisateur());
            stmt.executeUpdate();
        }
    }

    public void supprimerUtilisateur(int idUtilisateur) throws SQLException {
        String query = "DELETE FROM Utilisateurs WHERE idUtilisateur = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUtilisateur);
            stmt.executeUpdate();
        }
    }
    
    
    public boolean loginExiste(String login) throws SQLException {
        String query = "SELECT COUNT(*) FROM Utilisateurs WHERE login = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}